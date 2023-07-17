import Axios from "axios";
import { configure } from "axios-hooks";
import { env } from "./env";

export const configureAxiosHeaders = (token?: string) => {
  let headers = {};
  if (token !== null) {
    headers = { Authorization: `Bearer ${token}` };
  }

  configure({ axios: Axios.create({ baseURL: env.api.baseUri, headers }) });
};
