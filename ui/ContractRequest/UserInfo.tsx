import { Avatar, Grid, Typography } from "@mui/material";
import useAxios from "axios-hooks";
import React from "react";
import { UserSearch, getFormattedNameFromApi } from "../utils/user";
import { user } from "../api/routes";
import { useNavigate } from "react-router-dom";
import { routes } from "../routes";

interface Props {
  userId: string;
}
export default (props: Props) => {
  const [{ data }] = useAxios<UserSearch>({
    url: user.getUserInfo(props.userId)
  });
  const navigate = useNavigate();
  if (data) {
    return (
      <Grid
        item
        container
        alignSelf="flex-start"
        onClick={() => navigate(routes.profile(props.userId, "requests"))}
        sx={{ cursor: "pointer" }}
      >
        <Grid item>
          <Avatar sx={{ height: 40, width: 40 }} />
        </Grid>
        <Grid item alignSelf="center">
          <Typography variant="subtitle1">{getFormattedNameFromApi(data.firstName, data.lastName)}</Typography>
        </Grid>
      </Grid>
    );
  }
  return <></>;
};
