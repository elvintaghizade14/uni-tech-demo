package az.et.unitech.account.mapper;

import az.et.unitech.account.dao.entity.AccountEntity;
import az.et.unitech.account.model.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDto, AccountEntity> {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

}