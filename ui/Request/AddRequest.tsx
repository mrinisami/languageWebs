import { Container } from "@mui/system";
import React, { ChangeEvent, useEffect, useState } from "react";
import { languageToFlag } from "../api/language";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Autocomplete,
  Button,
  Grid,
  InputAdornment,
  Paper,
  TextField,
  Typography
} from "@mui/material";
import ArrowRightAltOutlinedIcon from "@mui/icons-material/ArrowRightAltOutlined";
import MonetizationOnIcon from "@mui/icons-material/MonetizationOn";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import UploadFile from "./UploadFile";
import useAxios from "axios-hooks";
import { request } from "../api/routes";
import { getUserId } from "../utils/user";
import { localStorage } from "../utils/localstorage";
import { Request } from "../api/review";
import { useParams } from "react-router-dom";
import RequestLoader from "./RequestLoader";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, { Dayjs } from "dayjs";

export default () => {
  const requestId = useParams().requestId ? Number(useParams().requestId) : undefined;
  const [requestInfo, setRequestInfo] = useState<Request | undefined>();
  const languages = Object.keys(languageToFlag).map((country) => country.charAt(0).toUpperCase() + country.slice(1));
  const [refLanguage, setRefLanguage] = useState<string | null>(languages[0]);
  const [translatedLanguage, setTranslatedLanguage] = useState<string | null>(languages[1]);
  const [date, setDate] = useState<Dayjs | null>(dayjs(new Date()));
  const [fileName, setFileName] = useState<string>();
  const [quote, setQuote] = useState<number>(0);
  const [description, setDescription] = useState<string>("");
  const [, executePost] = useAxios(
    {
      url: request.addRequest(getUserId(localStorage.token.get())),
      method: "POST"
    },
    { manual: true }
  );
  const [, executePut] = useAxios(
    {
      url: request.editRequest(getUserId(localStorage.token.get()), requestId),
      method: "PUT"
    },
    { manual: true }
  );
  const handlePost = (name: string, filePath: string) => {
    setFileName(name);
    const requestDto = {
      name,
      filePath,
      price: quote,
      sourceLanguage: refLanguage?.toUpperCase(),
      translatedLanguage: translatedLanguage?.toUpperCase(),
      description,
      dueDate: date?.toDate().getTime()
    };
    executePost({
      data: requestDto
    });
  };
  const handleGetRequest = (requestInfo: Request) => setRequestInfo(requestInfo);
  useEffect(() => {
    if (requestInfo) {
      setQuote(requestInfo.price);
      setRefLanguage(requestInfo.sourceLanguage);
      setTranslatedLanguage(requestInfo.translatedLanguage);
      setDescription(requestInfo.description);
      setFileName(requestInfo.name);
    }
  }, [requestInfo]);
  return (
    <Container maxWidth="md">
      <Paper>
        <Grid container justifyContent="center" rowSpacing={4}>
          <Grid item container xs={12} alignItems="center" justifyContent="space-evenly">
            <Grid item xs={3}>
              <Autocomplete
                value={refLanguage}
                onChange={(event: React.SyntheticEvent, newValue: string | null) => {
                  setRefLanguage(newValue);
                }}
                disabled={requestId !== undefined}
                options={languages}
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
            <Grid item xs={1}>
              <ArrowRightAltOutlinedIcon sx={{ fontSize: 60 }} />
            </Grid>
            <Grid item xs={3}>
              <Autocomplete
                value={translatedLanguage}
                onChange={(event: React.SyntheticEvent, newValue: string | null) => {
                  setTranslatedLanguage(newValue);
                }}
                options={languages}
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
            <Grid item xs={2}>
              <TextField
                type="number"
                label="Quote"
                value={quote}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <MonetizationOnIcon />
                    </InputAdornment>
                  )
                }}
                onChange={(event: React.ChangeEvent<HTMLInputElement>) => setQuote(Number(event.target.value))}
              />
            </Grid>
            <Grid item xs={2.5}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker value={date} onChange={(newValue: Dayjs | null) => setDate(newValue)} disablePast />
              </LocalizationProvider>
            </Grid>
          </Grid>
          <Grid item container xs={12} justifyContent="space-evenly">
            <Grid item xs={8}>
              <Accordion>
                <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography>Description</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <textarea
                    cols="60"
                    onChange={(event: ChangeEvent<HTMLTextAreaElement>) => setDescription(event.target.value)}
                  />
                  <Button>Submit</Button>
                </AccordionDetails>
              </Accordion>
            </Grid>
            <Grid item xs={2}>
              <UploadFile setFileInfo={handlePost} name={fileName} />
            </Grid>
          </Grid>
        </Grid>
      </Paper>
    </Container>
  );
};
