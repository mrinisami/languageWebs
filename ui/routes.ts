export const routes = {
  home: "/",
  login: "/login",
  profile: (userId: string) => `/users/${userId}/profile`,
  userLanguageReviews: "/profile/language-reviews"
};
