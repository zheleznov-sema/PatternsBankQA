package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationDto {
    private String login;
    private String password;
    private String status;
}
