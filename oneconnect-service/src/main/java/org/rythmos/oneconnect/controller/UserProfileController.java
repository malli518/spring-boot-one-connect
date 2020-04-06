package org.rythmos.oneconnect.controller;

import java.io.IOException;

import org.rythmos.oneconnect.json.request.UpdatePwdJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.response.UserProfileResponse;
import org.rythmos.oneconnect.security.UserPrincipal;
import org.rythmos.oneconnect.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mahalakshmi
 *
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/profile")
public class UserProfileController {
	public static Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private BaseResponse baseResponse;

	@GetMapping("/getUserProfile")
	public UserProfileResponse<?> getUserProfile(@RequestParam Long eId) {
		logger.info("id is::{}", eId);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserPrincipal) {
			Long id = ((UserPrincipal) principal).getEmployee().getId();
			if (id == eId) {
				return userProfileService.getUserProfile(eId);
			} else {
				logger.info("Unauthorized user");
				baseResponse.setMessage("Unauthorized user");
				return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("Invalid Request");
			baseResponse.setMessage("Invalid Request");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/updatePassword")
	public UserProfileResponse<?> updatePassword(@RequestBody UpdatePwdJSONRequest updatePwdJSONRequest) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserPrincipal) {
			Long id = ((UserPrincipal) principal).getId();
			if (id == updatePwdJSONRequest.getId()) {
				return userProfileService.updatePassword(updatePwdJSONRequest);
			} else {
				logger.info("Unauthorized user");
				baseResponse.setMessage("Unauthorized user");
				return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("Invalid Request");
			baseResponse.setMessage("Invalid Request");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/updateImage")
	public UserProfileResponse<?> updateImage(@RequestParam("avatar") MultipartFile avatar,
			@RequestParam("userId") Long userId) throws IOException {
		return userProfileService.updateImage(avatar, userId);
	}

}
