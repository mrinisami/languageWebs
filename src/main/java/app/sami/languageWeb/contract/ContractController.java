package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.*;
import app.sami.languageWeb.contract.models.ContractStatus;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ContractController {

    private final ContractRepository contractRepository;
    private final ContractService contractService;

    @PostMapping("/contracts/storage/upload-uri")
    public StorageUriDto getUploadUri(@RequestJwtSubject UUID subject,
                                      @RequestBody String fileName){
        return contractService.getUploadUri(subject, fileName);
    }

    @PutMapping("/contracts/{contractId}")
    public ContractDto editContract(@RequestJwtSubject UUID subject,
                                             @PathVariable Long contractId,
                                             @RequestBody ContractDto contractDto){
        return ContractMapper.toContractDto(contractService.editContract(subject, contractId,
                contractDto));
    }

    @GetMapping("/contracts")
    public ContractsDto getFilteredContracts(@RequestParam(required = false) List<ContractStatus> contractStatuses,
                                             @RequestParam(required = false) UUID contractedUserId,
                                             @RequestParam(required = false) UUID requestUserId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             @RequestParam(defaultValue = "DESC") String sortOrder,
                                             @RequestParam(defaultValue = "modifiedAt") String sortCriteria){
        ContractFilterDto filter = ContractFilterDto.builder()
                .contractedUserId(contractedUserId)
                .requestUserId(requestUserId)
                .contractStatuses(contractStatuses)
                .build();

        return new ContractsDto(contractRepository.findAll(ContractSpecification.createFilter(filter),
                PageRequest.of(page, pageSize, Sort.by(Sort.Direction.fromString(sortOrder), sortCriteria)))
                .stream().map(ContractMapper::toContractDto).toList());
    }

}
