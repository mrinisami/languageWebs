import React from "react";
import { Avatar, Card, Grid, Paper, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { endpoints } from "../api/endpoints";
import { getUserId } from "../utils/user";
import useAxios from "axios-hooks";
import { LanguagesAllGrades } from "../api/language";
import { CircleFlag } from "react-circle-flags";
import LanguageReviewRow from "./LanguageReviewRow";

export default () => {
  const [{ data, loading, error }] = useAxios<LanguagesAllGrades>({
    baseURL: process.env.REACT_APP_API_BASE_URL,
    url: endpoints.getUserGrades(getUserId(localStorage.getItem("token"))),
    method: "GET"
  });
  if (error) {
    console.log(error);
  }

  if (loading) {
    return <p>loading...</p>;
  }

  if (data) {
    return (
      <Grid item container sx={{ pt: 3 }}>
        <Paper>
          <Grid item container>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Language</TableCell>
                  <TableCell>Self</TableCell>
                  <TableCell>Evaluator</TableCell>
                  <TableCell>User</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {data.languages.map((languageGrade, i) => (
                  <LanguageReviewRow languageGrade={languageGrade} key={i} />
                ))}
              </TableBody>
            </Table>
          </Grid>
        </Paper>
      </Grid>
    );
  }
  return <></>;
};
