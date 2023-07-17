import { createTheme, experimental_sx as sx } from "@mui/material/styles";

export const themeDark = createTheme({
  palette: {
    mode: "dark"
  },
  components: {
    MuiButton: {
      defaultProps: { variant: "outlined" }
    },
    MuiTableCell: { defaultProps: { align: "center" } },
    MuiPaper: {
      defaultProps: { elevation: 20 },
      styleOverrides: { root: { background: "rgba(255, 255, 255, 0.16)", padding: 12 } }
    },
    MuiChip: {
      defaultProps: { variant: "outlined" }
    }
  }
});
