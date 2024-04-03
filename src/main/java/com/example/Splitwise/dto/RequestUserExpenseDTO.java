package com.example.Splitwise.dto;

import com.example.Splitwise.model.UserExpenseType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserExpenseDTO {
    private int userId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private UserExpenseType userExpenseType;
}
