package com.example.Splitwise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;
    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

}
