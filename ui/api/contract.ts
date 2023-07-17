import { Request } from "./review";

export interface Contract {
  id: number;
  contractedUserId: string;
  requestDto: Request;
  filePath: string;
  modifiedAt: number;
  dueDate: number;
  status: string;
}

export interface Contracts {
  contracts: Contract[];
}

export interface Extension {
  date: number;
  contractId: number;
  status: string;
  id: number;
}
