package az.et.unitech.account.service;

import az.et.unitech.account.dao.repository.TransferHistoryRepository;
import az.et.unitech.common.error.exception.CommonBadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static az.et.unitech.account.common.TestConstants.*;
import static az.et.unitech.account.model.enums.AccountStatus.ACTIVATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransferHistoryRepository transferHistoryRepository;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setUpSecurityContext() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(PRINCIPAL, null));
    }

    @Test
    void transferToAccount_Should_ReturnSuccess() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED)).willReturn(SENDER_ACCOUNT_DTO);
        given(accountService.findByIban(RECEIVER_IBAN)).willReturn(RECEIVER_ACCOUNT_DTO);
        willDoNothing().given(accountService).updateAccount(any());
        given(transferHistoryRepository.save(TRANSFER_HISTORY_ENTITY)).willReturn(TRANSFER_HISTORY_ENTITY);

        transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST);

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
        then(accountService).should(times(1)).findByIban(RECEIVER_IBAN);
        then(accountService).should(times(2)).updateAccount(any());
        then(transferHistoryRepository).should(times(1)).save(TRANSFER_HISTORY_ENTITY);
    }

    @Test
    void transferToAccount_Should_ThrowCommonBadRequestException_When_SenderAccountNotExists() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED))
                .willThrow(new CommonBadRequestException("Account with iban " + SENDER_IBAN + " doesn't exists!"));

        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Account with iban AZ21NABZ00000000137010001944 doesn't exists!");

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
    }

    @Test
    void transferToAccount_Should_ThrowCommonBadRequestException_When_AmountIsInvalid() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED)).willReturn(SENDER_ACCOUNT_DTO);

        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST_WITH_INVALID_AMOUNT));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Invalid amount!");

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
    }

    @Test
    void transferToAccount_Should_ThrowCommonBadRequestException_When_BalanceIsInsufficient() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED)).willReturn(SENDER_ACCOUNT_DTO);

        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST_WITH_HIGH_AMOUNT));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Insufficient balance!");

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
    }

    @Test
    void transferToAccount_Should_ThrowCommonBadRequestException_When_ReceiverAccountNotExists() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED)).willReturn(SENDER_ACCOUNT_DTO);
        given(accountService.findByIban(RECEIVER_IBAN)).willThrow(new CommonBadRequestException("Account with iban " + RECEIVER_IBAN + " doesn't exists!"));

        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Account with iban AZ21NABZ00000000137010001945 doesn't exists!");

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
        then(accountService).should(times(1)).findByIban(RECEIVER_IBAN);
    }

    @Test
    void transferToAccount_Should_ThrowCommonBadRequestException_When_ReceiverAccountDeactivated() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED)).willReturn(SENDER_ACCOUNT_DTO);
        given(accountService.findByIban(RECEIVER_IBAN)).willReturn(DEACTIVATED_RECEIVER_ACCOUNT_DTO);

        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Transfer into deactivated account not permitted!");

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
        then(accountService).should(times(1)).findByIban(RECEIVER_IBAN);
    }

    @Test
    void transferToAccount_Should_ThrowCommonBadRequestException_When_ReceiverIsSender() {
        given(accountService.findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED)).willReturn(SENDER_ACCOUNT_DTO);
        given(accountService.findByIban(RECEIVER_IBAN)).willReturn(SENDER_ACCOUNT_DTO);

        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> transferService.transferToAccount(ACCOUNT_TO_ACCOUNT_REQUEST));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Transfer into the same account not permitted!");

        then(accountService).should(times(1)).findByUsernameAndIbanAndStatus(USERNAME, SENDER_IBAN, ACTIVATED);
        then(accountService).should(times(1)).findByIban(RECEIVER_IBAN);
    }

}
