package app.sami.languageWeb.contract;

import app.sami.languageWeb.contract.dtos.ContractDto;
import app.sami.languageWeb.contract.dtos.ContractFilterDto;
import app.sami.languageWeb.contract.dtos.ContractsDto;
import app.sami.languageWeb.contract.dtos.StorageUriDto;
import app.sami.languageWeb.contract.models.Contract;
import app.sami.languageWeb.contract.models.Status;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ContractController {

    private final ContractRepository contractRepository;
    private final ContractService contractService;

    @PostMapping("/requests/{requestId}/contract")
    public ContractDto createContract(@PathVariable Long requestId,
                                      @RequestBody String path,
                                      @RequestJwtSubject UUID subject){
        return ContractMapper.toContractDto(contractService.createContract(subject, requestId, path));
    }

    @GetMapping("/contracts/storage/upload-uri")
    public StorageUriDto getUploadUri(@RequestJwtSubject UUID subject,
                                      @RequestBody String fileName){
        return contractService.getUploadUri(subject, fileName);
    }

    @PutMapping("/contracts/{contractId}/file")
    public ContractDto addTranslatedDocument(@RequestJwtSubject UUID subject,
                                             @PathVariable Long contractId,
                                             @RequestBody String path){
        return ContractMapper.toContractDto(contractService.changeContractFile(subject, contractId, path));
    }

    @PutMapping("/contracts/{contractId}/extension")
    public ContractDto giveExtension(@RequestJwtSubject UUID subject,
                                     @PathVariable Long contractId,
                                     @RequestBody Long dueDate)
    {
        return ContractMapper.toContractDto(contractService.pushBackDueDate(contractId,
                subject,
                Instant.ofEpochMilli(dueDate)));
    }

    @GetMapping("/contracts")
    public ContractsDto getFilteredContracts(@RequestParam(required = false) List<Status> statuses,
                                             @RequestParam(required = false) UUID contractedUserId,
                                             @RequestParam(required = false) UUID requestUserId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             @RequestParam(defaultValue = "DESC") String sortOrder,
                                             @RequestParam(defaultValue = "modifiedAt") String sortCriteria){
        ContractFilterDto filter = ContractFilterDto.builder()
                .contractedUserId(contractedUserId)
                .requestUserId(requestUserId)
                .statuses(statuses)
                .build();

        return new ContractsDto(contractRepository.findAll(ContractSpecification.createFilter(filter),
                PageRequest.of(page, pageSize, Sort.by(Sort.Direction.fromString(sortOrder), sortCriteria)))
                .stream().map(ContractMapper::toContractDto).toList());
    }
}
