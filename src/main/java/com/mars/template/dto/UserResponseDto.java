package com.mars.template.dto;

import com.mars.template.utils.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Angel Ruiz
 * @version 1.0.0
 * date 10/06/25
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto implements Serializable {

	private Long id;
	private String rfc;
	private String curp;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate birthDate;
	private UserStatus status;
	private String createdBy;
	private LocalDateTime createdAt;
	private String updatedBy;
	private LocalDateTime updatedAt;

}
