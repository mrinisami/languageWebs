import { Checkbox, FormControlLabel, Grid, Radio, Typography } from "@mui/material";
import React, { useState } from "react";
import { addDaysToTimestamp } from "../utils/functions";
import { useSearchParams } from "react-router-dom";

export interface DateInfo {
  days: number;
  label: string;
  index: number;
}

interface Props {
  addDueDate: (date: DateInfo) => void;
  removeDueDate: () => void;
}

export default (props: Props) => {
  const dates = [
    { label: "in 1 week", days: 7 },
    { label: "in 2 weeks", days: 14 },
    { label: "in 1 month", days: 30 }
  ];
  const [checked, setChecked] = useState<number | null>(null);
  const handleOnChange = (date: DateInfo) => {
    if (checked === date.index) {
      setChecked(null);
      props.removeDueDate();
    } else {
      setChecked(date.index);
      props.addDueDate(date);
    }
  };
  return (
    <Grid item>
      <Grid item>
        <Typography variant="subtitle2" fontWeight="bold">
          Due Date
        </Typography>
      </Grid>
      {dates.map((date, i) => (
        <Grid item key={i}>
          <FormControlLabel
            control={<Radio checked={checked === i} onClick={() => handleOnChange({ ...date, index: i })} />}
            label={date.label}
          />
        </Grid>
      ))}
    </Grid>
  );
};
