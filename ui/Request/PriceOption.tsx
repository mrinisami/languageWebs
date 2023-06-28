import { Button, Checkbox, FormControlLabel, Grid, TextField, Typography } from "@mui/material";
import React, { useState } from "react";
import { Prices } from "./Filter";
import { useSearchParams } from "react-router-dom";

interface Props {
  updatePrices: (price: Prices) => void;
  deletePrices: (index: number) => void;
}

export default (props: Props) => {
  const [searchParams] = useSearchParams();
  const bracket = [0.5, 0.75, 1.0, 1.25];
  const defaultChecks = () => {
    const checks = [...Array(bracket.length + 1).keys()].map((i) => false);
    const params = Object.fromEntries(searchParams);
    if (Object.keys(params).includes("min")) {
      const min = Number(params.min);
      const max = Number(params.max);
      if (max === bracket[0]) {
        checks[0] = true;
        return checks;
      }
      if (min === bracket[bracket.length - 1]) {
        checks[checks.length - 1] = true;
        return checks;
      }
      checks[bracket.indexOf(min) + 1] = true;
    }
    return checks;
  };
  defaultChecks();

  const [checked, setChecked] = useState<boolean[]>(defaultChecks());
  const priceJump = 0.25;
  const handleOnChange = (index: number, min: number | undefined, max: number | undefined) => {
    const newChecked = [...checked];
    newChecked[index] = !newChecked[index];
    setChecked(newChecked);
    if (!newChecked[index]) {
      return props.deletePrices(index);
    }
    return props.updatePrices({ index, min, max });
  };
  return (
    <Grid item>
      <Grid item>
        <Typography variant="subtitle2" fontWeight="bold">
          Price per word
        </Typography>
      </Grid>
      <Grid item>
        <FormControlLabel
          control={<Checkbox checked={checked?.at(0)} />}
          label={`Less than $${bracket.at(0)?.toFixed(2)}`}
          onChange={() => handleOnChange(0, 0, bracket?.at(0))}
        />
      </Grid>
      {bracket.slice(0, -1).map((price, i) => (
        <Grid item key={i}>
          <FormControlLabel
            control={<Checkbox checked={checked?.at(i + 1)} />}
            onChange={() => handleOnChange(i + 1, price, price + priceJump)}
            label={`$${price.toFixed(2)} to $${bracket.at(i + 1)?.toFixed(2)}`}
          />
        </Grid>
      ))}
      <Grid item>
        <FormControlLabel
          control={<Checkbox checked={checked?.at(-1)} />}
          label={`$${bracket.at(bracket.length - 1)?.toFixed(2)} & Above`}
          onChange={() => handleOnChange(bracket.length, bracket.at(-1), undefined)}
        />
      </Grid>
    </Grid>
  );
};
