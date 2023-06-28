export const routes = {
  home: "/",
  login: "/login",
  profile: (userId: string | undefined, tabValue: string | undefined) => `/users/${userId}/profile/${tabValue}`,
  userLanguageReviews: "/languageGrades",
  request: "/requests",
  filteredRequest: "/requests/",
  addRequest: "/requests/create",
  editRequest: (requestId: number) => `/requests/${requestId}/edit`
};
