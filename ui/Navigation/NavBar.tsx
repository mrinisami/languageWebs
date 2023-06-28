import { Avatar, Grid, IconButton, Paper, Tab, Tabs } from "@mui/material";
import React, { useEffect, useState } from "react";
import UserInfo from "./UserInfo";
import SearchUsers from "./SearchUsers";
import RequestPageIcon from "@mui/icons-material/RequestPage";
import { useLocation, useNavigate } from "react-router-dom";
import { routes } from "../routes";
import HomeIcon from "@mui/icons-material/Home";
import NoteAddIcon from "@mui/icons-material/NoteAdd";

export default () => {
  const navigate = useNavigate();
  return (
    <Paper sx={{ width: "100%", p: 0, mb: 4 }}>
      <Grid container justifyContent="space-between" alignItems="center">
        <Grid item container spacing={1} xs={5} alignItems="center">
          <Grid item>
            <Avatar />
          </Grid>
          <Grid item>
            <Avatar />
          </Grid>
          <Grid item xs={5}>
            <SearchUsers />
          </Grid>
        </Grid>
        <Grid item container spacing={1} xs={3}>
          <Tabs value={useLocation().pathname}>
            <Tab icon={<HomeIcon sx={{ fontSize: 30 }} />} value={routes.home} onClick={() => navigate("/")} />
            <Tab
              icon={<RequestPageIcon sx={{ fontSize: 30 }} />}
              value={routes.request}
              onClick={() => navigate(routes.request)}
            />
            <Tab
              icon={<NoteAddIcon sx={{ fontSize: 30 }} />}
              value={routes.addRequest}
              onClick={() => navigate(routes.addRequest)}
            />
          </Tabs>
        </Grid>
        <Grid item>
          <UserInfo />
        </Grid>
      </Grid>
    </Paper>
  );
};
