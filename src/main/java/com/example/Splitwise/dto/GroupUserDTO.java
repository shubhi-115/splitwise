package com.example.Splitwise.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GroupUserDTO {
    private int groupId;
    List<Integer> userId;
}
