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
  handleDelete: (userId: string, requestId: number) => void;
  handleCreateRequest: (requestId: number) => void;
}

export default (props: RequestProps) => {
  const request = props.request;
  const [{ data, loading }] = useAxios<ContractRequest>(
    {
      url: contractRequest.getContractRequest,
      params: { requestId: request.id, userId: request.userDto.id }
    },
    { useCache: false }
  );
  const [openCreateContractRequest, setOpenCreateContractRequest] = useState<boolean>(false);
  const [open, setOpen] = useState<boolean>(false);
  const [editMode, setEditMode] = useState<boolean>(false);
  const url = useLocation().pathname;
  const showContractLink: boolean = props.request.userDto.id === getUserId(localStorage.token.get());
  const navigate = useNavigate();
  const handleDeleteRequest = () => {
    setOpen(true);
  };
  const handleEdit = () => {
    if (url !== routes.profile(request.userDto.id, "requests")) {
      navigate(routes.profile(request.userDto.id, "requests"));
    }

    setEditMode(true);
  };
  const handleCreateRequest = () => {
    props.handleCreateRequest(props.request.id);
  };
  const confirmDeletion = () => {
    props.handleDelete(request.userDto.id, request.id);
  };
  if (!loading) {
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
                    <Grid item>
                      {request.userDto.id === getUserId(localStorage.token.get()) ? (
                        editMode ? (
                          <IconButton onClick={handleDeleteRequest}>
                            <DeleteIcon />
                          </IconButton>
                        ) : (
                          <IconButton onClick={handleEdit}>
                            <EditIcon />
                          </IconButton>
                        )
                      ) : (
                        <IconButton disabled>
                          <EditIcon sx={{ opacity: 0 }}></EditIcon>
                        </IconButton>
                      )}
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
                <RequestInfo request={request} editable={editMode} />
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

                <Grid item>
                  <Button
                    startIcon={<AddCircleIcon color="primary" />}
                    onClick={() => setOpenCreateContractRequest(!openCreateContractRequest)}
                    sx={{ visibility: showContractLink || data ? "hidden" : "visible" }}
                  >
                    Select
                  </Button>
                  <Chip
                    label={`Contract request: ` + data?.status}
                    sx={{ visibility: data ? "visible" : "hidden" }}
                    variant="outlined"
                  />
                </Grid>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
        <Dialog open={open} onClose={() => setOpen(false)}>
          <Grid container alignItems="center" spacing={2}>
            <Grid item>
              <Typography>Confirm Deletion</Typography>
            </Grid>
            <Grid item>
              <IconButton onClick={confirmDeletion}>
                <CheckCircleIcon color="primary" />
              </IconButton>
            </Grid>
          </Grid>
        </Dialog>
        <ContractModal
          open={openCreateContractRequest}
          handleConfirm={handleCreateRequest}
          onClose={() => setOpenCreateContractRequest(false)}
        />
      </Grid>
    );
  }
  return <></>;
};
