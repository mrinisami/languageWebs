export const routes = {
  home: "/",
  login: "/login",
  profile: (userId: string | undefined, tabValue: string | undefined) => `/users/${userId}/profile/${tabValue}`,
  userLanguageReviews: "/languageGrades"
};
