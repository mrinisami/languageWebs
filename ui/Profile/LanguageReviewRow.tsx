import { TableRow, TableCell, Grid, IconButton, CircularProgress, Typography, Box } from "@mui/material";
import React, { useEffect, useState } from "react";
import { LanguageRequest, LanguageAllGrades, LanguageGradeEmitter, languageToFlag } from "../api/language";
import SyncAltIcon from "@mui/icons-material/SyncAlt";
import ModeIcon from "@mui/icons-material/Mode";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import LanguageGradeDialog from "./LanguageGradeDialog";
import useAxios from "axios-hooks";
import { languageGrades } from "../api/routes";
import { useParams } from "react-router-dom";
import { circleProgressColor } from "../utils/colors";
import { useTokenContext } from "../context/TokenContext";

interface Props {
  languageGrade: LanguageAllGrades;
  userLanguageGrade: LanguageGradeEmitter | null;
}

export default (props: Props) => {
  const userId: string | undefined = useParams() ? useParams().userId : "";
  const [{ data: editData, loading: editLoading, error: editError }, executeEdit] = useAxios(
    {
      url: languageGrades.editLanguageGrades(userId, props.userLanguageGrade?.id),
      method: "put",
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
    },
    { manual: true }
  );
  const [{ data: addData, loading: addLoading, error: addError }, executeAdd] = useAxios(
    {
      url: languageGrades.addLanguageGrade(userId),
      method: "POST",
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
    },
    { manual: true }
  );
  const [openEditDialog, setOpenEditDialog] = useState<boolean>(false);
  const [openCreateDialog, setOpenCreateDialog] = useState<boolean>(false);
  const boxSx = {
    top: 0,
    left: 0,
    bottom: 0,
    right: 0,
    position: "absolute",
    display: "flex",
    alignItems: "center",
    justifyContent: "center"
  };
  if (editError) {
    <p>{editError.response?.data.message}</p>;
  }
  if (editLoading) {
    <p>Loading...</p>;
  }
  const { isLoggedIn } = useTokenContext();
  const editDataCall = (data: LanguageRequest) => {
    executeEdit({
      data: data
    });
  };
  const postData = (data: LanguageRequest) => {
    executeAdd({
      data: data
    });
  };
  return (
    <TableRow>
      <TableCell>
        <span>
          <img src={languageToFlag[props.languageGrade.language.toLowerCase()]} width="30" />
          <SyncAltIcon />
          <img src={languageToFlag[props.languageGrade.translatedLanguage.toLowerCase()]} width="30" />
        </span>
      </TableCell>
      <TableCell>
        <Box sx={{ position: "relative", display: "inline-flex" }}>
          <CircularProgress
            variant="determinate"
            value={props.languageGrade.selfAssessment}
            thickness={7}
            sx={{ color: circleProgressColor(props.languageGrade.selfAssessment) }}
          />
          <Box sx={boxSx}>
            <Typography
              variant="subtitle2"
              component="div"
              color={circleProgressColor(props.languageGrade.selfAssessment)}
              fontWeight="bold"
            >
              {props.languageGrade.selfAssessment}
            </Typography>
          </Box>
        </Box>
      </TableCell>
      <TableCell>
        <Box sx={{ position: "relative", display: "inline-flex" }}>
          <CircularProgress
            variant="determinate"
            value={props.languageGrade.evaluatorGrade ? props.languageGrade.evaluatorGrade : 100}
            thickness={7}
            sx={{
              color: circleProgressColor(props.languageGrade.evaluatorGrade ? props.languageGrade.evaluatorGrade : 0)
            }}
          />
          <Box sx={boxSx}>
            <Typography
              variant="subtitle2"
              component="div"
              fontWeight="bold"
              color={circleProgressColor(props.languageGrade.evaluatorGrade ? props.languageGrade.evaluatorGrade : 0)}
            >
              {props.languageGrade.evaluatorGrade ? props.languageGrade.evaluatorGrade : ""}
            </Typography>
          </Box>
        </Box>
      </TableCell>
      <TableCell>
        <Box sx={{ position: "relative", display: "inline-flex" }}>
          <CircularProgress
            variant="determinate"
            value={props.languageGrade.userGrade ? props.languageGrade.userGrade : 100}
            thickness={7}
            sx={{
              color: circleProgressColor(props.languageGrade.userGrade ? props.languageGrade.userGrade : 0)
            }}
          />
          <Box sx={boxSx}>
            <Typography
              variant="subtitle2"
              component="div"
              fontWeight="bold"
              color={circleProgressColor(props.languageGrade.userGrade ? props.languageGrade.userGrade : 0)}
            >
              {props.languageGrade.userGrade ? props.languageGrade.userGrade : ""}
            </Typography>
          </Box>
        </Box>
      </TableCell>
      <TableCell
        onClick={props.userLanguageGrade !== null ? () => setOpenEditDialog(true) : () => setOpenCreateDialog(true)}
        sx={{ cursor: "pointer" }}
      >
        <Box sx={{ position: "relative", display: "inline-flex" }}>
          <CircularProgress
            variant="determinate"
            value={props.userLanguageGrade !== null ? props.userLanguageGrade.grade : 100}
            thickness={7}
            sx={{
              color: circleProgressColor(props.userLanguageGrade !== null ? props.userLanguageGrade.grade : 0)
            }}
          />
          <Box sx={boxSx}>
            <Typography
              variant="subtitle2"
              component="div"
              fontWeight="bold"
              color={circleProgressColor(props.userLanguageGrade !== null ? props.userLanguageGrade.grade : 0)}
            >
              {props.userLanguageGrade !== null ? props.userLanguageGrade.grade : ""}
            </Typography>
          </Box>
        </Box>
      </TableCell>
      <LanguageGradeDialog
        onClose={() => setOpenCreateDialog(false)}
        open={openCreateDialog}
        title={"Add an assessment"}
        grade={props.languageGrade.userGrade ? props.languageGrade.userGrade : 0}
        language={props.languageGrade.language}
        translatedLanguage={props.languageGrade.translatedLanguage}
        apiCall={postData}
      />
      <LanguageGradeDialog
        open={openEditDialog}
        onClose={() => setOpenEditDialog(false)}
        title={"Edit an assessment"}
        grade={props.userLanguageGrade !== null ? props.userLanguageGrade.grade : 0}
        language={props.languageGrade.language}
        translatedLanguage={props.languageGrade.translatedLanguage}
        apiCall={editDataCall}
      />
    </TableRow>
  );
};
