export interface LanguageAllGrades {
  userGrade: number | null;
  evaluatorGrade: number | null;
  selfAssessment: number;
  language: string;
}

export interface LanguagesAllGrades {
  languages: LanguageAllGrades[];
}
