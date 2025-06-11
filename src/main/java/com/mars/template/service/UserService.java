package com.mars.template.service;

import com.mars.template.dto.UserCreateDto;
import com.mars.template.dto.UserResponseDto;
import com.mars.template.dto.UserUpdateDto;
import com.mars.template.entity.UserEntity;
import com.mars.template.repository.UserRepository;
import com.mars.template.utils.MapperUtils;
import com.mars.template.utils.enums.UserStatus;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;


/**
 * @author Angel Ruiz
 * @version 1.0.0
 * date 10/06/25
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;

	public UserResponseDto createUser(UserCreateDto request) {
		log.info("Creating new user with username: {}", request.getUsername());

		validateUniqueFields(request.getUsername(), request.getEmail(), request.getRfc(), request.getCurp(), null);

		UserEntity user = MapperUtils.map(request, UserEntity.class);
		user.setStatus(UserStatus.ACTIVE);

		UserEntity savedUser = userRepository.save(user);
		log.info("User created successfully with ID: {}", savedUser.getId());

		return MapperUtils.map(savedUser, UserResponseDto.class);
	}

	@Transactional(readOnly = true)
	public UserResponseDto getUserById(Long id) {
		log.debug("Fetching user by ID: {}", id);
		UserEntity user = findUserById(id);
		return MapperUtils.map(user, UserResponseDto.class);
	}

	@Transactional(readOnly = true)
	public Page<UserResponseDto> getAllUsers(String username, String email, UserStatus status, Pageable pageable) {
		log.debug("Fetching users with filters - username: {}, email: {}, status: {}", username, email, status);

		Specification<UserEntity> spec = createUserSpecification(username, email, status);
		Page<UserEntity> users = userRepository.findAll(spec, pageable);

		return users.map(user -> MapperUtils.map(user, UserResponseDto.class));
	}

	public UserResponseDto updateUser(Long id, UserUpdateDto request) {
		log.info("Updating user with ID: {}", id);

		UserEntity existingUser = findUserById(id);
		validateUniqueFields(request.getUsername(), request.getEmail(), request.getRfc(), request.getCurp(), id);

		MapperUtils.map(request, existingUser);
		UserEntity updatedUser = userRepository.save(existingUser);
		log.info("User updated successfully with ID: {}", updatedUser.getId());

		return MapperUtils.map(updatedUser, UserResponseDto.class);
	}

	public UserResponseDto updateUserStatus(Long id, UserStatus status) {
		log.info("Updating user status for ID: {} to: {}", id, status);

		UserEntity user = findUserById(id);
		user.setStatus(status);

		UserEntity updatedUser = userRepository.save(user);
		log.info("User status updated successfully for ID: {}", id);

		return MapperUtils.map(updatedUser, UserResponseDto.class);
	}

	public void deleteUser(Long id) {
		log.info("Soft deleting user with ID: {}", id);

		UserEntity user = findUserById(id);
		user.setStatus(UserStatus.INACTIVE);

		userRepository.save(user);
		log.info("User soft deleted successfully with ID: {}", id);
	}

	@Transactional(readOnly = true)
	public UserResponseDto getUserByRfc(String rfc) {
		log.debug("Fetching user by RFC: {}", rfc);
		UserEntity user = userRepository.findByRfc(rfc)
				.orElseThrow(() -> new RuntimeException("User not found with RFC: " + rfc));
		return MapperUtils.map(user, UserResponseDto.class);
	}

	@Transactional(readOnly = true)
	public UserResponseDto getUserByCurp(String curp) {
		log.debug("Fetching user by CURP: {}", curp);
		UserEntity user = userRepository.findByCurp(curp)
				.orElseThrow(() -> new RuntimeException("User not found with CURP: " + curp));
		return MapperUtils.map(user, UserResponseDto.class);
	}

	private UserEntity findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
	}

	private void validateUniqueFields(String username, String email, String rfc, String curp, Long excludeId) {
		if (username != null && userRepository.existsByUsernameAndIdNot(username, excludeId != null ? excludeId : 0L)) {
			throw new RuntimeException("Username already exists: " + username);
		}
		if (email != null && userRepository.existsByEmailAndIdNot(email, excludeId != null ? excludeId : 0L)) {
			throw new RuntimeException("Email already exists: " + email);
		}
		if (rfc != null && userRepository.existsByRfcAndIdNot(rfc, excludeId != null ? excludeId : 0L)) {
			throw new RuntimeException("RFC already exists: " + rfc);
		}
		if (curp != null && userRepository.existsByCurpAndIdNot(curp, excludeId != null ? excludeId : 0L)) {
			throw new RuntimeException("CURP already exists: " + curp);
		}
	}

	private Specification<UserEntity> createUserSpecification(String username, String email, UserStatus status) {
		return (root, query, criteriaBuilder) -> {
			var predicates = new ArrayList<Predicate>();

			Optional.ofNullable(username)
					.filter(u -> !u.isBlank())
					.ifPresent(u -> predicates.add(
							criteriaBuilder.like(
									criteriaBuilder.lower(root.get("username")),
									"%" + u.toLowerCase() + "%"
							)
					));

			Optional.ofNullable(email)
					.filter(e -> !e.isBlank())
					.ifPresent(e -> predicates.add(
							criteriaBuilder.like(
									criteriaBuilder.lower(root.get("email")),
									"%" + e.toLowerCase() + "%"
							)
					));

			Optional.ofNullable(status)
					.ifPresent(s -> predicates.add(criteriaBuilder.equal(root.get("status"), s)));

			return predicates.isEmpty()
					? criteriaBuilder.conjunction()
					: criteriaBuilder.and(predicates.toArray(Predicate[]::new));
		};
	}
}
