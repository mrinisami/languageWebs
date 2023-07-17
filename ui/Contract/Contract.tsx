import { Card, Chip, Grid, Typography } from "@mui/material";
import React, { useState } from "react";
import { Contract, Extension } from "../api/contract";
import { languageToFlag } from "../api/language";
import FileDownloadIcon from "@mui/icons-material/FileDownload";
import useAxios from "axios-hooks";
import { contract, request } from "../api/routes";
import UploadFile from "../Request/UploadFile";
import { localStorage } from "../utils/localstorage";
import { getUserId } from "../utils/user";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import RequestQuoteIcon from "@mui/icons-material/RequestQuote";
import ContractModal from "./ContractModal";
import dayjs, { Dayjs } from "dayjs";
import FileUploadIcon from "@mui/icons-material/FileUpload";
import Status from "../ContractRequest/Status";
import ExtensionRequest from "./ExtensionRequest";
import NotificationImportantIcon from "@mui/icons-material/NotificationImportant";
import DecisionExtensionRequest from "./DecisionExtensionRequest";

interface Props {
  contract: Contract;
  refetch: () => void;
}

export default (props: Props) => {
  const [openExtension, setOpenExtension] = useState<boolean>(false);
  const [open, setOpen] = useState<boolean>(false);
  const [isDecisionModal, setIsDecisionModal] = useState<boolean>(false);
  const [contractedStatus, setContractedStatus] = useState<string>(props.contract.status);
  const [{ data }] = useAxios<Extension>(
    {
      url: contract.getExtensionRequests,
      params: { contractId: props.contract.id }
    },
    { useCache: false }
  );
  const [, editContract] = useAxios(
    {
      url: contract.editContract(props.contract.id),
      method: "PUT"
    },
    { manual: true }
  );
  const [, requestLink] = useAxios(
    {
      url: request.getDownloadUri
    },
    { manual: true }
  );
  const [, extension] = useAxios(
    {
      url: contract.askExtension,
      method: "POST"
    },
    { manual: true }
  );
  const [, decisionExtension] = useAxios(
    {
      url: contract.editExtensionRequests(data?.id),
      method: "PUT"
    },
    { manual: true }
  );
  const [, downloadFile] = useAxios({}, { manual: true });
  const [filePath, setFilePath] = useState<string>(props.contract.filePath);
  const handleDownloadFile = async (filePath: string) => {
    const { data } = await requestLink({ params: { path: filePath } });
    const { data: file } = await downloadFile({ url: data.url, headers: { Authorization: "" } });
    const blob = new Blob([file]);
    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    const fileName = data.fileName.split("_");
    link.download = fileName[fileName.length - 1];
    link.click();
  };
  const handleFileUpdate = (filePath: string) => {
    setOpen(true);
    setFilePath(filePath);
  };
  const handleContractUpdate = (status: string) => {
    setOpen(false);
    setContractedStatus(status);
    editContract({ data: { filePath: filePath, status: status, dueDate: null } });
    props.refetch();
  };
  const handleExtensionRequest = (date: Dayjs | null) => {
    extension({ data: { date: date?.toDate().getTime(), contractId: props.contract.id, status: null, id: null } });
    setOpenExtension(false);
    props.refetch();
  };
  const renderDecisionExtension = async (status: string) => {
    await decisionExtension({
      data: { status: status }
    });
    setIsDecisionModal(false);
    props.refetch();
  };
  const date = new Date(props.contract.modifiedAt * 1000);
  const fileName = props.contract.requestDto.filePath.split("/");
  const translatedFile = props.contract.filePath ? props.contract.filePath.split("_") : "";
  return (
    <Card>
      <Grid container>
        <Grid item container justifyContent="space-between" alignItems="center">
          <Grid item>
            <Chip label={props.contract.status} icon={<Status status={props.contract.status} />} variant="outlined" />
          </Grid>
          <Grid item>
            <Chip label={`Last modified: ${date.toDateString()}`} variant="outlined" />
          </Grid>
        </Grid>
        <Grid container justifyContent="space-between" alignItems="center" spacing={2}>
          <Grid item>
            <Grid item container direction="column" justifyContent="space-between" alignItems="center">
              <Grid item>
                <img src={languageToFlag[props.contract.requestDto.sourceLanguage.toLowerCase()]} width="25" />
              </Grid>
              <Grid item>
                <Chip
                  label={fileName[fileName.length - 1]}
                  icon={<FileDownloadIcon />}
                  onClick={() => handleDownloadFile(props.contract.requestDto.filePath)}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid item>
            <Grid item container direction="column" alignItems="center">
              <Grid item>
                <Chip
                  label={props.contract.requestDto.price.toFixed(2)}
                  icon={<RequestQuoteIcon />}
                  variant="outlined"
                />
              </Grid>
              <Grid item>
                <Chip
                  label={new Date(props.contract.requestDto.dueDate * 1000).toDateString()}
                  icon={<CalendarMonthIcon />}
                  onClick={() =>
                    props.contract.contractedUserId === getUserId(localStorage.token.get())
                      ? setOpenExtension(true)
                      : null
                  }
                  deleteIcon={data ? <NotificationImportantIcon /> : <></>}
                  onDelete={() => setIsDecisionModal(true)}
                  variant="outlined"
                />
              </Grid>
            </Grid>
          </Grid>

          <Grid item sx={{ minWidth: "0%" }}>
            <Grid item container direction="column" alignItems="center">
              <Grid item>
                <img src={languageToFlag[props.contract.requestDto.translatedLanguage.toLowerCase()]} width="25" />
              </Grid>
              <Grid item>
                {props.contract.contractedUserId === getUserId(localStorage.token.get())
                  ? renderUploadTranslator()
                  : renderFileContractor()}
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
      <ContractModal open={open} updateContract={handleContractUpdate} />
      <ExtensionRequest
        open={openExtension}
        onClose={() => setOpenExtension(false)}
        confirmRequest={handleExtensionRequest}
      />
      {data ? (
        <DecisionExtensionRequest
          open={isDecisionModal}
          confirm={renderDecisionExtension}
          date={data.date}
          onClose={() => setIsDecisionModal(false)}
        />
      ) : (
        <></>
      )}
    </Card>
  );
  function renderUploadTranslator() {
    if (props.contract.filePath === null) {
      return <UploadFile setFileInfo={handleFileUpdate} storageEndPoint={contract.getUploadUri} />;
    }
    return (
      <Grid item container>
        <Grid item>
          <Chip label={translatedFile[translatedFile.length - 1]} icon={<FileUploadIcon />} />
        </Grid>
      </Grid>
    );
  }
  function renderFileContractor() {
    if (props.contract.filePath === null) {
      return <Typography>No updated file</Typography>;
    }
    return (
      <Chip
        label={translatedFile[translatedFile.length - 1]}
        icon={<FileDownloadIcon />}
        onClick={() => handleDownloadFile(props.contract.filePath)}
      />
    );
  }
};
