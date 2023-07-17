export interface ContractRequest {
  id: number;
  userId: string;
  requestId: number;
  createdAt: string;
  status: string;
}

export interface ContractRequests {
  contractRequests: ContractRequest[];
}
