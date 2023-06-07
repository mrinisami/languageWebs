import { Avatar, Grid, Paper, Tab, Tabs, Typography } from "@mui/material";
import { Container } from "@mui/system";
import React, { useEffect, useState } from "react";
import { getFormattedNameFromApi } from "../utils/user";
import { Navigate, createSearchParams, useNavigate, useParams, useSearchParams } from "react-router-dom";
import LanguageReviews from "./LanguageReviews";
import useAxios from "axios-hooks";
import { user } from "../api/routes";
import { routes } from "../routes";
import { localStorage } from "../utils/localstorage";
import LanguageReviewPublic from "./LanguageReviewPublic";
import Request from "../Request/Request";

export default () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const userId: string | undefined = useParams() !== undefined ? useParams().userId : "";
  const navigate = useNavigate();
  if (useParams().tabValue === undefined) {
    navigate(routes.profile(userId, "recentActivity"));
  }
  const [{ data, loading, error }] = useAxios({
    url: user.getUserInfo(userId),
    method: "GET"
  });
  const [tabValue, setTabValue] = useState<string | undefined>(useParams().tabValue);
  useEffect(() => {
    navigate(routes.profile(userId, tabValue));
  }, [tabValue]);
  if (error) {
    return <p>{error.response?.data.message}</p>;
  }
  if (loading) {
    return <p>Loading...</p>;
  }
  if (data) {
    return (
      <Container maxWidth="md">
        <Paper variant="outlined" sx={{ p: 2 }}>
          <Grid container alignItems="center">
            <Grid item container alignSelf="flex-start">
              <Grid item>
                <Avatar sx={{ height: 80, width: 80 }} />
              </Grid>
              <Grid item alignSelf="center">
                <Typography variant="h4">{getFormattedNameFromApi(data.firstName, data.lastName)}</Typography>
              </Grid>
            </Grid>
            <Grid item container xs={12} justifyContent="center" rowSpacing={2}>
              <Grid item>
                <Tabs value={tabValue} onChange={(e: React.SyntheticEvent, value: string) => setTabValue(value)}>
                  <Tab label="Recent activity" value="recentActivity" />
                  <Tab label="Language reviews" value="languageReviews" />
                  <Tab label="Following" value="following" />
                  <Tab label="Requests" value="requests" />
                </Tabs>
              </Grid>
              <Grid item xs={12}>
                {renderTab()}
              </Grid>
            </Grid>
          </Grid>
        </Paper>
      </Container>
    );
  }
  function renderTab() {
    if (tabValue === "recentActivity") {
      return <></>;
    }
    if (tabValue === "languageReviews") {
      return localStorage.token.exists() ? <LanguageReviews /> : <LanguageReviewPublic />;
    }
    if (tabValue === "requests") {
      //setSearchParams({ userId });
      return <Request />;
    }
  }
};
