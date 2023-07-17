import { Chip, Grid, IconButton, InputAdornment, TextField } from "@mui/material";
import React, { ChangeEvent, useEffect, useState } from "react";
import RequestQuoteIcon from "@mui/icons-material/RequestQuote";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import AutoStoriesIcon from "@mui/icons-material/AutoStories";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import { Request } from "../api/review";
import { request } from "../api/routes";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, { Dayjs } from "dayjs";
import useAxios from "axios-hooks";

interface Props {
  request: Request;

  editable: boolean;
}
export default (props: Props) => {
  const [quote, setQuote] = useState<number>(props.request.price);
  const [date, setDate] = useState<Dayjs | null>(dayjs(new Date(props.request.dueDate * 1000)));
  const [editable, setEditable] = useState<boolean>(props.editable);
  const [, executePut] = useAxios<Request>({ method: "PUT" }, { manual: true });
  const handleQuoteChange = (event: ChangeEvent<HTMLInputElement>) => {
    setQuote(Number(event.target.value));
  };
  const handleConfirmEdit = async () => {
    const { data } = await executePut({
      url: request.editRequest(props.request.userDto.id, props.request.id),
      data: { price: quote, dueDate: date?.toDate().getTime() }
    });
    setQuote(data.price);
    setEditable(false);
  };
  useEffect(() => {
    setEditable(props.editable);
  }, [props.editable]);
  return (
    <Grid item container spacing={2} alignItems="center">
      <Grid item>
        {editable ? (
          <TextField
            sx={{ width: "8rem" }}
            label="Quote"
            type="number"
            value={quote}
            onChange={(event: ChangeEvent<HTMLInputElement>) => handleQuoteChange(event)}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <RequestQuoteIcon />
                </InputAdornment>
              )
            }}
          />
        ) : (
          <Chip label={quote.toFixed(2)} icon={<RequestQuoteIcon />} variant="outlined" />
        )}
      </Grid>
      <Grid item>
        <Chip label="30 mins" icon={<AccessTimeIcon />} variant="outlined" />
      </Grid>
      <Grid item>
        <Chip label="3000 words" icon={<AutoStoriesIcon />} variant="outlined" />
      </Grid>
      <Grid item>
        {editable ? (
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker value={date} onChange={(newValue: Dayjs | null) => setDate(newValue)} disablePast />
          </LocalizationProvider>
        ) : (
          <Chip label={date?.toString()} icon={<CalendarMonthIcon />} variant="outlined" />
        )}
      </Grid>
      {editable ? (
        <Grid item>
          <IconButton onClick={handleConfirmEdit}>
            <CheckCircleIcon color="primary" />
          </IconButton>
        </Grid>
      ) : (
        <></>
      )}
    </Grid>
  );
};
