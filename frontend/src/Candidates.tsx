import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/StylesInvertedTable.css";
import "./css/StiloviForma.css";
import "./css/StiloviTabela.css";
import "./css/header.css";

interface Skill {
  id: number;
  name: string;
}

interface Candidate {
  id: number;
  nameFull: string;
// yyyy-MM-dd
  dateOfBirth: string;
  contactNumber: string;
  email: string;
  skills: Skill[];
}

const formatDate = (isoString: string) => {
  const date = new Date(isoString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const Candidates: React.FC = () => {
  const [candidates, setCandidates] = useState<Candidate[]>([]);
  const [skills, setSkills] = useState<Skill[]>([]);
  const [searchName, setSearchName] = useState<string>("");
  const [skillFilter, setSkillFilter] = useState<number[]>([]);

// Init candidates and skills load-up
  useEffect(() => {
    fetchAllCandidates();
    fetchAllSkills();
  }, []);

  const fetchAllCandidates = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/candidates/all");

      if (Array.isArray(res.data)) {
        setCandidates(res.data);
      } else {
        console.warn("Expected array from /api/candidates/all", res.data);
        setCandidates([]);
      }
    } catch (err) {
      console.error("Error fetching candidates:", err);
      setCandidates([]);
    }
  };

  const fetchAllSkills = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/skills");
      if (Array.isArray(res.data)) {
        setSkills(res.data);
      } else {
        console.warn("Expected array from /api/skills", res.data);
        setSkills([]);
      }
    } catch (err) {
      console.error("Error fetching skills:", err);
      setSkills([]);
    }
  };

  const handleNameSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await axios.get("http://localhost:8080/api/candidates/all", {
        params: { name: searchName || "" },
      });
      setCandidates(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error("Error searching by name:", err);
      setCandidates([]);
    }
  };

  const handleSkillSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await axios.get("http://localhost:8080/api/candidates/withSkills", {
        params: { skillId: skillFilter },
      });
      setCandidates(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error("Error searching by skills:", err);
      setCandidates([]);
    }
  };

  const removeSkillFromCandidate = async (candidateId: number, skillId: number) => {
    try {
      await axios.delete(`http://localhost:8080/api/candidates/${candidateId}/skills`, { data: [skillId] });
      fetchAllCandidates();
    } catch (err) {
      console.error("Error removing skill from candidate:", err);
    }
  };

  const addSkillToCandidate = async (candidateId: number, skillId: number) => {
    if (!skillId) return;
    try {
      await axios.post(`http://localhost:8080/api/candidates/${candidateId}/skills`, [skillId]);
      fetchAllCandidates();
    } catch (err) {
      console.error("Error adding skill to candidate:", err);
    }
  };

  return (
    <div>
      <h1>Candidates</h1>

      {/* Skill Filter */}
      <form onSubmit={handleSkillSearch}>
        <select
          multiple
          value={skillFilter.map(String)}
          onChange={(e) =>
            setSkillFilter(
              Array.from(e.target.selectedOptions, (option) =>
                Number(option.value)
              )
            )
          }
          size={1}
        >
          {skills.map((skill) => (
            <option key={skill.id} value={skill.id}>
              {skill.name}
            </option>
          ))}
        </select>
        <input type="submit" value="Search by Skill" />
      </form>

      {/* Name Search */}
      <form onSubmit={handleNameSearch}>
        <input
          type="search"
          value={searchName}
          onChange={(e) => setSearchName(e.target.value)}
        />
        <input type="submit" value="Search by Name" />
      </form>

      <table className="tabela">
        <thead>
          <tr>
            <th>Name</th>
            <th>Date of Birth</th>
            <th>Contact</th>
            <th>Email</th>
            <th>Skills</th>
            <th>Add Skill</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(candidates) && candidates.length > 0 ? (
            candidates.map((candidate) => (
              <tr key={candidate.id}>
                <td>{candidate.nameFull}</td>
                <td>{formatDate(candidate.dateOfBirth)}</td>
                <td>{candidate.contactNumber}</td>
                <td>{candidate.email}</td>
                <td>
                  {Array.isArray(candidate.skills)
                    ? candidate.skills.map(s =>
                        <span key={s.id} style={{ marginRight: "8px" }}>
                            {s.name}
                            <img
                              src="/remove.png"
                              alt="Remove skill"
                              style={{ cursor: "pointer", marginLeft: "4px", width: "16px", height: "16px" }}
                              onClick={() => removeSkillFromCandidate(candidate.id, s.id)}
                            />
                        </span>)
                    : null}
                </td>
                <td>
                  <select
                    onChange={(e) =>
                      addSkillToCandidate(candidate.id, Number(e.target.value))
                    }
                  >
                    <option value="">--Select Skill--</option>
                    {skills
                      .filter(
                        (skill) =>
                          !candidate.skills.some((s) => s.id === skill.id)
                      )
                      .map((skill) => (
                        <option key={skill.id} value={skill.id}>
                          {skill.name}
                        </option>
                      ))}
                  </select>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={6}>No candidates found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );

};

export default Candidates;