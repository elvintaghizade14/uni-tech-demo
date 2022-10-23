package az.et.unitech.identity.mapper;

import az.et.unitech.identity.dao.entity.UserEntity;
import az.et.unitech.identity.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
