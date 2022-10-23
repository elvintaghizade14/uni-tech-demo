package az.et.unitech.exchange.mapper;

import az.et.unitech.exchange.dao.entity.ExchangeRateEntity;
import az.et.unitech.exchange.model.dto.ExchangeRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface ExchangeRateMapper extends EntityMapper<ExchangeRateDto, ExchangeRateEntity> {

    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

}