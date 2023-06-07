import { Button, Grid } from "@mui/material";
import useAxios from "axios-hooks";
import React, { useState } from "react";

export default () => {
  const [{ data, loading, error }, executePost] = useAxios(
    {
      url: "/public/test",
      method: "POST",
      headers: { "Content-type": "multipart/form-data" }
    },
    { manual: true }
  );
  const [selectedFile, setSelectedFile] = useState<string | Blob>("");
  const onChangeUpload = (event: any) => {
    setSelectedFile(event.target.files[0]);
  };

  const onClickUpload = () => {
    const formData = new FormData();
    formData.append("File", selectedFile);
    //executePost({ data: { file: selectedFile } });
  };
  return (
    <Grid item>
      <form className="form" method="POST" encType="multipart/form-data">
        <input type="file" id="test" onChange={onChangeUpload}></input>
        <Button onClick={onClickUpload}>Upload</Button>
      </form>
    </Grid>
  );
};
