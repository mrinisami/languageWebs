import { UserSearch } from "./user";

export interface RequestLanguageStatsDto {
  sourceLanguage: string;
  translatedLanguage: string;
  nbRequests: number;
}

export interface RequestLanguagesStats {
  requestLanguageStats: RequestLanguageStatsDto[];
}

export interface Request {
  price: number;
  sourceLanguage: string;
  translatedLanguage: string;
  name: string;
  modifiedAt: string;
  contractStatus: string;
  avgTime: number;
  filePath: string;
  downloadUri: string;
  userDto: UserSearch;
  dueDate: number;
  id: number;
  description: string;
}

export interface Requests {
  requests: Request[];
}

export interface UploadUri {
  url: string;
  fileName: string;
}
