import { Button, Dialog, DialogTitle, Grid, Paper, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import { LanguageRequest, languageToFlag } from "../api/language";
import SyncAltIcon from "@mui/icons-material/SyncAlt";

interface Props {
  open: boolean;
  title: string;
  grade: number;
  language: string;
  translatedLanguage: string;
  onClose: () => void;
  apiCall: (data: LanguageRequest) => void;
}
export default (props: Props) => {
  const [grade, setGrade] = useState<number>(props.grade);
  const onClickSubmit = () => {
    props.onClose();
    props.apiCall({ grade, refLanguage: props.language, translatedLanguage: props.translatedLanguage });
    window.location.reload();
  };
  useEffect(() => {
    setGrade(props.grade);
  }, [props.onClose]);

  return (
    <Dialog onClose={props.onClose} open={props.open}>
      <Paper sx={{ p: 2 }}>
        <DialogTitle>{props.title}</DialogTitle>

        <Grid container justifyContent="space-evenly" alignItems="center">
          <Grid item xs={3}>
            <Grid item>
              <span>
                <img src={languageToFlag[props.language.toLowerCase()]} width="30" />
                <SyncAltIcon />
                <img src={languageToFlag[props.translatedLanguage.toLowerCase()]} width="30" />
              </span>
            </Grid>
          </Grid>
          <Grid item xs={3}>
            <TextField
              label="Grade"
              type="number"
              value={grade}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => setGrade(Number(e.target.value))}
            />
          </Grid>
          <Grid item xs={3}>
            <Button variant="outlined" onClick={onClickSubmit}>
              Submit
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Dialog>
  );
};
