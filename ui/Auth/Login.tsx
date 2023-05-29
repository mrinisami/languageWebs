import React, { useState } from "react";
import useAxios from "axios-hooks";
import {
  Avatar,
  Button,
  FilledInput,
  FormControl,
  Grid,
  IconButton,
  InputAdornment,
  InputLabel,
  Paper,
  TextField,
  Typography
} from "@mui/material";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import EmailIcon from "@mui/icons-material/Email";
import { endpoints, user } from "../api/routes";
import { routes } from "../routes";
import { Navigate } from "react-router-dom";
import { LoadingButton } from "@mui/lab";

export default () => {
  const [showPassword, setShowPassword] = useState<boolean>(false);
  const [password, setPassword] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [{ data, loading, error }, onClickLogin] = useAxios(
    {
      baseURL: process.env.REACT_APP_API_BASE_URL,
      url: user.login,
      method: "POST",
      data: { email, userPassword: password }
    },
    { manual: true }
  );

  if (error) {
    console.log(error.message);
  }
  if (data) {
    localStorage.setItem("token", data.token);
    return <Navigate to={routes.home} />;
  }

  return (
    <Grid item container alignItems="center" justifyContent="center">
      <Paper variant="outlined" sx={{ px: 2, pb: 1 }}>
        <Grid item container direction="column" spacing={1} sx={{ minWidth: "20vh" }} alignItems="center">
          <Grid item>
            <TextField
              label="email"
              variant="standard"
              fullWidth
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => setEmail(e.target.value)}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton>
                      <EmailIcon />
                    </IconButton>
                  </InputAdornment>
                )
              }}
            />
          </Grid>
          <Grid item>
            <TextField
              label="password"
              variant="standard"
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)}
              fullWidth
              type={showPassword ? "text" : "password"}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton onClick={() => setShowPassword(!showPassword)}>
                      {showPassword ? <Visibility /> : <VisibilityOff />}
                    </IconButton>
                  </InputAdornment>
                )
              }}
            />
          </Grid>
          <Grid item>
            <LoadingButton loading={loading} variant="outlined" onClick={() => onClickLogin()}>
              Login
            </LoadingButton>
          </Grid>
          <Grid item>
            <Typography>Loading with</Typography>
          </Grid>
          <Grid item>
            <Avatar sx={{ cursor: "pointer" }} />
          </Grid>
        </Grid>
      </Paper>
    </Grid>
  );
};
