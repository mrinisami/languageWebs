import { Grid } from "@mui/material";
import useAxios from "axios-hooks";
import React from "react";
import { request as api, contractRequest } from "../api/routes";
import { useParams, useSearchParams } from "react-router-dom";
import { Requests } from "../api/review";
import Request from "./Request";
import { ContractRequest } from "../api/contractRequest";
import { localStorage } from "../utils/localstorage";
import RequestPublic from "./RequestPublic";

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
  const [, addRequest] = useAxios<ContractRequest>(
    {
      method: "POST"
    },
    { manual: true }
  );
  const handleCreateRequest = async (requestId: number) => {
    await addRequest({ url: contractRequest.createRequest(requestId) });
    refetch();
  };
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
        {data.requests.map((request, i) =>
          localStorage.token.exists() ? (
            <Request request={request} key={i} handleDelete={handleDelete} handleCreateRequest={handleCreateRequest} />
          ) : (
            <RequestPublic request={request} key={i} />
          )
        )}
      </Grid>
    );
  }
  return <></>;
};
