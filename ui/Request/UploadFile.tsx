import { Button, Grid, Typography } from "@mui/material";
import React, { useState } from "react";
import FileUploadIcon from "@mui/icons-material/FileUpload";
import useAxios from "axios-hooks";
import { request } from "../api/routes";
import { UploadUri } from "../api/review";

interface Params {
  fileName: string;
}

interface Props {
  setFileInfo: (name: string, filePath: string) => void;
  name: string | undefined;}

export default (props: Props) => {
  const [file, setFile] = useState<Blob | string>("");
  const [reqParam, setReqParam] = useState<Params>({ fileName: "" });
  const [, executePut] = useAxios({ method: "PUT", baseURL: "", headers: { Authorization: "" } }, { manual: true });
  const [, executeGet] = useAxios<UploadUri>(
    {
      url: request.getUploadUri,
      params: reqParam
    },
    { manual: true }
  );
  const handleFileUpload = (event: any) => {
    setFile(event.target.files[0]);
    setReqParam({ fileName: event.target.files[0].name });
  };
  const onClickUpload = async () => {
    if (reqParam.fileName !== "") {
      const { data } = await executeGet();
      const { data: minio } = await executePut({
        url: data.url,
        data: file
      });
      props.setFileInfo(file.name, data.fileName);
    }
  };
  return (
    <Grid item container rowSpacing={1}>
      <Grid item xs={12}>
        <input type="file" onChange={handleFileUpload} />
      </Grid>
      <Grid item xs={12}>
        <Button endIcon={<FileUploadIcon />} onClick={onClickUpload}>
          Submit
        </Button>
      </Grid>
    </Grid>
  );
};
