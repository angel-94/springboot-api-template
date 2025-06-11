package com.mars.template.controller;


import com.mars.template.dto.UserCreateDto;
import com.mars.template.dto.UserResponseDto;
import com.mars.template.dto.UserUpdateDto;
import com.mars.template.service.UserService;
import com.mars.template.utils.enums.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Angel Ruiz
 * @version 1.0.0
 * date 10/06/25
 */
@Tag(name = "USERS")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/wt-returns/fee")
public class UserController {

	private final UserService userService;

	@Operation(summary = "Create new user", description = "Creates a new user in the system")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@Valid @RequestBody UserCreateDto request) {
		return userService.createUser(request);
	}

	@Operation(summary = "Get user by ID", description = "Retrieves user details by ID")
	@GetMapping("/{id}")
	public UserResponseDto getUserById(
			@Parameter(description = "User ID") @PathVariable Long id) {
		return userService.getUserById(id);
	}

	@Operation(summary = "Get all users", description = "Retrieves paginated list of users with optional filters")
	@GetMapping
	public Page<UserResponseDto> getAllUsers(
			@Parameter(description = "Username filter") @RequestParam(required = false) String username,
			@Parameter(description = "Email filter") @RequestParam(required = false) String email,
			@Parameter(description = "Status filter") @RequestParam(required = false) UserStatus status,
			@PageableDefault(size = 20) Pageable pageable) {
		return userService.getAllUsers(username, email, status, pageable);
	}

	@Operation(summary = "Update user", description = "Updates an existing user")
	@PutMapping("/{id}")
	public UserResponseDto updateUser(
			@Parameter(description = "User ID") @PathVariable Long id,
			@Valid @RequestBody UserUpdateDto request) {
		return userService.updateUser(id, request);
	}

	@Operation(summary = "Update user status", description = "Updates user status (ACTIVE, INACTIVE, BLOCKED)")
	@PatchMapping("/{id}/status")
	public UserResponseDto updateUserStatus(
			@Parameter(description = "User ID") @PathVariable Long id,
			@Parameter(description = "New status") @RequestParam UserStatus status) {
		return userService.updateUserStatus(id, status);
	}

	@Operation(summary = "Delete user", description = "Soft deletes a user by setting status to INACTIVE")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@Parameter(description = "User ID") @PathVariable Long id) {
		userService.deleteUser(id);
	}

	@Operation(summary = "Search users by RFC", description = "Finds user by RFC number")
	@GetMapping("/rfc/{rfc}")
	public UserResponseDto getUserByRfc(@Parameter(description = "RFC number") @PathVariable String rfc) {
		return userService.getUserByRfc(rfc);
	}

	@Operation(summary = "Search users by CURP", description = "Finds user by CURP number")
	@GetMapping("/curp/{curp}")
	public UserResponseDto getUserByCurp(@Parameter(description = "CURP number") @PathVariable String curp) {
		return userService.getUserByCurp(curp);
	}

}
