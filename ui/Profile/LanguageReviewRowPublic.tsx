import { TableRow, TableCell, CircularProgress, Typography, Box } from "@mui/material";
import React from "react";
import { LanguageAllGrades, languageToFlag } from "../api/language";
import SyncAltIcon from "@mui/icons-material/SyncAlt";
import { circleProgressColor } from "../utils/colors";

interface Props {
  languageGrade: LanguageAllGrades;
}

export default (props: Props) => {
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
    </TableRow>
  );
};
