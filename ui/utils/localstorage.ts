const ls = window.localStorage;

interface LocalStorageItem<T> {
  set: (value: T) => void;
  get: (defaultValue: T) => T;
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
    get: (defaultValue: T) => (ls.getItem(key) || defaultValue) as T,
    remove: () => ls.removeItem(key)
  };
}
