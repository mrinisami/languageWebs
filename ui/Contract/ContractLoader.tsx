import useAxios from "axios-hooks";
import React from "react";
import { contract } from "../api/routes";
import { useParams } from "react-router-dom";
import { Contracts } from "../api/contract";
import Contract from "./Contract";
import { Grid } from "@mui/material";

export default () => {
  const userId = useParams().userId;
  const [{ data }, refetch] = useAxios<Contracts>({
    url: contract.getContracts,
    params: { contractedUserId: userId, requestUserId: userId }
  });
  if (data) {
    const contracts = data.contracts;
    console.log(contracts);
    return (
      <Grid item container>
        {contracts.map((contract) => (
          <Grid item key={contract.id}>
            <Contract contract={contract} refetch={refetch} />
          </Grid>
        ))}
      </Grid>
    );
  }
  return <></>;
};
