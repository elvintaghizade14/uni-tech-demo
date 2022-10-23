package az.et.unitech.account.service;

import az.et.unitech.account.dao.repository.AccountRepository;
import az.et.unitech.account.mapper.AccountMapper;
import az.et.unitech.account.model.dto.AccountDto;
import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.account.model.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;

import static az.et.unitech.account.common.TestConstants.*;
import static az.et.unitech.account.model.enums.AccountStatus.ACTIVATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(PRINCIPAL, null));
        accountService = new AccountService(AccountMapper.INSTANCE, accountRepository);
    }

    @Test
    void findAllActiveAccounts_Should_ReturnSuccess() {
        given(accountRepository.findAllByUsernameAndStatus(USERNAME, ACTIVATED)).willReturn(ACCOUNT_ENTITY_LIST);

        List<AccountDto> response = accountService.findAllActiveAccounts();
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(ACCOUNT_DTO_LIST);

        then(accountRepository).should(times(1)).findAllByUsernameAndStatus(USERNAME, ACTIVATED);
    }

}
