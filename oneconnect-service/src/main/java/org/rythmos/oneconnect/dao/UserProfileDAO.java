package org.rythmos.oneconnect.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.rythmos.oneconnect.bean.EmployeeAuthenticationBean;
import org.rythmos.oneconnect.bean.EmployeeProjectMappingBean;
import org.rythmos.oneconnect.bean.UserBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.controller.WeeklyReportController;
import org.rythmos.oneconnect.entity.EmployeeProjectMapping;
import org.rythmos.oneconnect.entity.User;
import org.rythmos.oneconnect.json.response.AvatarJSONResponse;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.EmployeeAuthenticationRespository;
import org.rythmos.oneconnect.repository.EmployeeProjectMappingRepository;
import org.rythmos.oneconnect.repository.UserRepository;
import org.rythmos.oneconnect.response.UserProfileResponse;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDAO {
	@Autowired
	private EmployeeProjectMappingRepository employeeProjectMappingRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static Logger logger = LoggerFactory.getLogger(WeeklyReportController.class);

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	@Autowired
	private EmployeeAuthenticationRespository employeeAuthenticationRespository;

	public List<EmployeeProjectMappingBean> getUserProfile(Long eId) {
		List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
				.findActiveEmployeeProjectByEmployeeId(OneConnectConstants.employProjectStatus.ACTIVE.toString(), eId);
		if (!employeeProjectMappingList.isEmpty()) {
			return employeeProjectMappingList.stream()
					.map(employeeProjectMapping -> convertToBean(employeeProjectMapping)).collect(Collectors.toList());
		} else {
			return new ArrayList<EmployeeProjectMappingBean>();

		}
	}

	private EmployeeProjectMappingBean convertToBean(EmployeeProjectMapping employeeProjectMapping) {
		EmployeeProjectMappingBean employeeProjectMappingBean = oneConnectDAOUtility.modelMapper
				.map(employeeProjectMapping, EmployeeProjectMappingBean.class);
		return employeeProjectMappingBean;
	}

	public UserProfileResponse<?> updatePassword(EmployeeAuthenticationBean employeeAuthenticationBean) {
		User userAuthentication = employeeAuthenticationRespository.findByEmpId(employeeAuthenticationBean.getId());
		userAuthentication.setPassword(passwordEncoder.encode(employeeAuthenticationBean.getPassword()));
		if (employeeAuthenticationRespository.save(userAuthentication) != null) {
			logger.info("Password changed successfully");
			baseResponse.setMessage("Password changed successfully");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Unable to change password");
			baseResponse.setMessage("Unable to change password");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public UserProfileResponse<?> updateImage(UserBean userBean) {
		User user = userRepository.findUserById(userBean.getId());
		if (user != null) {
			user.setData(userBean.getData());
			user.setFileName(userBean.getFileName());
			user.setFileType(userBean.getFileType());
			if (userRepository.save(user) != null) {
				AvatarJSONResponse avatar = new AvatarJSONResponse();

				User user1 = userRepository.findUserById(userBean.getId());
				avatar.setId(user1.getId());
				avatar.setData(Base64.encodeBase64String(user1.getData()));
				avatar.setFileName(user1.getFileName());
				avatar.setFileType(user1.getFileType());
				logger.info("Profile Image Updated successfully");

				return new UserProfileResponse<>(avatar, null, HttpStatus.OK);
			} else {
				logger.info("Unable to Update Profile Image");
				baseResponse.setMessage("Unable to Update Profile Image");
				return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			logger.info("User not found");
			baseResponse.setMessage("User not found");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public Optional<String> findEmailByUserName(String userName) {

		return userRepository.findEmailByUserName(userName);

	}

}
