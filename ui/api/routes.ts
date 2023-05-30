interface UserRoutes {
  getUsers: string;
  login: string;
  getUserInfo: (userId: string | undefined) => string;
}

export const user: UserRoutes = {
  getUsers: "/public/users",
  login: `/auth/authenticate`,
  getUserInfo: (userId: string | undefined) => `/public/users/${userId}`
};

interface LanguageRoutes {
  getUserGrades: (userId: string | undefined) => string;
  getListofEmittedGrades: (userId: string | undefined, emitterUserId: string) => string;
  editLanguageGrades: (userId: string | undefined, languageGradeId: number | undefined) => string;
  addLanguageGrade: (userId: string | undefined) => string;
}
export const languageGrades: LanguageRoutes = {
  getUserGrades: (userId: string | undefined) => `/public/users/${userId}/languageGrades/summary`,
  getListofEmittedGrades: (userId: string | undefined, emitterUserId: string) =>
    `/users/${userId}/languageGrades/${emitterUserId}`,
  editLanguageGrades: (userId: string | undefined, languageGradeId: number | undefined) =>
    `/users/${userId}/languageGrade/${languageGradeId}`,
  addLanguageGrade: (userId) => `/users/${userId}/languages/grade`
};
