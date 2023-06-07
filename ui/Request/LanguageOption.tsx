import { Checkbox, Chip, FormControlLabel, Grid, Icon, Typography } from "@mui/material";
import React, { useState } from "react";
import { languageToFlag } from "../api/language";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";

interface Props {
  chosenLanguages: string[];
  nbLangShown: number;
  setChosenLanguages: (language: string) => void;
  setNbLangShown: (nbLang: number) => void;
}

export default (props: Props) => {
  const languages = Object.keys(languageToFlag);
  return (
    <Grid item>
      {languages.slice(0, props.nbLangShown).map((language, i) => (
        <Grid item xs={12} key={i}>
          <FormControlLabel
            control={<Checkbox size="small" />}
            label={language}
            onChange={() => props.setChosenLanguages(language)}
          />
        </Grid>
      ))}
      <Grid
        item
        xs={12}
        container
        sx={{ cursor: "pointer" }}
        onClick={() => (props.nbLangShown === 3 ? props.setNbLangShown(languages.length) : props.setNbLangShown(3))}
      >
        <Grid item>
          {props.nbLangShown === 3 ? (
            <KeyboardArrowDownIcon sx={{ fontSize: 18 }} color="primary" />
          ) : (
            <KeyboardArrowUpIcon sx={{ fontSize: 18 }} color="primary" />
          )}
        </Grid>
        <Grid item>
          <Typography fontSize={13} color="primary">
            {props.nbLangShown === 3 ? "See more" : "See less"}
          </Typography>
        </Grid>
      </Grid>
    </Grid>
  );
};
