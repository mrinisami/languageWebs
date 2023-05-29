import React, { useState } from "react";
import {
  Avatar,
  Button,
  Card,
  Grid,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@mui/material";
import { languageGrades, user } from "../api/routes";
import { getUserId } from "../utils/user";
import useAxios from "axios-hooks";
import { LanguageAllGrades, LanguageGradeEmitter, LanguagesAllGrades, LanguagesGradesEmitter } from "../api/language";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import LanguageReviewRow from "./LanguageReviewRow";
import { useParams } from "react-router-dom";
import { isSubject, localStorage } from "../utils/localstorage";
import AddLanguageDialog from "./AddLanguageDialog";

export default () => {
  const userId: string | undefined = useParams() ? useParams().userId : "";
  const [open, setOpen] = useState<boolean>(false);
  const [{ data, loading, error }] = useAxios<LanguagesAllGrades>({
    url: languageGrades.getUserGrades(userId),
    method: "GET"
  });
  const [{ data: userGradesData, loading: userGradesLoading, error: userGradesError }] =
    useAxios<LanguagesGradesEmitter>({
      url: languageGrades.getListofEmittedGrades(userId, getUserId(localStorage.token.get(""))),
      method: "GET",
      headers: { Authorization: `Bearer ${localStorage.token.get("")}` }
    });
  if (error) {
    return <p>{error.response?.data.message}</p>;
  }

  if (userGradesError) {
    return <p>{userGradesError.response?.data.message}</p>;
  }
  if (loading) {
    return <p>loading...</p>;
  }
  if (userGradesLoading) {
    return <p>loading...</p>;
  }
  if (data && userGradesData) {
    return (
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell></TableCell>
              <TableCell>Self</TableCell>
              <TableCell>Evaluator</TableCell>
              <TableCell>User</TableCell>
              <TableCell>
                {isSubject(userId) ? (
                  <IconButton onClick={() => setOpen(true)} color="primary">
                    <AddCircleIcon />
                  </IconButton>
                ) : (
                  <></>
                )}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.languages.map((languageGrade: LanguageAllGrades, i) => {
              const userReview: LanguageGradeEmitter | undefined = userGradesData.languages.find(
                (userLanguageGrade: LanguageGradeEmitter) =>
                  userLanguageGrade.language === languageGrade.language &&
                  userLanguageGrade.translatedLanguage === languageGrade.translatedLanguage
              );
              return (
                <LanguageReviewRow
                  languageGrade={languageGrade}
                  key={i}
                  userLanguageGrade={userReview === undefined ? undefined : userReview}
                />
              );
            })}
          </TableBody>
        </Table>
        <AddLanguageDialog open={open} onClose={() => setOpen(false)} />
      </Card>
    );
  }
  return <></>;
};
