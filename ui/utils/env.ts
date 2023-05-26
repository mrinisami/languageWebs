interface Api {
  baseUri: string;
}

interface Env {
  api: Api;
  isProd: boolean;
  isDev: boolean;
}

export const env: Env = {
  isProd: process.env.NODE_ENV === "production",
  isDev: process.env.NODE_ENV === "development",
  api: {
    baseUri: ensureEnvExists(process.env.REACT_APP_API_BASE_URL, "API_BASE_URL")
  }
};

function ensureEnvExists(value: string | undefined, name: string): string {
  if (value == undefined) {
    throw Error(`Missing env [REACT_APP_${name}]`);
  }

  return value;
}
