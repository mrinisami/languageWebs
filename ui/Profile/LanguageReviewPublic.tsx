import React, { useState } from "react";
import { Card, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { languageGrades } from "../api/routes";
import useAxios from "axios-hooks";
import { LanguageAllGrades, LanguagesAllGrades } from "../api/language";
import { useParams } from "react-router-dom";
import AddLanguageDialog from "./AddLanguageDialog";
import LanguageReviewRowPublic from "./LanguageReviewRowPublic";

export default () => {
  const userId: string | undefined = useParams() ? useParams().userId : "";
  const [open, setOpen] = useState<boolean>(false);
  const [{ data, loading, error }] = useAxios<LanguagesAllGrades>({
    url: languageGrades.getUserGrades(userId),
    method: "GET"
  });
  if (error) {
    return <p>{error.response?.data.message}</p>;
  }

  if (loading) {
    return <p>loading...</p>;
  }

  if (data) {
    return (
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell></TableCell>
              <TableCell>Self</TableCell>
              <TableCell>Evaluator</TableCell>
              <TableCell>User</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.languages.map((languageGrade: LanguageAllGrades, i) => (
              <LanguageReviewRowPublic languageGrade={languageGrade} key={i} />
            ))}
          </TableBody>
        </Table>
        <AddLanguageDialog open={open} onClose={() => setOpen(false)} />
      </Card>
    );
  }
  return <></>;
};
