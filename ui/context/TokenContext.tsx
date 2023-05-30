import React, { FC, createContext, useContext, useState } from "react";
import { localStorage } from "../utils/localstorage";
import { LanguagesGradesEmitter } from "../api/language";
import { languageGrades } from "../api/routes";
import useAxios from "axios-hooks";
import { getUserId } from "../utils/user";

function useProviderToken(): TokenContext {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(localStorage.token.exists());

  const userLanguageGrade = (userId: string | undefined): LanguagesGradesEmitter | null => {
    if (localStorage.token.exists()) {
      const [{ data, loading, error }] = useAxios<LanguagesGradesEmitter>({
        url: languageGrades.getListofEmittedGrades(userId, getUserId(localStorage.token.get()))
      });
      if (data) {
        return data;
      }
    }
    return null;
  };

  const login = () => setIsLoggedIn(localStorage.token.exists());
  return { login, isLoggedIn, userLanguageGrade };
}

interface TokenContext {
  login: () => void;
  isLoggedIn: boolean;
  userLanguageGrade: (userId: string | undefined) => LanguagesGradesEmitter | null;
}
interface Props {
  children: React.ReactNode;
}

const tokenContext = createContext({} as TokenContext);
export const useTokenContext = () => {
  return useContext(tokenContext);
};
export const ProvideToken: FC<Props> = ({ children }) => {
  const provideToken: TokenContext = useProviderToken();

  return <tokenContext.Provider value={provideToken}>{children}</tokenContext.Provider>;
};
