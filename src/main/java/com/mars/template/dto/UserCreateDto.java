package com.mars.template.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto implements Serializable {
	@Size(max = 13, message = "RFC must not exceed 13 characters")
	private String rfc;

	@Size(max = 18, message = "CURP must not exceed 18 characters")
	private String curp;

	@NotBlank(message = "Username is required")
	@Size(max = 50, message = "Username must not exceed 50 characters")
	private String username;

	@NotBlank(message = "First name is required")
	@Size(max = 100, message = "First name must not exceed 100 characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(max = 100, message = "Last name must not exceed 100 characters")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	@Size(max = 150, message = "Email must not exceed 150 characters")
	private String email;

	@Size(max = 15, message = "Phone number must not exceed 15 characters")
	private String phoneNumber;

	@Past(message = "Birth date must be in the past")
	private LocalDate birthDate;
}
