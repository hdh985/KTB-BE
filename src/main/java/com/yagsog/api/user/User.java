package com.yagsog.api.user;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String age; // 함수 통합 처리를 위하여 String 처리
    private String gender;

    @Column(columnDefinition = "TEXT")
    private String medications;

}
