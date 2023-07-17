import { Route, Routes } from "react-router-dom";
import React from "react";
import { routes } from "../routes";
import Home from "../Home/Home";
import { Grid } from "@mui/material";
import NavBar from "../Navigation/NavBar";
import Profile from "../Profile/Profile";
import Login from "../Auth/Login";
import AddRequest from "../Request/AddRequest";
import RequestPage from "../Request/Page";
import RequestLoader from "../ContractRequest/RequestLoader";
import { localStorage } from "../utils/localstorage";
import PublicProfile from "../Profile/PublicProfile";

export const App = () => {
  return (
    <Grid container>
      <NavBar />
      <Routes>
        <Route path={routes.request} element={<RequestPage />} />
        <Route path={routes.home} element={<Home />} />
        <Route path={routes.login} element={<Login />} />
        <Route
          path="/users/:userId/profile/:tabValue"
          element={localStorage.token.exists() ? <Profile /> : <PublicProfile />}
        />
        <Route path={routes.addRequest} element={<AddRequest />} />
        <Route path="/requests/:requestId/edit" element={<AddRequest />} />
        <Route path="/requests/:requestId/contract-request" element={<RequestLoader />} />
      </Routes>
    </Grid>
  );
};
