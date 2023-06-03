import { Avatar, Grid } from "@mui/material";
import React, { useEffect } from "react";
import UserInfo from "./UserInfo";
import SearchUsers from "./SearchUsers";
import { useTokenContext } from "../context/TokenContext";
import UploadText from "./UploadText";

export default () => {
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
      <Grid item>
        <UploadText />
      </Grid>
      <Grid item>
        <UserInfo />
      </Grid>
    </Grid>
  );
};
