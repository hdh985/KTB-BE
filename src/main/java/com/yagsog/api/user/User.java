package com.yagsog.api.user;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    private String name;
    private int age;
    private String gender;
    private JSONPObject medications;
}
