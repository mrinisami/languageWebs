import { Dialog, DialogTitle, Grid, IconButton, Typography } from "@mui/material";
import React from "react";
import CancelIcon from "@mui/icons-material/Cancel";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

interface Props {
  date: number;
  open: boolean;
  confirm: (status: string) => void;
  onClose: () => void;
}
export default (props: Props) => {
  return (
    <Dialog open={props.open} onClose={() => props.onClose()}>
      <DialogTitle>Will you accept this extension?</DialogTitle>
      <Grid container alignItems="center" justifyContent="space-between">
        <Grid item>
          <Typography>{new Date(props.date).toDateString()}</Typography>
        </Grid>
        <Grid item>
          <Grid item container>
            <Grid item>
              <IconButton color="success" onClick={() => props.confirm("ACCEPTED")}>
                <CheckCircleIcon />
              </IconButton>
            </Grid>
            <Grid item>
              <IconButton color="error" onClick={() => props.confirm("DENIED")}>
                <CancelIcon />
              </IconButton>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Dialog>
  );
};
