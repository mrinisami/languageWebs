import useAxios from "axios-hooks";
import React from "react";
import { Request } from "../api/review";
import { contractRequest, request } from "../api/routes";
import { useParams } from "react-router-dom";
import { Card, Grid } from "@mui/material";
import RequestPage from "../Request/Request";
import { ContractRequest as ContractRequestInterface, ContractRequests } from "../api/contractRequest";
import ContractRequest from "./ContractRequest";
import { Container } from "@mui/system";

export default () => {
  const requestId: number | undefined = Number(useParams().requestId);
  const [{ data, loading, error }] = useAxios<Request>({
    url: request.getRequest(requestId)
  });
  const [{ data: contractRequests }, refetch] = useAxios<ContractRequests>(
    {
      url: contractRequest.getContractRequests,
      params: { requestId: requestId }
    },
    { useCache: false }
  );
  const handleRefetch = () => {
    refetch();
  };
  if (data) {
    return (
      <Container maxWidth="md">
        <Grid container direction="column" rowSpacing={2}>
          <Grid item>
            <RequestPage request={data} />
          </Grid>
          {contractRequests?.contractRequests?.map((req: ContractRequestInterface, i: number) => (
            <Grid item key={i}>
              <Card>
                <Grid item container>
                  <ContractRequest
                    request={req}
                    refetch={handleRefetch}
                    params={{
                      refLanguage: data.sourceLanguage,
                      translatedLanguage: data.translatedLanguage,
                      userId: req.userId
                    }}
                  />
                </Grid>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    );
  }
  return <></>;
};
