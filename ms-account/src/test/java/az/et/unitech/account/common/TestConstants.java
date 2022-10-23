package az.et.unitech.account.common;

import az.et.unitech.account.dao.entity.AccountEntity;
import az.et.unitech.account.dao.entity.TransferHistoryEntity;
import az.et.unitech.account.model.AccountToAccountRequest;
import az.et.unitech.account.model.dto.AccountDto;
import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.account.model.enums.Currency;
import az.et.unitech.common.model.enums.UserType;
import az.et.unitech.common.security.model.CustomUserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.List;

public final class TestConstants {

    //CHECKSTYLE:OFF
    public static final String USERNAME = "ABC1234";
    public static final String SENDER_IBAN = "AZ21NABZ00000000137010001944";
    public static final String RECEIVER_IBAN = "AZ21NABZ00000000137010001945";

    public static final AccountDto ACCOUNT_DTO = AccountDto.builder()
            .id(1L)
            .username(USERNAME)
            .customerId("0000001")
            .balance(BigDecimal.TEN)
            .status(AccountStatus.ACTIVATED)
            .currency(Currency.AZN)
            .iban(SENDER_IBAN)
            .build();

    public static final AccountDto SENDER_ACCOUNT_DTO = AccountDto.builder()
            .id(1L)
            .username(USERNAME)
            .customerId("0000001")
            .balance(BigDecimal.TEN)
            .status(AccountStatus.ACTIVATED)
            .currency(Currency.AZN)
            .iban(SENDER_IBAN)
            .build();
    public static final List<AccountDto> ACCOUNT_DTO_LIST = List.of(ACCOUNT_DTO);

    public static final AccountDto RECEIVER_ACCOUNT_DTO = AccountDto.builder()
            .id(2L)
            .username("ALI1234")
            .customerId("0000002")
            .balance(BigDecimal.TEN)
            .status(AccountStatus.ACTIVATED)
            .currency(Currency.AZN)
            .iban(RECEIVER_IBAN)
            .build();

    public static final AccountDto DEACTIVATED_RECEIVER_ACCOUNT_DTO = AccountDto.builder()
            .id(2L)
            .username("ALI1234")
            .customerId("0000002")
            .balance(BigDecimal.TEN)
            .status(AccountStatus.DEACTIVATED)
            .currency(Currency.AZN)
            .iban(RECEIVER_IBAN)
            .build();

    public static final AccountEntity ACCOUNT_ENTITY = AccountEntity.builder()
            .id(1L)
            .username(USERNAME)
            .customerId("0000001")
            .balance(BigDecimal.TEN)
            .status(AccountStatus.ACTIVATED)
            .currency(Currency.AZN)
            .iban(SENDER_IBAN)
            .build();
    public static final List<AccountEntity> ACCOUNT_ENTITY_LIST = List.of(ACCOUNT_ENTITY);

    public static final CustomUserPrincipal PRINCIPAL = new CustomUserPrincipal(USERNAME, "123", "Elvin Taghizade", true, UserType.USER, List.of(new SimpleGrantedAuthority("USER")));

    public static final AccountToAccountRequest ACCOUNT_TO_ACCOUNT_REQUEST = AccountToAccountRequest.builder()
            .amount(BigDecimal.ONE)
            .senderAccountIban(SENDER_IBAN)
            .receiverAccountIban(RECEIVER_IBAN)
            .build();

    public static final AccountToAccountRequest ACCOUNT_TO_ACCOUNT_REQUEST_WITH_INVALID_AMOUNT = AccountToAccountRequest.builder()
            .amount(BigDecimal.ZERO)
            .senderAccountIban(SENDER_IBAN)
            .receiverAccountIban(RECEIVER_IBAN)
            .build();

    public static final AccountToAccountRequest ACCOUNT_TO_ACCOUNT_REQUEST_WITH_HIGH_AMOUNT = AccountToAccountRequest.builder()
            .amount(BigDecimal.valueOf(1000))
            .senderAccountIban(SENDER_IBAN)
            .receiverAccountIban(RECEIVER_IBAN)
            .build();

    public static final TransferHistoryEntity TRANSFER_HISTORY_ENTITY = TransferHistoryEntity.builder()
            .amount(BigDecimal.ONE)
            .senderIban(SENDER_IBAN)
            .receiverIban(RECEIVER_IBAN)
            .build();

    //CHECKSTYLE:ON

}