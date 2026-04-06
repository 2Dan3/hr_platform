export interface Skill {
  id: number;
  name: string;
}

export interface Candidate {
  id: number;
  nameFull: string;
  dateOfBirth: string; // ISO string
  contactNumber: string;
  email: string;
  skills: Skill[];
}