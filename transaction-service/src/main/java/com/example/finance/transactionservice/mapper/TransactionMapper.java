package com.example.finance.transactionservice.mapper;

import com.example.finance.transactionservice.domain.Transaction;
import com.example.finance.transactionservice.dto.CreateTransactionRequest;
import com.example.finance.transactionservice.dto.TransactionResponse;
import com.example.finance.transactionservice.dto.UpdateTransactionRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

  // Create -> Entity: let occurredAt come from the request
    Transaction toEntity(CreateTransactionRequest request);

    // Patch -> Entity: apply only non-null fields (keep system fields untouched)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Transaction entity, UpdateTransactionRequest request);

    // Entity -> DTO
    TransactionResponse toResponse(Transaction transaction);
}
