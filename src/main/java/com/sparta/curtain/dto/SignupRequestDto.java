package com.sparta.curtain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequestDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소가 아닙니다.")
    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}\\b",
            message = "유효한 이메일 주소가 아닙니다.")
    private String email;
    private boolean admin = false;
    private String adminToken = "";

    @NotBlank
    @Size(min = 4, max = 10, message = "4자 이상, 10자 이하")
    @Pattern(regexp = "^[a-z0-9]*$",message = "알파벳 소문자(a~z), 숫자(0~9)")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "8자 이상, 15자 이하")
    @Pattern(regexp = "^[a-z0-9]*$",message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9)")
    private String password;


    private String authKey;
    private boolean isConfirm =false;
}
