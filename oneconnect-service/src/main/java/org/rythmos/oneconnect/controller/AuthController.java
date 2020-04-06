package org.rythmos.oneconnect.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.entity.Role;
import org.rythmos.oneconnect.entity.RoleName;
import org.rythmos.oneconnect.entity.User;
import org.rythmos.oneconnect.exception.AppException;
import org.rythmos.oneconnect.json.request.LoginJSONRequest;
import org.rythmos.oneconnect.json.request.SignUpJSONRequest;
import org.rythmos.oneconnect.json.response.AvatarJSONResponse;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.EmployeeRepository;
import org.rythmos.oneconnect.repository.ProjectRoleRepository;
import org.rythmos.oneconnect.repository.RoleRepository;
import org.rythmos.oneconnect.repository.UserRepository;
import org.rythmos.oneconnect.response.ApiResponse;
import org.rythmos.oneconnect.response.JwtAuthenticationResponse;
import org.rythmos.oneconnect.security.JwtTokenProvider;
import org.rythmos.oneconnect.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Prasanth Kusumaraju
 *
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	ProjectRoleRepository projectRoleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	BaseResponse baseResponse;

	@Autowired
	JwtTokenProvider tokenProvider;
	public static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginJSONRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String jwt = tokenProvider.generateToken(authentication);

		Long userId = tokenProvider.getUserIdFromJWT(jwt);
		logger.info("user Id is ::{}", userId);
		User user = userRepository.findUserById(userId);

		AvatarJSONResponse avatar = new AvatarJSONResponse();
		avatar.setData(Base64.encodeBase64String(user.getData()));
		avatar.setFileName(user.getFileName());
		avatar.setFileType(user.getFileType());

		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, avatar));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpJSONRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			logger.error("Username is already taken! {}", signUpRequest.getEmail());
			return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			logger.error("Email-Id is already taken! {}", signUpRequest.getEmail());
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
					HttpStatus.BAD_REQUEST);
		}
		if (employeeRepository.findActiveEmployeeByEmailId(signUpRequest.getEmail(),
				OneConnectConstants.employStatus.ACTIVE.toString()).isEmpty()) {
			logger.info("Sorry! you are unauthorized to Signup with Email-Id {}", signUpRequest.getEmail());
			baseResponse.setMessage("Sorry! you are unauthorized to Signup with Email-Id " + signUpRequest.getEmail());
			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword(), signUpRequest.getAdmin(), signUpRequest.getExecutive(),
				signUpRequest.getProjectRole());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = null;
		if (signUpRequest.getAdmin()) {
			userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
					.orElseThrow(() -> new AppException("User Role not set."));
		} else if (signUpRequest.getExecutive()) {
			userRole = roleRepository.findByName(RoleName.ROLE_EXECUTIVE)
					.orElseThrow(() -> new AppException("User Role not set."));
		} else {
			try {
				Long eId = employeeRepository.findEmployeeBymailId(signUpRequest.getEmail()).getId();
				String projectRole = projectRoleRepository
						.findMaxRoleByEmployeeId(OneConnectConstants.employProjectStatus.ACTIVE.toString(), eId);
				RoleName roleName = RoleName.valueOf(projectRole);
				logger.info("Rolename is::{}", roleName);
				if (roleName.equals(RoleName.ROLE_DELIVERYMANAGER) || roleName.equals(RoleName.ROLE_MANAGER)
						|| roleName.equals(RoleName.ROLE_LEAD) || roleName.equals(RoleName.ROLE_SCRUMMASTER)) {
					userRole = roleRepository.findByName(roleName)
							.orElseThrow(() -> new AppException("User Role not set."));
				} else {
					logger.info("Sorry! you are unauthorized to Signup");
					baseResponse.setMessage("Sorry! you are unauthorized to Signup");
					return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (IllegalArgumentException lae) {
				logger.info("Sorry! you are unauthorized to Signup");
				baseResponse.setMessage("Sorry! you are unauthorized to Signup");
				return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));
		user.setEmployee(employeeRepository.findEmployeeBymailId(signUpRequest.getEmail()));
		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}
