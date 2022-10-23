package az.et.unitech.account.controller;

import az.et.unitech.account.model.AccountToAccountRequest;
import az.et.unitech.account.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/a2a")
    public ResponseEntity<Void> transferToAccount(@RequestBody @Valid AccountToAccountRequest accountToAccountRequest) {
        transferService.transferToAccount(accountToAccountRequest);
        return ResponseEntity.accepted().build();
    }

}
