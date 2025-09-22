package com.example.finance.budgetservice.mapper;

import com.example.finance.budgetservice.domain.Budget;
import com.example.finance.budgetservice.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Budget toEntity(CreateBudgetRequest req);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void update(@MappingTarget Budget budget, UpdateBudgetRequest req);

  BudgetResponse toResponse(Budget budget);
}
