import { Checkbox, Chip, FormControlLabel, Grid, Icon, Typography } from "@mui/material";
import React, { useState } from "react";
import { languageToFlag } from "../api/language";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import useAxios from "axios-hooks";
import { request } from "../api/routes";
import { RequestLanguagesStats } from "../api/review";
import ArrowRightAltIcon from "@mui/icons-material/ArrowRightAlt";
import { Language } from "./Filter";
import { useSearchParams } from "react-router-dom";

interface Props {
  nbLangShown: number;
  setChosenLanguages: (language: Language) => void;
  setNbLangShown: (nbLang: number) => void;
  languageStats: RequestLanguagesStats;
}

export default (props: Props) => {
  const [searchParams] = useSearchParams();
  const languages = Object.keys(languageToFlag);
  const defaultChecks = () => {
    const sourceLanguages = searchParams.getAll("sourceLanguages");
    const translatedLanguages = searchParams.getAll("translatedLanguages");
    const params = props.languageStats.requestLanguageStats.slice(0, props.nbLangShown).map((lang, i) => {
      return lang.sourceLanguage === sourceLanguages[i] && lang.translatedLanguage === translatedLanguages[i];
    });
    return params;
  };
  const [checked, setChecked] = useState<boolean[]>(defaultChecks());

  return (
    <Grid item>
      <Grid item>
        <Typography variant="subtitle2" fontWeight="bold">
          Language
        </Typography>
      </Grid>
      {props.languageStats.requestLanguageStats.slice(0, props.nbLangShown).map((language, i) => (
        <Grid item key={i} container justifyContent="flex-start">
          <FormControlLabel
            control={
              <Checkbox
                onChange={() => {
                  props.setChosenLanguages({
                    index: i,
                    sourceLanguage: language.sourceLanguage,
                    translatedLanguage: language.translatedLanguage
                  });
                  const newChecks = [...checked];
                  newChecks[i] = !newChecks[i];
                  setChecked(newChecks);
                }}
                checked={checked[i]}
              />
            }
            label={
              <Grid item>
                <img src={languageToFlag[language.sourceLanguage.toLowerCase()]} width="20" />
                <ArrowRightAltIcon />
                <img src={languageToFlag[language.translatedLanguage.toLowerCase()]} width="20" />
              </Grid>
            }
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
