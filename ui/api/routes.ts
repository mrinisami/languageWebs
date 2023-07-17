export const user = {
  getUsers: "/public/users",
  login: `/auth/authenticate`,
  getUserInfo: (userId: string | undefined) => `/public/users/${userId}`
};

export const languageGrades = {
  getUserGrades: (userId: string | undefined) => `/public/users/${userId}/languageGrades/summary`,
  getUserGradesByLanguage: (userId: string) => `/users/${userId}/languageGrades`,
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
  getDownloadUri: "/storage/download-uri",
  addRequest: (userId: string) => `/users/${userId}/requests`,
  editRequest: (userId: string, requestId: number | undefined) => `/users/${userId}/requests/${requestId}`,
  getRequest: (requestId: number | undefined) => `/requests/${requestId}`,
  deleteRequest: (userId: string, requestId: number | undefined) => `/users/${userId}/requests/${requestId}`
};

export const contractRequest = {
  getContractRequests: `/contract-requests`,
  getContractRequest: `/contract-request`,
  renderDecision: (contractRequestId: number) => `/contract-request/${contractRequestId}`,
  createRequest: (requestId: number | undefined) => `/requests/${requestId}/contract-request`
};

export const contract = {
  getContracts: "/contracts",
  editContract: (contractId: number) => `/contracts/${contractId}`,
  getUploadUri: "/contracts/storage/upload-uri",
  askExtension: `/extension-request`,
  getExtensionRequests: "/extension-requests",
  editExtensionRequests: (extensionRequestId: number | undefined) => `/extension-requests/${extensionRequestId}`
};
