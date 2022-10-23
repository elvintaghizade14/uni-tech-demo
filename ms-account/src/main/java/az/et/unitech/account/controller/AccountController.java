package az.et.unitech.account.controller;

import az.et.unitech.account.model.dto.AccountDto;
import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> findAllActiveAccounts() {
        return ResponseEntity.ok(accountService.findAllActiveAccounts());
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountDto> findByUsernameAndIbanAndStatus(@PathVariable String username,
                                                                     @RequestParam String iban,
                                                                     @RequestParam AccountStatus status) {
        return ResponseEntity.ok(accountService.findByUsernameAndIbanAndStatus(username, iban, status));
    }

    @GetMapping("/iban/{iban}")
    public ResponseEntity<AccountDto> findByIban(@PathVariable String iban) {
        return ResponseEntity.ok(accountService.findByIban(iban));
    }

    @PostMapping
    public ResponseEntity<Void> updateAccount(AccountDto accountDto) {
        accountService.updateAccount(accountDto);
        return ResponseEntity.ok().build();
    }

}
