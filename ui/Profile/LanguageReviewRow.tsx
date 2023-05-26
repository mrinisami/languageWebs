import { TableRow, TableCell, Grid } from "@mui/material";
import React from "react";
import { LanguageAllGrades } from "../api/language";

interface Props {
  languageGrade: LanguageAllGrades;
}

export default (props: Props) => {
  return (
    <TableRow>
      <TableCell>
        <Grid item>
          <img src="/static/flags/ae.svg" width="30" />
        </Grid>
      </TableCell>
      <TableCell>
        <Grid item>{props.languageGrade.selfAssessment}</Grid>
      </TableCell>
      <TableCell>
        <Grid item>{props.languageGrade.evaluatorGrade ? props.languageGrade.evaluatorGrade : "N/A"}</Grid>
      </TableCell>
      <TableCell>
        <Grid item>{props.languageGrade.userGrade}</Grid>
      </TableCell>
    </TableRow>
  );
};
