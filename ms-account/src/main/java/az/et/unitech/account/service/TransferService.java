package az.et.unitech.account.service;

import az.et.unitech.account.dao.entity.TransferHistoryEntity;
import az.et.unitech.account.dao.repository.TransferHistoryRepository;
import az.et.unitech.account.model.AccountToAccountRequest;
import az.et.unitech.account.model.dto.AccountDto;
import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.common.error.exception.CommonBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferService {

    private final AccountService accountService;
    private final TransferHistoryRepository transferHistoryRepository;

    @Transactional
    public void transferToAccount(AccountToAccountRequest request) {
        final String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDto senderAccount = accountService.findByUsernameAndIbanAndStatus(senderUsername, request.getSenderAccountIban(), AccountStatus.ACTIVATED);

        final BigDecimal amountToTransfer = request.getAmount();
        final BigDecimal senderAccountBalance = senderAccount.getBalance();
        checkAmountAndBalance(senderAccountBalance, amountToTransfer);

        AccountDto receiverAccount = accountService.findByIban(request.getReceiverAccountIban());
        validateReceiverAccount(senderAccount, receiverAccount);

        senderAccount.setBalance(senderAccountBalance.subtract(amountToTransfer));
        accountService.updateAccount(senderAccount);

        receiverAccount.setBalance(receiverAccount.getBalance().add(amountToTransfer));
        accountService.updateAccount(receiverAccount);

        transferHistoryRepository.save(TransferHistoryEntity.builder()
                .senderIban(senderAccount.getIban())
                .receiverIban(receiverAccount.getIban())
                .amount(amountToTransfer)
                .build());
        log.info("Account2account transfer completed successfully.");
        //todo for further steps send push notification or sms or email about outgoing and incoming transfers
    }

    private void checkAmountAndBalance(BigDecimal balance, BigDecimal amountToTransfer) {
        if (amountToTransfer.compareTo(BigDecimal.ZERO) < 1)
            throw new CommonBadRequestException("Invalid amount!");
        if (balance.compareTo(amountToTransfer) < 0)
            throw new CommonBadRequestException("Insufficient balance!");
    }

    private void validateReceiverAccount(AccountDto senderAccount, AccountDto receiverAccount) {
        if (receiverAccount.getStatus() == AccountStatus.DEACTIVATED)
            throw new CommonBadRequestException("Transfer into deactivated account not permitted!");
        if (receiverAccount.equals(senderAccount))
            throw new CommonBadRequestException("Transfer into the same account not permitted!");
    }


}

