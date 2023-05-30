import React, { createContext } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import { configureAxiosHeaders } from "./utils/axios";
import { localStorage } from "./utils/localstorage";
import { ProvideToken } from "./context/TokenContext";

configureAxiosHeaders(localStorage.token.get());

const root = ReactDOM.createRoot(document.getElementById("app") as HTMLElement);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);
