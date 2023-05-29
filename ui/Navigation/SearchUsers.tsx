import React, { useEffect } from "react";
import { useState } from "react";
import { Autocomplete, Box, Grid, TextField, Typography } from "@mui/material";
import useAxios from "axios-hooks";
import { UserSearch, routes } from "../api/user";
import { routes as uiRoutes } from "../routes";
import { useNavigate } from "react-router-dom";

export default () => {
  const [users, setUsers] = useState<readonly UserSearch[]>([]);
  const navigate = useNavigate();
  const [inputValue, setInputValue] = useState<string>("");
  const [userSelected, setUserSelected] = useState<UserSearch | null>(null);
  const [{ data, loading, error }, executeGet] = useAxios({
    url: routes.searchUsers,
    method: "GET"
  });

  useEffect(() => {
    console.log(users);
    if (inputValue !== "") {
      executeGet({ params: { nameLike: inputValue } });
      if (data) {
        setUsers(data.users);
      }
    }
  }, [inputValue]);

  useEffect(() => {
    if (userSelected !== null) {
      navigate(uiRoutes.profile(userSelected?.id, "recentActivity"));
      setUserSelected(null);
      setUsers([]);
    }
  }, [userSelected]);
  return (
    <Grid item>
      <Autocomplete
        filterOptions={(x) => x}
        options={users}
        getOptionLabel={(user) => (typeof user === "string" ? user : `${user.firstName} ${user.lastName}`)}
        value={userSelected}
        noOptionsText="No users found"
        onInputChange={(event: React.SyntheticEvent, newValue: string) => setInputValue(newValue)}
        onChange={(event: React.SyntheticEvent, newValue: UserSearch | null) => {
          setUserSelected(newValue);
        }}
        renderInput={(params) => <TextField {...params} />}
        renderOption={(props, user) => {
          return (
            <li {...props}>
              <Typography>{`${user.firstName} ${user.lastName}`}</Typography>
            </li>
          );
        }}
      />
    </Grid>
  );
};
