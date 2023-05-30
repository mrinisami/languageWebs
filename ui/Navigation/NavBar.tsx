import { Avatar, Grid } from "@mui/material";
import React, { useEffect } from "react";
import UserInfo from "./UserInfo";
import SearchUsers from "./SearchUsers";
import { useTokenContext } from "../context/TokenContext";

export default () => {
  return (
    <Grid container justifyContent="space-between" sx={{ mb: 4 }}>
      <Grid item container spacing={1} xs={2}>
        <Grid item>
          <Avatar />
        </Grid>
        <Grid item>
          <Avatar />
        </Grid>
      </Grid>
      <Grid item xs={3}>
        <SearchUsers />
      </Grid>
      <Grid item>
        <UserInfo />
      </Grid>
    </Grid>
  );
};
