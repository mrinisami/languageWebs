import React from "react";
import { UserSearch } from "../utils/user";
import { useState } from "react";
import { Autocomplete, Grid, TextField } from "@mui/material";
import axios from "axios";
import { endpoints } from "../api/endpoints";

interface Props {
  data: [] | UserSearch[];
  loading: boolean;
}
export default async (props: Props) => {
  const [options, setOptions] = useState([]);
  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {};
  const getusers = async () => {
    setOptions(await axios.get(endpoints.getUsers, { params: { pageSize: 10 } }));
  };
  return (
    <Grid item>
      <Autocomplete
        filterOptions={(x) => x}
        onInputChange={getusers}
        options={options}
        renderInput={(params) => <TextField {...params} />}
        renderOption={() => {
          const firstName = "";
          const lastName = "";

          return (
            <Grid container>
              <Grid item>{firstName}</Grid>
              <Grid item>{lastName}</Grid>
            </Grid>
          );
        }}
      />
    </Grid>
  );
};
