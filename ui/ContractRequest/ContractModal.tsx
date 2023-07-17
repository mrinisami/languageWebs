import { Button, Checkbox, Dialog, DialogTitle, FormControlLabel, Grid, Typography } from "@mui/material";
import React, { useState } from "react";

interface Props {
  open: boolean;
  onClose: () => void;
  handleConfirm: () => void;
}

export default (props: Props) => {
  const [checked, setChecked] = useState<boolean>(false);
  const handleAgree = () => {
    if (!checked) {
      //hover message must agree
      return;
    }
    props.handleConfirm();
  };
  return (
    <Dialog open={props.open} onClose={() => props.onClose()}>
      <DialogTitle>
        <Typography>Contract Requirements</Typography>
      </DialogTitle>
      <Grid container>
        <Grid item>1. blablabla</Grid>
        <Grid item>2. boubouboub</Grid>
        <Grid item container justifyContent="space-between">
          <Grid item>
            <FormControlLabel
              control={<Checkbox checked={checked} onClick={() => setChecked(!checked)} />}
              label="You accept the contract conditions"
            />
          </Grid>
          <Grid item>
            <Button onClick={handleAgree}>I Agree</Button>
          </Grid>
        </Grid>
      </Grid>
    </Dialog>
  );
};
