import useAxios from "axios-hooks";
import React from "react";
import { request } from "../api/routes";
import { Request } from "../api/review";

interface Props {
  requestId: number;
  setRequestInfo: (requestInfo: Request) => void;
}
export default (props: Props) => {
  const [{ data }] = useAxios<Request>({ url: request.getRequest(props.requestId) });

  if (data) {
    props.setRequestInfo(data);
  }

  return <></>;
};
