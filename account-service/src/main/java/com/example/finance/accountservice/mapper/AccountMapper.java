package com.example.finance.accountservice.mapper;

import com.example.finance.accountservice.domain.Account;
import com.example.finance.accountservice.dto.AccountDto;
import com.example.finance.accountservice.dto.CreateAccountRequest;
import com.example.finance.accountservice.dto.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AccountMapper {

  // entity -> dto
  AccountDto toDto(Account entity);

  // create request -> entity
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Account fromCreate(CreateAccountRequest req);

  // patch update onto existing entity
  void update(@MappingTarget Account target, UpdateAccountRequest req);
}
