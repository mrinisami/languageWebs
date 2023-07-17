import { Dialog, DialogTitle, Grid, IconButton } from "@mui/material";
import React, { useState } from "react";
import { DateCalendar, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import dayjs, { Dayjs } from "dayjs";

interface Props {
  open: boolean;
  onClose: () => void;
  confirmRequest: (date: Dayjs | null) => void;
}
export default (props: Props) => {
  const [date, setDate] = useState<Dayjs | null>(dayjs(new Date()));

  return (
    <Dialog open={props.open} onClose={props.onClose}>
      <DialogTitle>Request for an extension</DialogTitle>
      <Grid container>
        <Grid item>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateCalendar onChange={(newValue: Dayjs | null) => setDate(newValue)} />
          </LocalizationProvider>
        </Grid>
        <Grid item alignSelf="flex-end">
          <IconButton color="success" onClick={() => props.confirmRequest(date)}>
            <CheckCircleIcon />
          </IconButton>
        </Grid>
      </Grid>
    </Dialog>
  );
};
