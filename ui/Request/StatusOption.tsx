import { Checkbox, FormControlLabel, Grid, Typography } from "@mui/material";
import React, { useState } from "react";
import { Status } from "./Filter";
import { useSearchParams } from "react-router-dom";

interface Props {
  updateStatus: (contractStatus: Status) => void;
  deleteStatus: (contractStatus: Status) => void;
}

export default (props: Props) => {
  const contractStatuses = ["OPEN", "ACCEPTED", "PENDING", "CLOSED"];
  const [searchParams] = useSearchParams();
  const defaultChecks = () => {
    const checks = [...Array(contractStatuses.length).map(() => false)];
    const params = Object.fromEntries(searchParams);
    if (Object.keys(params).includes("contractStatus")) {
      const statusParams = searchParams.getAll("contractStatus");
      contractStatuses.map((s, i) => {
        if (statusParams.includes(s)) {
          checks[i] = true;
        }
      });
    }
    return checks;
  };
  const [checked, setChecked] = useState<boolean[]>(defaultChecks());
  const handleOnChange = (contractStatus: Status) => {
    const newChecks = [...checked];
    newChecks[contractStatus.index] = !newChecks[contractStatus.index];
    setChecked(newChecks);
    if (newChecks[contractStatus.index]) {
      props.updateStatus(contractStatus);
    } else {
      props.deleteStatus(contractStatus);
    }
  };
  return (
    <Grid item>
      <Grid item>
        <Typography variant="subtitle2" fontWeight="bold">
          Status
        </Typography>
      </Grid>
      {contractStatuses.map((contractStatus, i) => (
        <Grid item key={i}>
          <FormControlLabel
            control={<Checkbox checked={checked[i]} onChange={() => handleOnChange({ contractStatus, index: i })} />}
            label={contractStatus}
          />
        </Grid>
      ))}
    </Grid>
  );
};
