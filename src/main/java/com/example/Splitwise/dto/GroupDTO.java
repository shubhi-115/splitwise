package com.example.Splitwise.dto;

import com.example.Splitwise.model.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.Splitwise.model.User;
import java.util.List;

@Getter
@Setter
public class GroupDTO {
    private String name;
    private String description;
    private double totalAmountSpent;
    @Enumerated(EnumType.STRING)
    private Currency defaultCurrency;
    List<Integer> userId;
}
