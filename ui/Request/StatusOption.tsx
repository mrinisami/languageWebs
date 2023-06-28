import { Checkbox, FormControlLabel, Grid, Typography } from "@mui/material";
import React, { useState } from "react";
import { Status } from "./Filter";
import { useSearchParams } from "react-router-dom";

interface Props {
  updateStatus: (status: Status) => void;
  deleteStatus: (status: Status) => void;
}

export default (props: Props) => {
  const statuses = ["OPEN", "ACCEPTED", "PENDING", "CLOSED"];
  const [searchParams] = useSearchParams();
  const defaultChecks = () => {
    const checks = [...Array(statuses.length).map(() => false)];
    const params = Object.fromEntries(searchParams);
    if (Object.keys(params).includes("status")) {
      const statusParams = searchParams.getAll("status");
      statuses.map((s, i) => {
        if (statusParams.includes(s)) {
          checks[i] = true;
        }
      });
    }
    return checks;
  };
  const [checked, setChecked] = useState<boolean[]>(defaultChecks());
  const handleOnChange = (status: Status) => {
    const newChecks = [...checked];
    newChecks[status.index] = !newChecks[status.index];
    setChecked(newChecks);
    if (newChecks[status.index]) {
      props.updateStatus(status);
    } else {
      props.deleteStatus(status);
    }
  };
  return (
    <Grid item>
      <Grid item>
        <Typography variant="subtitle2" fontWeight="bold">
          Status
        </Typography>
      </Grid>
      {statuses.map((status, i) => (
        <Grid item key={i}>
          <FormControlLabel
            control={<Checkbox checked={checked[i]} onChange={() => handleOnChange({ status, index: i })} />}
            label={status}
          />
        </Grid>
      ))}
    </Grid>
  );
};
