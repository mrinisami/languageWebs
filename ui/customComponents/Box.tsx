import { Box } from "@mui/material";
import React from "react";

export default () => {
  return (
    <Box
      sx={{
        top: 0,
        left: 0,
        bottom: 0,
        right: 0,
        position: "absolute",
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
      }}
    ></Box>
  );
};
