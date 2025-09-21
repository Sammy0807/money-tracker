package com.example.finance.accountservice.mapper;

import com.example.finance.accountservice.domain.Account;
import com.example.finance.accountservice.dto.AccountDto;
import com.example.finance.accountservice.dto.CreateAccountRequest;
import com.example.finance.accountservice.dto.UpdateAccountRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  AccountDto toDto(Account account);

  Account toEntity(CreateAccountRequest req);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(UpdateAccountRequest req, @MappingTarget Account account);
}
