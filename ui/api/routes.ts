export const user = {
  getUsers: "/public/users",
  login: `/auth/authenticate`,
  getUserInfo: (userId: string | undefined) => `/public/users/${userId}`
};

export const languageGrades = {
  getUserGrades: (userId: string | undefined) => `/public/users/${userId}/languageGrades/summary`,
  getListofEmittedGrades: (userId: string | undefined, emitterUserId: string) =>
    `/users/${userId}/languageGrades/${emitterUserId}`,
  editLanguageGrades: (userId: string | undefined, languageGradeId: number | undefined) =>
    `/users/${userId}/languageGrade/${languageGradeId}`,
  addLanguageGrade: (userId: string | undefined) => `/users/${userId}/languages/grade`
};

export const request = {
  getLanguageStats: "/public/requests/summary",
  getFilteredRequests: "/public/requests",
  getUploadUri: "/storage/upload-uri",
  addRequest: (userId: string) => `/users/${userId}/requests`,
  editRequest: (userId: string, requestId: number | undefined) => `/users/${userId}/requests/${requestId}`,
  getRequest: (requestId: number | undefined) => `/requests/${requestId}`,
  deleteRequest: (userId: string, requestId: number | undefined) => `/users/${userId}/requests/${requestId}`
};
