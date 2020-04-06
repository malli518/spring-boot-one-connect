package org.rythmos.oneconnect.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rythmos.oneconnect.bean.EmployeeAuthenticationBean;
import org.rythmos.oneconnect.bean.EmployeeProjectMappingBean;
import org.rythmos.oneconnect.bean.UserBean;
import org.rythmos.oneconnect.controller.WeeklyReportController;
import org.rythmos.oneconnect.dao.UserProfileDAO;
import org.rythmos.oneconnect.entity.User;
import org.rythmos.oneconnect.json.request.UpdatePwdJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.EmployeeJSONResponse;
import org.rythmos.oneconnect.repository.EmployeeAuthenticationRespository;
import org.rythmos.oneconnect.response.UserProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {

	@Autowired
	private UserProfileDAO userProfileDAO;
	public static Logger logger = LoggerFactory.getLogger(WeeklyReportController.class);

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private AdminService adminService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeAuthenticationRespository employeeAuthenticationRespository;

	public UserProfileResponse<?> getUserProfile(Long eId) {

		List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = userProfileDAO.getUserProfile(eId);
		List<EmployeeJSONResponse> employeeJSONResponseList = new ArrayList<EmployeeJSONResponse>();
		if (employeeProjectMappingBeanList != null && employeeProjectMappingBeanList.size() > 0) {

			for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {
				employeeJSONResponseList
						.add(adminService.convertToEmployeeProjectMappingJSONResponse(employeeProjectMappingBean));
			}
			logger.info("EmployeeProjectMappingBean Response for getALLProjectResource Operation {}",
					employeeJSONResponseList);
			employeeJSONResponseList = adminService.sortEmployeeJSONResponse(employeeJSONResponseList);
			return new UserProfileResponse<EmployeeJSONResponse>(employeeJSONResponseList.get(0), null, HttpStatus.OK);
		} else {
			logger.info("No Resources found!");
			baseResponse.setMessage("No Resources found");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public UserProfileResponse<?> updatePassword(@RequestBody UpdatePwdJSONRequest updatePwdJSONRequest) {
		if (updatePwdJSONRequest != null) {

			User userAuthentication = employeeAuthenticationRespository.findByEmpId(updatePwdJSONRequest.getId());
			if (passwordEncoder.matches(updatePwdJSONRequest.getCurrentPassword(), userAuthentication.getPassword())) {
				EmployeeAuthenticationBean employeeAuthenticationBean = new EmployeeAuthenticationBean();
				employeeAuthenticationBean.setId(updatePwdJSONRequest.getId());
				employeeAuthenticationBean.setPassword(updatePwdJSONRequest.getNewPassword());
				return userProfileDAO.updatePassword(employeeAuthenticationBean);
			} else {
				logger.info("Incorrect Current Password!");
				baseResponse.setMessage("Incorrect Current Password!");
				return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Invalid Request");
			baseResponse.setMessage("Invalid Request");
			return new UserProfileResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	public UserProfileResponse<?> updateImage(@RequestParam("avatar") MultipartFile avatar,
			@RequestParam("userId") Long userId) throws IOException {

		UserBean userBean = new UserBean();
		userBean.setId(userId);
		userBean.setData(avatar.getBytes());
		userBean.setFileType(avatar.getContentType());
		userBean.setFileName(avatar.getOriginalFilename());
		return userProfileDAO.updateImage(userBean);

	}

}
