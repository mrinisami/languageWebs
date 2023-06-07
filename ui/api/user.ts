export interface UserSearch {
  id: string;
  firstName: string;
  lastName: string;
  dateJoined: string | null;
  avatarUri: string | null;
}

interface UserEndPoints {
  searchUsers: string;
}

export const routes: UserEndPoints = {
  searchUsers: "/public/users/search"
};
