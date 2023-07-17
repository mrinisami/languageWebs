import React from "react";
import { ContractRequest } from "../api/contractRequest";
import { Box, Chip, CircularProgress, Grid, Typography } from "@mui/material";
import UserInfo from "./UserInfo";
import UserLanguageGrade from "./UserLanguageGrade";
import Actions from "./Actions";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import Status from "./Status";

export interface Params {
  userId: string;
  refLanguage: string;
  translatedLanguage: string;
}
interface Props {
  request: ContractRequest;
  refetch: () => void;
  params: Params;
}

export default (props: Props) => {
  return (
    <Grid container alignItems="center" justifyContent="space-between">
      <Grid item>
        <Grid item container alignItems="center" justifyContent="space-evenly" spacing={3}>
          <Grid item>
            <UserInfo userId={props.params.userId} />
          </Grid>
          <Grid item>
            <UserLanguageGrade
              userId={props.params.userId}
              refLanguage={props.params.refLanguage}
              translatedLanguage={props.params.translatedLanguage}
            />
          </Grid>
          <Grid item>
            <Chip label={props.request.status} icon={<Status status={props.request.status} />} variant="outlined" />
          </Grid>
          <Grid item>
            <Chip label={props.request.createdAt?.toString()} icon={<CalendarMonthIcon />} variant="outlined" />
          </Grid>
          <Grid item>See reviews (40)</Grid>
        </Grid>
      </Grid>
      {props.request.status === "DENIED" ? (
        <></>
      ) : (
        <Grid item>
          <Actions id={props.request.id} refetch={props.refetch} />
        </Grid>
      )}
    </Grid>
  );
};
