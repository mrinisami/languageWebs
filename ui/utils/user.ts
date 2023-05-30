import jwtDecode from "jwt-decode";
import { TokenPayload } from "../Navigation/UserInfo";

export interface UserSearch {
  userId: string;
  firstName: string;
  lastName: string;
}

export const getFormattedNameFromToken = (token: string | null): string => {
  if (token !== null) {
    const tokenPayload: TokenPayload = jwtDecode(token);
    return `${tokenPayload.firstName.charAt(0).toUpperCase() + tokenPayload.firstName.slice(1)} ${
      tokenPayload.lastName.charAt(0).toUpperCase() + tokenPayload.lastName.slice(1)
    }`;
  }
  return "";
};

export const getFormattedNameFromApi = (firstName: string, lastName: string): string => {
  return `${firstName.charAt(0).toUpperCase() + firstName.slice(1)} ${
    lastName.charAt(0).toUpperCase() + lastName.slice(1)
  }`;
};

export const getUserId = (token: string | null): string => {
  if (token !== null) {
    const tokenPayload: TokenPayload = jwtDecode(token);
    return tokenPayload.userId;
  }
  return "";
};
