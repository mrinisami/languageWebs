import { Checkbox, FormControlLabel, FormGroup, Grid, Paper, Typography } from "@mui/material";
import React, { useState } from "react";
import LanguageOption from "./LanguageOption";
import PriceOption from "./PriceOption";

export default () => {
  const [chosenLanguages, setChosenLanguages] = useState<string[]>([]);
  const [nbLangShown, setNbLangShown] = useState<number>(3);
  const updateChosenLanguages = (language: string) => {
    const languages = [...chosenLanguages];
    const languageIndex = chosenLanguages.indexOf(language);
    if (languageIndex !== -1) {
      languages.splice(languageIndex, 1);
    } else {
      languages.push(language);
    }
    setChosenLanguages(languages);
  };
  const updateNbLang = (nbLang: number) => setNbLangShown(nbLang);
  return (
    <Grid container>
      <Grid item container xs={4}>
        <Paper>
          <Grid item xs={12}>
            <Typography variant="subtitle2" fontWeight="bold">
              Owner
            </Typography>
          </Grid>
          <Grid item>
            <FormGroup>
              <FormControlLabel control={<Checkbox size="small" />} label="Me" />
              <FormControlLabel control={<Checkbox size="small" />} label="Community" />
            </FormGroup>
          </Grid>
          <Grid item xs={12}>
            <Typography variant="subtitle2" fontWeight="bold">
              Language
            </Typography>
          </Grid>
          <Grid item>
            <LanguageOption
              chosenLanguages={chosenLanguages}
              nbLangShown={nbLangShown}
              setChosenLanguages={updateChosenLanguages}
              setNbLangShown={updateNbLang}
            />
          </Grid>
          <Grid item xs={12}>
            <PriceOption />
          </Grid>
        </Paper>
      </Grid>
      <Grid item container xs={8}></Grid>
    </Grid>
  );
};
