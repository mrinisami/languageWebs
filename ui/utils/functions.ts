import { LanguageAllGrades } from "../api/language";

export const addDaysToTimestamp = (days: number): Date => {
  const date = new Date();
  date.setDate(date.getDate() + days);
  return date;
};

export const avgGrade = (grade: LanguageAllGrades) => {
  let grades = [grade.evaluatorGrade, grade.userGrade, grade.selfAssessment];

  const newGrades: number[] = grades.filter((el) => el !== null);

  return newGrades.reduce((a: number, b: number) => a + b) / newGrades.length;
};
