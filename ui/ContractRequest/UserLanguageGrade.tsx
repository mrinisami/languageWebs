import useAxios from "axios-hooks";
import React from "react";
import { languageGrades } from "../api/routes";
import { LanguageAllGrades } from "../api/language";
import { Params } from "./ContractRequest";
import LanguageReviewRowPublic from "../Profile/LanguageReviewRowPublic";
import { Box, Chip, CircularProgress, Grid, Typography } from "@mui/material";
import { avgGrade, avgGrade } from "../utils/functions";
import { circleProgressColor } from "../utils/colors";

export default (props: Params) => {
  const [{ data }] = useAxios<LanguageAllGrades>({
    url: languageGrades.getUserGradesByLanguage(props.userId),
    params: { refLanguage: props.refLanguage.toUpperCase(), translatedLanguage: props.translatedLanguage.toUpperCase() }
  });

  if (data) {
    const grade = avgGrade(data);
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
      <Grid item>
        <Box sx={{ position: "relative", display: "inline-flex" }}>
          <CircularProgress
            variant="determinate"
            value={grade}
            thickness={7}
            sx={{ color: circleProgressColor(grade) }}
          />
          <Box sx={boxSx}>
            <Typography variant="subtitle2" component="div" color={circleProgressColor(grade)} fontWeight="bold">
              {grade}
            </Typography>
          </Box>
        </Box>
      </Grid>
    );
  }
  return <></>;
};
