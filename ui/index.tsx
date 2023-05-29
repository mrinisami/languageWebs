import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import Axios from "axios";
import { configure } from "axios-hooks";
import { localStorage } from "./utils/localstorage";

const axios = Axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: { Authorization: `Bearer ${localStorage.token.get("")}` }
});

configure({ axios });

const root = ReactDOM.createRoot(document.getElementById("app") as HTMLElement);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);
