import jwtDecode from "jwt-decode";
import { TokenPayload } from "../Navigation/UserInfo";

export interface UserSearch {
  userId: string;
  firstName: string;
  lastName: string;
}

export const getFormattedName = (token: string | null): string => {
  if (token !== null) {
    const tokenPayload: TokenPayload = jwtDecode(token);
    return `${tokenPayload.firstName.charAt(0).toUpperCase() + tokenPayload.firstName.slice(1)} ${
      tokenPayload.lastName.charAt(0).toUpperCase() + tokenPayload.lastName.slice(1)
    }`;
  }
  return "";
};

export const getUserId = (token: string | null): string => {
  if (token !== null) {
    const tokenPayload: TokenPayload = jwtDecode(token);
    return tokenPayload.userId;
  }
  return "";
};
