import { Grid } from "@mui/material";
import React from "react";
import Filter from "./Filter";
import Results, { PutParams } from "./Results";
import { request } from "../api/routes";
import useAxios from "axios-hooks";
import { RequestLanguagesStats } from "../api/review";

export default () => {
  const [{ data }] = useAxios<RequestLanguagesStats>({
    url: request.getLanguageStats
  });
  if (data) {
    return (
      <Grid container spacing={1}>
        <Grid item xs={2}>
          <Filter languageStats={data} />
        </Grid>
        <Grid item container xs>
          <Results />
        </Grid>
      </Grid>
    );
  }
  return <></>;
};
