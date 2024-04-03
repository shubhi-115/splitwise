package com.example.Splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //parameterized constructor
@NoArgsConstructor //default constructor
public class TransactionDTO {
    private String fromUserName;
    private String toUserName;
    private double amount;
}
