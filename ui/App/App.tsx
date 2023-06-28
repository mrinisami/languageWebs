import { Route, Routes } from "react-router-dom";
import React from "react";
import { routes } from "../routes";
import Home from "../Home/Home";
import { Grid } from "@mui/material";
import NavBar from "../Navigation/NavBar";
import Profile from "../Profile/Profile";
import Login from "../Auth/Login";
import Request from "../Request/Request";
import AddRequest from "../Request/AddRequest";
import RequestPage from "../Request/RequestPage";

export const App = () => {
  return (
    <Grid container>
      <NavBar />
      <Routes>
        <Route path={routes.request} element={<RequestPage />} />
        <Route path={routes.home} element={<Home />} />
        <Route path={routes.login} element={<Login />} />
        <Route path="/users/:userId/profile/:tabValue" element={<Profile />} />
        <Route path={routes.addRequest} element={<AddRequest />} />
        <Route path="/requests/:requestId/edit" element={<AddRequest />} />
      </Routes>
    </Grid>
  );
};
