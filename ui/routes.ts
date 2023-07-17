export const routes = {
  home: "/",
  login: "/login",
  profile: (userId: string | undefined, tabValue: string | undefined) => `/users/${userId}/profile/${tabValue}`,
  userLanguageReviews: "/languageGrades",
  request: "/requests",
  filteredRequest: "/requests/",
  addRequest: "/requests/create",
  contractRequests: (requestId: number) => `/requests/${requestId}/contract-request`,
  editRequest: (requestId: number) => `/requests/${requestId}/edit`
};
