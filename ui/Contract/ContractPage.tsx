import { Grid } from "@mui/material";
import React from "react";
import ContractLoader from "./ContractLoader";

export default () => {
  return (
    <Grid container>
      <Grid item></Grid>
      <Grid item>
        <ContractLoader />
      </Grid>
    </Grid>
  );
};
