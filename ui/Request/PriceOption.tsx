import { Button, Grid, TextField, Typography } from "@mui/material";
import React from "react";
import { prices } from "../utils/priceByWord";

export default () => {
  const bracket = [0.5, 0.75, 1.0, 1.25];
  return (
    <Grid item>
      <Grid item>
        <Typography variant="subtitle2" fontWeight="bold">
          Price per word
        </Typography>
      </Grid>
      <Grid item>
        <Typography variant="subtitle2">{`Under $${bracket.at(0)?.toFixed(2)}`}</Typography>
      </Grid>
      {bracket.slice(0, -1).map((price, i) => (
        <Grid item key={i}>
          <Typography variant="subtitle2">{`$${price.toFixed(2)} to $${bracket.at(i + 1)?.toFixed(2)}`}</Typography>
        </Grid>
      ))}
      <Grid item>
        <Typography variant="subtitle2">{`$${bracket.at(bracket.length - 1)?.toFixed(2)} & Above`}</Typography>
      </Grid>
      <Grid item container spacing={1} alignItems="center">
        <Grid item xs={2}>
          <TextField label="Min" size="small" />
        </Grid>
        <Grid item xs={2}>
          <TextField label="Max" size="small" />
        </Grid>
        <Grid item xs={2}>
          <Button>Go</Button>
        </Grid>
      </Grid>
    </Grid>
  );
};
