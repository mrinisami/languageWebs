import { Chip, Dialog, Grid, Typography } from "@mui/material";
import React from "react";
import CheckIcon from "@mui/icons-material/Check";

interface Props {
  open: boolean;
  updateContract: (status: string) => void;
}

export default (props: Props) => {
  return (
    <Dialog open={props.open} onClose={() => props.updateContract("PENDING")}>
      <Grid container>
        <Grid item>
          <Typography>
            Is this your final file deposit? By Clicking Yes, you notify your client of the completion of the contract.
          </Typography>
        </Grid>
        <Grid item>
          <Chip label="Yes" icon={<CheckIcon color="success" />} onClick={() => props.updateContract("COMPLETED")} />
        </Grid>
      </Grid>
    </Dialog>
  );
};
