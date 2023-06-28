import { Avatar, Grid } from "@mui/material";
import React from "react";
import { UserSearch } from "../api/user";
import { useNavigate } from "react-router-dom";
import { routes } from "../routes";

interface Props {
  userInfo: UserSearch;
}
export default (props: Props) => {
  const navigate = useNavigate();
  return (
    <Grid
      item
      container
      alignItems="center"
      spacing={1}
      onClick={() => navigate(routes.profile(props.userInfo.id, "requests"))}
      sx={{ cursor: "pointer" }}
    >
      <Grid item>
        <Avatar sx={{ height: 50, width: 50 }} />
      </Grid>
      <Grid item>{`${props.userInfo.firstName} ${props.userInfo.lastName}`}</Grid>
    </Grid>
  );
};
