import { Button, Checkbox, FormControlLabel, FormGroup, Grid, Paper, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import LanguageOption from "./LanguageOption";
import PriceOption from "./PriceOption";
import { createSearchParams, useLocation, useNavigate, useParams, useSearchParams } from "react-router-dom";
import { routes } from "../routes";
import { RequestLanguagesStats } from "../api/review";
import StatusOption from "./StatusOption";
import DueDateOption, { DateInfo } from "./DueDateOption";
import { addDaysToTimestamp } from "../utils/functions";

export interface Language {
  index: number;
  sourceLanguage: string;
  translatedLanguage: string;
}
export interface Prices {
  index: number;
  min: number | undefined;
  max: number | undefined;
}
interface SearchParams {
  max: number;
  min: number;
  sourceLanguages: string[];
  translatedLanguages: string[];
  status: string[];
  dueDate: number;
}
export interface Status {
  index: number;
  status: string;
}
interface Props {
  languageStats: RequestLanguagesStats;
}
export default (props: Props) => {
  const route = useLocation().pathname;
  const [searchParams] = useSearchParams();
  const sourceLanguages = searchParams.getAll("sourceLanguages");
  const translatedLanguages = searchParams.getAll("translatedLanguages");
  const [status, setStatus] = useState<Status[]>([]);
  const [dueDate, setDueDate] = useState<number | null>(null);
  const [chosenLanguages, setChosenLanguages] = useState<Language[]>(
    props.languageStats.requestLanguageStats
      .filter(
        (lang, i) => lang.sourceLanguage === sourceLanguages[i] && lang.translatedLanguage === translatedLanguages[i]
      )
      .map((lang, i) => {
        return { sourceLanguage: lang.sourceLanguage, translatedLanguage: lang.translatedLanguage, index: i };
      })
  );
  const navigate = useNavigate();
  const [nbLangShown, setNbLangShown] = useState<number>(3);
  const [prices, setPrices] = useState<Prices[]>([]);
  const updateChosenLanguages = (language: Language) => {
    const langExists: boolean = chosenLanguages.filter((lang) => language.index === lang.index).length > 0;
    if (langExists) {
      const newValues = [...chosenLanguages];
      newValues.splice(
        newValues.findIndex((lang) => lang.index === language.index),
        1
      );
      setChosenLanguages(newValues);
    } else {
      setChosenLanguages([...chosenLanguages, language]);
    }
  };
  const updatePrices = (argPrices: Prices) => {
    setPrices([...prices, argPrices]);
  };
  const deletePrices = (index: number) => {
    setPrices(prices.filter((price) => price.index !== index));
  };
  const updateNbLang = (nbLang: number) => setNbLangShown(nbLang);
  const updateStatus = (statusParam: Status) => setStatus([...status, statusParam]);
  const deleteStatus = (statusParam: Status) =>
    setStatus(status.filter((status) => status.index !== statusParam.index));
  const addDueDate = (date: DateInfo) => setDueDate(addDaysToTimestamp(date.days).getTime());
  const removeDueDate = () => setDueDate(null);
  const onClickApplyFilter = () => {
    let allPrices: number[] = [];
    prices.forEach((info) => {
      if (info.max !== undefined) {
        allPrices.push(info.max);
      }
      if (info.min !== undefined) {
        allPrices.push(info.min);
      }
    });
    allPrices = allPrices.sort((a, b) => a - b);
    const params: SearchParams = {};
    if (allPrices.length > 1) {
      params.max = allPrices[allPrices.length - 1];
      params.min = allPrices[0];
    }
    if (allPrices.length === 1) {
      params.min = allPrices[0];
    }
    if (chosenLanguages.length > 0) {
      params.sourceLanguages = chosenLanguages.map((info) => info.sourceLanguage);
      params.translatedLanguages = chosenLanguages.map((info) => info.translatedLanguage);
    }
    if (status.length > 0) {
      params.status = status.map((s) => s.status);
    }
    if (dueDate) {
      params.dueDate = dueDate;
    }
    navigate({
      pathname: route,
      search: createSearchParams(params).toString()
    });
  };
  return (
    <Paper>
      <Grid container direction="column">
        <Grid item>
          <LanguageOption
            nbLangShown={nbLangShown}
            setChosenLanguages={updateChosenLanguages}
            setNbLangShown={updateNbLang}
            languageStats={props.languageStats}
          />
        </Grid>
        <Grid item>
          <PriceOption updatePrices={updatePrices} deletePrices={deletePrices} />
        </Grid>
        <Grid item>
          <StatusOption updateStatus={updateStatus} deleteStatus={deleteStatus} />
        </Grid>
        <Grid item>
          <DueDateOption addDueDate={addDueDate} removeDueDate={removeDueDate} />
        </Grid>
        <Grid item container justifyContent="flex-end">
          <Grid item>
            <Button size="small" onClick={onClickApplyFilter}>
              Apply
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Paper>
  );
};
