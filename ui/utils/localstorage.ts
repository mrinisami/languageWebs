import { getUserId } from "./user";

const ls = window.localStorage;

interface LocalStorageItem<T> {
  set: (value: T) => void;
  get: () => T;
  exists: () => boolean;
  remove: () => void;
}

export interface LocalStorage {
  token: LocalStorageItem<string>;
}

export const localStorage: LocalStorage = {
  token: generateLocalStorageItem("token")
};

function generateLocalStorageItem<T>(key: string): LocalStorageItem<T> {
  return {
    set: (value: T) => ls.setItem(key, String(value)),
    get: () => ls.getItem(key) as T,
    exists: () => Boolean(ls.getItem(key)),
    remove: () => ls.removeItem(key)
  };
}

export const isSubject = (userId: string | undefined) => {
  return userId === getUserId(localStorage.token.get());
};
