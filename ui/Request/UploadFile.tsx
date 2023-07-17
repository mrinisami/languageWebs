import { Chip, Grid } from "@mui/material";
import React, { useState } from "react";
import FileUploadIcon from "@mui/icons-material/FileUpload";
import useAxios from "axios-hooks";
import { request } from "../api/routes";
import { UploadUri } from "../api/review";
import CheckIcon from "@mui/icons-material/Check";

interface Params {
  fileName: string;
}

interface Props {
  setFileInfo: (filePath: string) => void;
  storageEndPoint: string;
}

export default (props: Props) => {
  const [file, setFile] = useState<Blob | string>("");
  const [reqParam, setReqParam] = useState<Params>({ fileName: "" });
  const [, executePut] = useAxios({ method: "PUT", baseURL: "", headers: { Authorization: "" } }, { manual: true });
  const [, executeGet] = useAxios<UploadUri>(
    {
      url: props.storageEndPoint,
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
      props.setFileInfo(data.fileName);
    }
  };
  return (
    <Grid item container direction="column" alignItems="center">
      <Grid item>
        {file === "" ? (
          <Grid item>
            <Chip
              label="Upload file"
              icon={<FileUploadIcon />}
              onClick={() => document.getElementById("upload")?.click()}
            />
          </Grid>
        ) : (
          <Grid item>
            <Chip label={file.name} icon={<FileUploadIcon />} />
          </Grid>
        )}
      </Grid>
      <Grid item>
        <input type="file" onChange={handleFileUpload} id="upload" hidden />
      </Grid>
      <Grid item>
        <Chip label="Confirm" icon={<CheckIcon color="success" />} onClick={onClickUpload} />
      </Grid>
    </Grid>
  );
};
