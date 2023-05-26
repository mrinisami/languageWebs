import { Avatar, Grid } from "@mui/material";
import React, { useEffect } from "react";
import UserInfo from "./UserInfo";

export default () => {
  return (
    <Grid container justifyContent="space-between">
      <Grid item container spacing={1} xs={2}>
        <Grid item>
          <Avatar />
        </Grid>
        <Grid item>
          <Avatar />
        </Grid>
      </Grid>
      <Grid item xs={3}>
        <></>
      </Grid>
      <Grid item>
        <UserInfo />
      </Grid>
    </Grid>
  );
};
