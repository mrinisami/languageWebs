import React, { createContext } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import { configureAxiosHeaders } from "./utils/axios";
import { localStorage } from "./utils/localstorage";
import { ProvideToken } from "./context/TokenContext";
import { ThemeProvider } from "@emotion/react";
import { themeDark } from "./utils/theme";
import { CssBaseline } from "@mui/material";

configureAxiosHeaders(localStorage.token.get());

const root = ReactDOM.createRoot(document.getElementById("app") as HTMLElement);
root.render(
  <React.StrictMode>
    <ThemeProvider theme={themeDark}>
      <CssBaseline />
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </ThemeProvider>
  </React.StrictMode>
);
