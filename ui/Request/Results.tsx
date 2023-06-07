import { Button, Card, CardContent, Dialog, Grid, IconButton, Typography } from "@mui/material";
import useAxios from "axios-hooks";
import React, { ChangeEvent, ChangeEventHandler, useState } from "react";
import { request as api } from "../api/routes";
import { useLocation, useNavigate, useParams, useSearchParams } from "react-router-dom";
import { Request, Requests } from "../api/review";
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

interface RequestProps {
  request: Request;
  i: number;
  handleDelete: (userId: string, requestId: number) => void;
}

export interface PutParams {
  url: string;
  data: EditRequestDto;
}

interface EditRequestDto {
  price: number;
  dueDate: number | undefined;
}

export default () => {
  const [searchParams] = useSearchParams();
  const [, executeDelete] = useAxios(
    {
      method: "DELETE"
    },
    { manual: true }
  );
  const navigate = useNavigate();
  const userId = useParams().userId;
  const params =
    userId === undefined ? Object.fromEntries(searchParams) : { ...Object.fromEntries(searchParams), userId };
  const [{ data, loading, error }, refetch] = useAxios<Requests>(
    {
      url: api.getFilteredRequests,
      params: params
    },
    { useCache: false }
  );
  const handleDelete = async (userId: string, requestId: number) => {
    await executeDelete({ url: api.deleteRequest(userId, requestId) });
    refetch();
  };
  if (loading) {
    return <p>Loading...</p>;
  }
  if (error) {
    return <p>{error.response?.data}</p>;
  }
  if (data) {
    return (
      <Grid item container rowSpacing={3} direction="column" alignItems="center">
        {data.requests.map((request, i) => (
          <RenderRequest request={request} i={i} key={i} handleDelete={handleDelete} />
        ))}
      </Grid>
    );
  }
  function RenderRequest(props: RequestProps) {
    const request = props.request;
    const [open, setOpen] = useState<boolean>(false);
    const [editMode, setEditMode] = useState<boolean>(false);
    const url = useLocation().pathname;
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
    const confirmDeletion = () => {
      props.handleDelete(request.userDto.id, request.id);
    };
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
      </Grid>
    );
  }
  return <></>;
};
