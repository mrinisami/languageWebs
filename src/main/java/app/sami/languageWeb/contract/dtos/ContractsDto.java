package app.sami.languageWeb.contract.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractsDto {
    private List<ContractDto> contracts;
}
