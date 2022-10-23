package az.et.unitech.account.service;

import az.et.unitech.account.dao.entity.AccountEntity;
import az.et.unitech.account.dao.repository.AccountRepository;
import az.et.unitech.account.mapper.AccountMapper;
import az.et.unitech.account.model.dto.AccountDto;
import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.common.error.exception.CommonBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static az.et.unitech.account.model.enums.AccountStatus.ACTIVATED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public List<AccountDto> findAllActiveAccounts() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findAllByUsernameAndStatus(username, ACTIVATED)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    public AccountDto findByUsernameAndIbanAndStatus(String username, String iban, AccountStatus status) {
        return accountRepository.findByUsernameAndIbanAndStatus(username, iban, status)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new CommonBadRequestException("Account with iban " + iban + " doesn't exists!"));
    }


    public AccountDto findByIban(String iban) {
        return accountRepository.findByIban(iban)
                .map(AccountMapper.INSTANCE::toDto)
                .orElseThrow(() -> new CommonBadRequestException("Account with iban " + iban + " doesn't exists!"));
    }

    @Transactional
    public void updateAccount(AccountDto accountDto) {
        if (accountDto.getId() == null)
            throw new CommonBadRequestException("Account id should be present to update it!");
        AccountEntity updatedAccountEntity = accountMapper.toEntity(accountDto);
        accountRepository.save(updatedAccountEntity);
    }

}

