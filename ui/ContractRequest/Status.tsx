import React from "react";
import PendingIcon from "@mui/icons-material/Pending";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";

interface Props {
  status: string;
}
export default (props: Props) => {
  if (props.status === "ACCEPTED" || props.status === "COMPLETED") {
    return <CheckCircleIcon color="success" />;
  } else if (props.status === "PENDING") {
    return <PendingIcon color="disabled" />;
  }
  return <CancelIcon color="error" />;
};
