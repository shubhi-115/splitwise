package com.example.Splitwise.model;

import jakarta.persistence.ManyToMany;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="SPLITWISE_USER")
public class User extends BaseModel{
    private String name;
    private String email;
    private String phoneNumber;
    @ManyToMany(mappedBy = "users")
    private List<Group> groups;
}
