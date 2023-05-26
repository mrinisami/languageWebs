import { Avatar, Grid, Paper, Tab, Tabs, Typography } from "@mui/material";
import { Container } from "@mui/system";
import React, { useState } from "react";
import { getFormattedName, getUserId } from "../utils/user";
import { Navigate, useNavigate } from "react-router-dom";
import { routes } from "../routes";
import LanguageReviews from "./LanguageReviews";

export default () => {
  const navigate = useNavigate();
  const [tabValue, setTabValue] = useState<string>("recentActivity");
  console.log(tabValue);
  return (
    <Container>
      <Paper variant="outlined">
        <Grid container alignItems="center">
          <Grid item container alignSelf="flex-start" xs={12}>
            <Grid item>
              <Avatar sx={{ height: 80, width: 80 }} />
            </Grid>
            <Grid item>
              <Typography variant="h4">{getFormattedName(localStorage.getItem("token"))}</Typography>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <Tabs value={tabValue} onChange={(e: React.SyntheticEvent, value: string) => setTabValue(value)}>
              <Tab label="Recent activity" value="recentActivity" />
              <Tab label="Language reviews" value="languageReviews" />
              <Tab label="Following" value="following" />
            </Tabs>
          </Grid>
          <Grid item xs={12}>
            {renderTab()}
          </Grid>
        </Grid>
      </Paper>
    </Container>
  );
  function renderTab() {
    if (tabValue === "recentActivity") {
      return <></>;
    }
    if (tabValue === "languageReviews") {
      console.log("hi");
      return <LanguageReviews />;
    }
  }
};
