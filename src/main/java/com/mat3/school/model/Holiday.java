package com.mat3.school.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "holidays")
@NoArgsConstructor
public class Holiday extends BaseEntity {

    @Id
    private String day;

    private String reason;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }

    public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }
}