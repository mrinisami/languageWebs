import React, { useState } from "react";
import { languageToFlag } from "../api/language";
import ArrowRightAltIcon from "@mui/icons-material/ArrowRightAlt";
import UserInfo from "./UserInfo";
import RequestInfo from "./RequestInfo";
import EditIcon from "@mui/icons-material/Edit";
import { localStorage } from "../utils/localstorage";
import { getUserId } from "../utils/user";
import DeleteIcon from "@mui/icons-material/Delete";
import { routes } from "../routes";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import { Button, Card, CardContent, Chip, Dialog, Grid, IconButton, Typography } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { Request } from "../api/review";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import ContractModal from "../ContractRequest/ContractModal";
import useAxios from "axios-hooks";
import { contractRequest } from "../api/routes";
import { ContractRequest } from "../api/contractRequest";
import LaunchIcon from "@mui/icons-material/Launch";

interface RequestProps {
  request: Request;
}

export default (props: RequestProps) => {
  const request = props.request;
  const url = useLocation().pathname;
  const showContractLink: boolean = props.request.userDto.id === getUserId(localStorage.token.get());
  const navigate = useNavigate();

  return (
    <Grid item>
      <Card>
        <CardContent>
          <Grid container>
            <Grid item container justifyContent="space-between">
              <Grid item>
                <img src={`${languageToFlag[request.sourceLanguage.toLowerCase()]}`} width="25" />
                <ArrowRightAltIcon />
                <img src={`${languageToFlag[request.translatedLanguage.toLowerCase()]}`} width="25" />
              </Grid>
              <Grid item>
                <Grid item container spacing={1} justifyContent="justify-content" alignItems="center">
                  <Grid item>
                    <Typography>{`Last modified : ${request.modifiedAt}`}</Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
          <Grid item container alignItems="center" spacing={3}>
            <Grid item>
              <UserInfo userInfo={request.userDto} />
            </Grid>
            <Grid item>
              <RequestInfo request={request} />
            </Grid>
            <Grid item container alignItems="center" justifyContent="space-between">
              <Grid item>
                <Grid item container alignItems="center" sx={{ visibility: showContractLink ? "visible" : "hidden" }}>
                  <Grid item>
                    <Typography
                      variant="subtitle2"
                      color="primary"
                      onClick={() => navigate(routes.contractRequests(request.id))}
                      sx={{ cursor: "pointer" }}
                    >
                      View Contract Requests
                    </Typography>
                  </Grid>

                  <Grid item>
                    <LaunchIcon
                      color="primary"
                      onClick={() => navigate(routes.contractRequests(request.id))}
                      sx={{ cursor: "pointer" }}
                    />
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </Grid>
  );
};
