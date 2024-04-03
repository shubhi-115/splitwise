package com.example.Splitwise.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ResponseGroupDTO {
    private int groupId;
    private String name;
    private String description;
    private Double totalAmountSpent;

    List<ResponseUserDTO> users;
}
