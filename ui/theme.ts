declare module "@mui/material/styles" {
  interface Palette {
    onboarding: Palette["primary"];
  }

  interface PaletteOptions {
    onboarding: PaletteOptions["primary"];
  }
}

import { createTheme, responsiveFontSizes } from "@mui/material/styles";
import { deepPurple } from "@mui/material/colors";

export const baseTheme = createTheme({
  palette: {
    primary: {
      main: deepPurple[400],
      light: deepPurple[100],
      dark: deepPurple[700]
    },
    secondary: {
      main: "rgba(248, 175, 38, 1)"
    },
    onboarding: {
      light: "rgba(102, 126, 234, 1)",
      main: "rgba(118, 75, 162, 1)"
    }
  },
  typography: {
    fontFamily: "Comfortaa"
  }
});

export const theme = responsiveFontSizes(baseTheme);
