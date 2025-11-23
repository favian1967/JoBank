package com.company.jobank.Mappers;

import com.company.jobank.Dtos.TransactionDto;
import com.company.jobank.Entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setFromAccountId(
                transaction.getFromAccount() != null ?
                        transaction.getFromAccount().getId() : null
        );
        dto.setToAccountId(
                transaction.getToAccount() != null ?
                        transaction.getToAccount().getId() : null
        );
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }
}