import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/StylesInvertedTable.css";
import "./css/StiloviForma.css";

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

  const [showCreateRow, setShowCreateRow] = useState<boolean>(false);
  const [newCandidate, setNewCandidate] = useState({
    nameFull: "",
    dateOfBirth: "",
    contactNumber: "",
    email: "",
    skillIds: [] as number[]
  });

  const [showCreateNewSkill, setShowCreateNewSkill] = useState<boolean>(false);
  const [newSkill, setNewSkill] = useState({
      name: ""
  });

  const [selectedSkillOption, setSelectedSkillOption] = useState<string>("");



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

  const removeCandidate = async (candidateId: number) => {
      try {
        await axios.delete(`http://localhost:8080/api/candidates/${candidateId}`);
        fetchAllCandidates();
      } catch (err) {
        console.error("Error removing the candidate: ", err);
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

    if (skillId == 0) {
        setShowCreateNewSkill(prev => !prev);
        return;
    }

    if (!skillId) return;

    try {
      await axios.post(`http://localhost:8080/api/candidates/${candidateId}/skills`, [skillId]);
      fetchAllCandidates();
    } catch (err) {
      console.error("Error adding skill to candidate:", err);
    }
  };

  const createSkill = async (e: React.FormEvent) => {
      e.preventDefault();
      setSelectedSkillOption("");

      try {
        await axios.post("http://localhost:8080/api/skills/", { ...newSkill });

        setShowCreateNewSkill(false);

        setNewSkill({
          name: ""
        });

        await fetchAllSkills();
        await fetchAllCandidates();

      } catch (err) {
        console.error("Error creating new skill: ", err);
      }
  };

  const createCandidate = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/api/candidates/", {
        ...newCandidate,
        dateOfBirth: newCandidate.dateOfBirth || null
      });

      setShowCreateRow(false);

      setNewCandidate({
        nameFull: "",
        dateOfBirth: "",
        contactNumber: "",
        email: "",
        skillIds: []
      });

      fetchAllCandidates();

    } catch (err) {
      console.error("Error creating the candidate: ", err);
    }
  };

  return (
    <div>
      <h1>Candidates</h1>

      {/* Skill filter for candidates */}
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

      {/* Name filter for candidates */}
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
            <th>Assign Skill</th>
            <th>Remove Candidate</th>
          </tr>
        </thead>

        <tbody>

          {Array.isArray(candidates) && candidates.length > 0 ? (
            candidates.map((candidate) => (
              <tr key={candidate.id}>
                <td className="wider">{candidate.nameFull}</td>
                <td className="wider">{formatDate(candidate.dateOfBirth)}</td>
                <td className="wider">{candidate.contactNumber}</td>
                <td className="wider">{candidate.email}</td>
                <td>
                  {Array.isArray(candidate.skills)
                    ? candidate.skills.map(s =>
                        <div className="skill-card-container">
                            <span key={s.id} style={{ marginRight: "8px" }}>
                                {s.name}
                                <img
                                  src="/remove.png"
                                  alt="Remove skill"
                                  style={{ cursor: "pointer", marginLeft: "4px", width: "16px", height: "16px" }}
                                  onClick={() => removeSkillFromCandidate(candidate.id, s.id)}
                                />
                            </span>
                        </div>)
                    : null}
                </td>
                <td>
                  <select
                    value={selectedSkillOption}
                      onChange={(e) => {
                        const val = e.target.value;
                        setSelectedSkillOption(val);

                        if (val === "CreateNewSkill") {
                          setShowCreateNewSkill(true);
                        } else if (val) {
                          addSkillToCandidate(candidate.id, Number(val));
                        }

                        setSelectedSkillOption("");
                      }}
                  >
                    <option value="">--Select from skills--</option>
                    <option value="CreateNewSkill">(Create new Skill)</option>
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
                <td>
                    <img
                      src="/remove.png"
                      alt="Remove candidate"
                      style={{ cursor: "pointer", marginLeft: "4px", width: "16px", height: "16px" }}
                      onClick={() => removeCandidate(candidate.id)}
                    />
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={6}>No candidates found</td>
            </tr>
          )}

          {showCreateRow && (
           <tr>
             <td>
               <input
                 type="text"
                 value={newCandidate.nameFull}
                 onChange={(e) =>
                   setNewCandidate({ ...newCandidate, nameFull: e.target.value })
                 }
               />
             </td>

             <td>
               <input
                 type="date"
                 value={newCandidate.dateOfBirth}
                 onChange={(e) =>
                   setNewCandidate({ ...newCandidate, dateOfBirth: e.target.value })
                 }
               />
             </td>

             <td>
               <input
                 type="number"
                 value={newCandidate.contactNumber}
                 onChange={(e) =>
                   setNewCandidate({ ...newCandidate, contactNumber: e.target.value })
                 }
               />
             </td>

             <td>
               <input
                 type="email"
                 value={newCandidate.email}
                 onChange={(e) =>
                   setNewCandidate({ ...newCandidate, email: e.target.value })
                 }
               />
             </td>

             <td>
               <select
                 size={1}
                 multiple
                 value={newCandidate.skillIds.map(String)}
                 onChange={(e) =>
                   setNewCandidate({
                     ...newCandidate,
                     skillIds: Array.from(
                       e.target.selectedOptions,
                       (option) => Number(option.value)
                     )
                   })
                 }
               >
                 {skills.map((skill) => (
                   <option key={skill.id} value={skill.id}>
                     {skill.name}
                   </option>
                 ))}
               </select>
             </td>

             <td>
               <button
                    className="btn-save"
                    onClick={createCandidate}
                    disabled={!newCandidate.nameFull || !newCandidate.email || !newCandidate.contactNumber || !newCandidate.dateOfBirth}>
                  Save
               </button>
             </td>
           </tr>
         )}
         <tr>
            <td>
              <button onClick={ () => setShowCreateRow(prev => !prev)}>
                  {showCreateRow ? "Cancel" : "Add new candidate"}
              </button>
            </td>
         </tr>

        </tbody>
      </table>

      {showCreateNewSkill && (
        <div className="modal-overlay">
          <div className="modal-content">

            <h3>Create New Skill</h3>

            <input
              type="text"
              placeholder="e.g React"
              value={newSkill.name}
              onChange={(e) =>
                setNewSkill({ ...newSkill, name: e.target.value })
              }
            />

            <div style={{ marginTop: "10px" }}>
              <button
                className="btn-save"
                onClick={createSkill}
                disabled={!newSkill.name.trim()}
              >
                Save
              </button>

              <button
                onClick={() => {
                  setShowCreateNewSkill(false);
                  setNewSkill({ name: "" });
                  setSelectedSkillOption("");
                }}
                style={{ marginLeft: "10px" }}>
                    Cancel
              </button>
            </div>

          </div>
        </div>
      )}

    </div>
  );

};

export default Candidates;