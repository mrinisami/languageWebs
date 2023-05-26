interface Routes {
  getUsers: string;
  login: string;
  getUserGrades: (userId: string) => string;
}

export const endpoints: Routes = {
  getUsers: "/public/users",
  login: `/auth/authenticate`,
  getUserGrades: (userId: string) => `/public/users/${userId}/languageGrades/summary`
};
