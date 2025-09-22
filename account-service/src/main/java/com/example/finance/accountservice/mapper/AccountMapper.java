package com.example.finance.accountservice.mapper;

import com.example.finance.accountservice.domain.Account;
import com.example.finance.accountservice.dto.AccountDto;
import com.example.finance.accountservice.dto.CreateAccountRequest;
import com.example.finance.accountservice.dto.UpdateAccountRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

  AccountDto toDto(Account account);

  Account toEntity(CreateAccountRequest req);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(UpdateAccountRequest req, @MappingTarget Account account);
}
