import { Avatar, Grid, IconButton } from "@mui/material";
import React, { useEffect } from "react";
import UserInfo from "./UserInfo";
import SearchUsers from "./SearchUsers";
import UploadText from "./UploadText";
import RequestPageIcon from "@mui/icons-material/RequestPage";
import { useNavigate } from "react-router-dom";
import { routes } from "../routes";

export default () => {
  const navigate = useNavigate();
  return (
    <Grid container justifyContent="space-between" sx={{ mb: 4 }}>
      <Grid item container spacing={1} xs={5}>
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
        <Grid item>
          <IconButton color="primary" size="large" onClick={() => navigate(routes.request)}>
            <RequestPageIcon sx={{ fontSize: 30 }} />
          </IconButton>
        </Grid>
        <UploadText />
      </Grid>
      <Grid item>
        <UserInfo />
      </Grid>
    </Grid>
  );
};
