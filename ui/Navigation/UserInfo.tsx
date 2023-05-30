import { Avatar, Button, Typography, Chip, Paper, Grid } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import LogoutIcon from "@mui/icons-material/Logout";
import LoginIcon from "@mui/icons-material/Login";
import { routes } from "../routes";
import { getFormattedNameFromToken, getUserId } from "../utils/user";
import { configureAxiosHeaders } from "../utils/axios";
import { useTokenContext } from "../context/TokenContext";

export interface TokenPayload {
  sub: string;
  firstName: string;
  lastName: string;
  userId: string;
}

export default () => {
  const [isDropDown, setIsDropDown] = useState<boolean>(false);
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const navigate = useNavigate();
  const { login: logoff } = useTokenContext();
  const onClickProfile = () => {
    setIsDropDown(false);
    navigate(routes.profile(getUserId(localStorage.getItem("token")), "recentActivity"));
  };
  const onClickLogoff = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    navigate(routes.home);
    setIsDropDown(false);
    configureAxiosHeaders();
    logoff();
  };
  const onClickLogin = () => {
    navigate(routes.login);
    setIsDropDown(false);
  };

  useEffect(() => {
    setIsLoggedIn(localStorage.getItem("token") !== null);
  }, [localStorage.getItem("token") !== null]);

  return (
    <>
      <Chip
        avatar={<Avatar />}
        onClick={() => setIsDropDown(!isDropDown)}
        sx={{ cursor: "pointer" }}
        label={isLoggedIn ? getFormattedNameFromToken(localStorage.getItem("token")) : "Guest"}
      />
      {isDropDown ? (
        <Paper>
          <Grid container direction="column">
            {isLoggedIn ? (
              <Grid item>
                <Button startIcon={<AccountCircleIcon />} onClick={onClickProfile}>
                  <Typography>Profile</Typography>
                </Button>
              </Grid>
            ) : (
              <></>
            )}

            <Grid item>
              <Button
                startIcon={isLoggedIn ? <LogoutIcon /> : <LoginIcon />}
                onClick={isLoggedIn ? onClickLogoff : onClickLogin}
              >
                <Typography>{isLoggedIn ? "Log out" : "Sign in"}</Typography>
              </Button>
            </Grid>
          </Grid>
        </Paper>
      ) : (
        <></>
      )}
    </>
  );
};
