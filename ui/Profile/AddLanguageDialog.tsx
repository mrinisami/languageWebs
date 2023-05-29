import { Autocomplete, Box, Button, Dialog, DialogTitle, Grid, Paper, TextField, Typography } from "@mui/material";
import React, { useState } from "react";
import { LanguageRequest, languageToFlag } from "../api/language";
import useAxios from "axios-hooks";
import { languageGrades } from "../api/routes";
import { useParams } from "react-router-dom";
import ArrowRightAltOutlinedIcon from "@mui/icons-material/ArrowRightAltOutlined";

interface Props {
  open: boolean;
  onClose: () => void;
}

export default (props: Props) => {
  const countries = Object.keys(languageToFlag).map((country) => country.charAt(0).toUpperCase() + country.slice(1));
  const userId: string | undefined = useParams().userId;
  const [grade, setGrade] = useState<number>(100);
  const [refLanguage, setRefLanguage] = useState<string | null>(countries[0]);
  const [translatedLanguage, setTranslatedLanguage] = useState<string | null>(countries[1]);
  const [{ data, loading, error }, executePost] = useAxios<LanguageRequest>(
    {
      url: languageGrades.addLanguageGrade(userId),
      method: "POST"
    },
    { manual: true }
  );

  if (data) {
    props.onClose();
    window.location.reload();
  }
  const onClickSubmit = () => {
    executePost({
      data: {
        grade,
        refLanguage: refLanguage?.toUpperCase(),
        translatedLanguage: translatedLanguage?.toLocaleUpperCase()
      }
    });
  };
  return (
    <Dialog open={props.open} onClose={props.onClose}>
      <DialogTitle>
        <Typography>Add a language</Typography>
      </DialogTitle>
      <Paper sx={{ p: 2 }}>
        <Grid container justifyContent="space-evenly" alignItems="center" sx={{ width: 550 }} rowSpacing={2}>
          <Grid item container xs={12} alignItems="center" justifyContent="space-evenly">
            <Grid item xs={4}>
              <Autocomplete
                value={refLanguage}
                onChange={(event: React.SyntheticEvent, newValue: string | null) => {
                  setRefLanguage(newValue);
                }}
                options={countries}
                fullWidth
                renderInput={(params) => <TextField {...params} fullWidth label="Choose a language" />}
                renderOption={(props, option: string) => (
                  <Grid container component="li" {...props} spacing={2}>
                    <Grid item>
                      <img src={languageToFlag[option.toLowerCase()]} width="30" />
                    </Grid>

                    <Grid item>
                      <Typography variant="subtitle2">{option}</Typography>
                    </Grid>
                  </Grid>
                )}
              ></Autocomplete>
            </Grid>
            <Grid item>
              <ArrowRightAltOutlinedIcon />
            </Grid>
            <Grid item xs={4}>
              <Autocomplete
                value={translatedLanguage}
                onChange={(event: React.SyntheticEvent, newValue: string | null) => {
                  setTranslatedLanguage(newValue);
                }}
                options={countries}
                fullWidth
                renderInput={(params) => <TextField {...params} fullWidth label="Choose a language" />}
                renderOption={(props, option: string) => (
                  <Grid container component="li" {...props} spacing={2}>
                    <Grid item>
                      <img src={languageToFlag[option.toLowerCase()]} width="30" />
                    </Grid>

                    <Grid item>
                      <Typography>{option}</Typography>
                    </Grid>
                  </Grid>
                )}
              ></Autocomplete>
            </Grid>
            <Grid item xs={2}>
              <TextField
                label="Grade"
                type="number"
                value={grade}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => setGrade(Number(e.target.value))}
              />
            </Grid>
          </Grid>

          <Grid item>
            <Button variant="outlined" onClick={onClickSubmit}>
              Submit
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Dialog>
  );
};
