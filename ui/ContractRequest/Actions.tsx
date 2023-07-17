import { Button, Dialog, Grid, IconButton } from "@mui/material";
import React, { useState } from "react";
import CancelIcon from "@mui/icons-material/Cancel";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import useAxios from "axios-hooks";
import { contractRequest } from "../api/routes";
import ContractModal from "./ContractModal";
import WarningIcon from "@mui/icons-material/Warning";

interface Props {
  id: number;
  refetch: () => void;
}

export default (props: Props) => {
  const [open, setOpen] = useState<boolean>(false);
  const [openCancel, setOpenCancel] = useState<boolean>(false);
  const [{ data }, executePut] = useAxios(
    {
      url: contractRequest.renderDecision(props.id),
      method: "PUT"
    },
    { manual: true }
  );
  const handleContractAcceptance = async () => {
    await executePut({ data: { status: "ACCEPTED" } });
    props.refetch();
    setOpen(false);
  };
  const handleContractDenial = async () => {
    await executePut({ data: { status: "DENIED" } });
    props.refetch();
    setOpenCancel(false);
  };
  return (
    <Grid item container>
      <Grid item>
        <IconButton color="success" onClick={() => setOpen(true)}>
          <CheckCircleIcon />
        </IconButton>
      </Grid>
      <Grid item>
        <IconButton color="error" onClick={() => setOpenCancel(true)}>
          <CancelIcon />
        </IconButton>
      </Grid>
      <ContractModal open={open} onClose={() => setOpen(false)} handleConfirm={handleContractAcceptance} />
      <Dialog open={openCancel} onClose={() => setOpenCancel(true)}>
        <Grid container spacing={2}>
          <Grid item>
            <Grid item container>
              <Grid item>
                <WarningIcon color="warning" />
              </Grid>

              <Grid item>This denial is irreversible. Do you still wish to deny this request?</Grid>
            </Grid>
          </Grid>
          <Grid item>
            <Button onClick={handleContractDenial}>Yes</Button>
          </Grid>
        </Grid>
      </Dialog>
    </Grid>
  );
};
