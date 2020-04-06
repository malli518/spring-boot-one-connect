package org.rythmos.oneconnect.service;

import java.util.ArrayList;
import java.util.List;

import org.rythmos.oneconnect.audit.AuditorAwareImpl;
import org.rythmos.oneconnect.bean.NotificationBean;
import org.rythmos.oneconnect.bean.NotificationGroupBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.dao.NotificationDAO;
import org.rythmos.oneconnect.entity.Notification;
import org.rythmos.oneconnect.json.request.IdJSONRequest;
import org.rythmos.oneconnect.json.request.NotificationJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.NotificationGroupResponse;
import org.rythmos.oneconnect.repository.NotificationRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectServiceUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class NotificationService {
	@Autowired
	private OneConnectServiceUtility oneConnectServiceUtility;

	@Autowired
	private NotificationDAO notificationDAO;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private ProjectDetailRepository projectDetailRepository;
	@Autowired
	private AuditorAwareImpl auditorAwareImpl;

	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private NotificationRepository notificationRepository;

	List<Long> userIdList = new ArrayList<>();
	List<Long> adminList = new ArrayList<>();
	List<Long> execetiveList = new ArrayList<>();
	public static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	public ResponseEntity<?> notify(NotificationJSONRequest request) {

		logger.info("NotificationService : notify Start {}", request);
		try {
			// fetch all users who all need to be notified
			if (null != request.getProjectId()) {
				userIdList = getUserList(request.getProjectId());
				adminList = getAdminList();
				userIdList.addAll(adminList);

				logger.info("NotificationService : notify ProjectId {},ListOfResources {}", request.getProjectId(),
						userIdList);
			}

			if (!userIdList.isEmpty()) {
				// build notification body
				request.setNotificationBody(notificationBodyBuilder(request));
				if (!request.getNotificationBody().isEmpty()) {
					// add notification in notification table
					// add notification in notification group

					if (addNotification(request, userIdList).getStatusCode().equals(HttpStatus.OK)) {
						logger.info("NotificationService : notify :: Notification added and publiched ");
						baseResponse.setMessage("Notification added and publiched ");
						return new ResponseEntity<>(baseResponse, HttpStatus.OK);
					} else {
						logger.info("NotificationService : notify :: Error while adding and publishing Notification ");
						baseResponse.setMessage("Error while adding and publishing Notification");
						return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);

					}
				} else {
					logger.info("NotificationService : notify :: Error building in notificaation Body");
					baseResponse.setMessage("Error building in notificaation Body");
					return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				logger.info(
						"NotificationService : notify :: Error Saving No User is associated with this notification");
				baseResponse.setMessage("Error Saving No User is associated with this notification");
				return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);

			}

		} catch (Exception e) {
			logger.info("NotificationService : notify :: Exception while Saving the notification ");
			baseResponse.setMessage("Exception while Saving the notification ");
			return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private String notificationBodyBuilder(NotificationJSONRequest request) {

		String notificationBody = "";

		logger.info("NotificationService : notificationBodyBuilder :: Notification String {} ", notificationBody);
		String user = auditorAwareImpl.getCurrentAuditor().get();
		String type = request.getReportType();
		String operation = request.getOperationType();
		java.util.Date date=dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				request.getStartDate());
		String reportDate = request.getStartDate();
		String projectName = projectDetailRepository.findProjectNameById(request.getProjectId());
		List<Notification> notificationList=notificationRepository.findExistNotification(request.getClientId(), request.getPortfolioId(), request.getProjectId(),new java.sql.Timestamp(date.getTime()), type, request.getIsMessage());
		if(notificationList.isEmpty()) {
		  request.setOperationType("save");
		}
		logger.info("notificationList is::{}",notificationList);
		if (!type.equals("") && !request.getIsMessage()) {

			switch (type) {

			case OneConnectConstants.EXECUTIVE_SUMMARY_REPORT:

				switch (request.getOperationType()) {
				case OneConnectConstants.SAVE:
					notificationBody = user + " has submitted an Executive Summary Report for " + reportDate
							+ " for Project  " + projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);
					break;
				case OneConnectConstants.UPDATE:
					notificationBody = user + " has updated an Executive Summary Report for " + reportDate
							+ " for Project  " + projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);
					break;
				default:
					logger.info("Invalid Operation for EXECUTIVE_SUMMARY_REPORT");
					break;
				}

				break;
			case OneConnectConstants.WEEKLY_SUMMARY_REPORT:

				switch (request.getOperationType()) {
				case OneConnectConstants.SAVE:
					notificationBody = user + " has submitted Weekly Report for " + reportDate + " for Project  "
							+ projectName;
					break;
				case OneConnectConstants.UPDATE:
					notificationBody = user + " has updated Weekly Report for " + reportDate + " for Project  "
							+ projectName;
					break;
				case OneConnectConstants.APPROVE:
					notificationBody = user + " has approved the Weekly Report for " + reportDate
							+ " for Project  " + projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);

					break;
				default:
					logger.info("Invalid Operation for WEEKLY_SUMMARY_REPORT");
					break;

				}

				break;

			case OneConnectConstants.CLIENT_HEALTH_REPORT:
				reportDate = dateUtil.getMonthYear(reportDate);
				switch (request.getOperationType()) {
				case OneConnectConstants.SAVE:
					notificationBody = user + " has submitted Client Health Report for " + reportDate + " for Project  "
							+ projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);
					break;
				case OneConnectConstants.UPDATE:
					notificationBody = user + " has updated Client Health Report for " + reportDate + " for Project  "
							+ projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);
					break;
				default:
					logger.info("Invalid Operation for CLIENT_HEALTH_REPORT");
					break;
				}

				break;

			case OneConnectConstants.AGILE_MATURITY_ASSESSMENT:
				reportDate = dateUtil.getMonthYear(reportDate);
				switch (request.getOperationType()) {
				case OneConnectConstants.SAVE:
					notificationBody = user + " has submitted an Agile Maturity Assessment Report for " + reportDate
							+ " for Project  " + projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);
					break;
				case OneConnectConstants.UPDATE:
					notificationBody = user + " has updated an Agile Maturity Assessment Report for " + reportDate
							+ " for Project  " + projectName;
					execetiveList = getExecetiveList();
					userIdList.addAll(execetiveList);
					break;
				default:
					logger.info("Invalid Operation for AGILE_MATURITY_ASSESSMENT");
					break;

				}

				break;

			case OneConnectConstants.RESOURCE:
				String resourse = request.getNotificationBody();
				switch (request.getOperationType()) {
				case OneConnectConstants.SAVE:
					notificationBody = user + " has mapped the Resource " + resourse + " to the Project  "
							+ projectName;
					break;

				case OneConnectConstants.UPDATE:
					notificationBody = user + " has updated the Resource " + resourse + " for the Project  "
							+ projectName;
					break;

				case OneConnectConstants.DELETE:
					notificationBody = user + " has deleted the Resource " + resourse + " from the Project  "
							+ projectName;
					break;

				default:
					logger.info("Invalid Operation for Resource");
					break;
				}

				break;

			default:
				logger.info("Invalid Report Type");
				break;
			}
			logger.info("NotificationService : notificationBodyBuilder :: Notification String $ {} $ RETURN ",
					notificationBody);
			return notificationBody;
		} else if (request.getIsMessage() && operation.equals(OneConnectConstants.SAVE)) {

			notificationBody = user + " posted a message in " + type + " for " + reportDate + " for  " + projectName;
			execetiveList = getExecetiveList();
			userIdList.addAll(execetiveList);

		} else {
			logger.info("Message DEFAULT");
		}
		logger.info("NotificationService : notificationBodyBuilder :: Notification String $ {} $ RETURN ",
				notificationBody);
		return notificationBody;
	}

	public ResponseEntity<?> addNotification(NotificationJSONRequest request, List<Long> userIdList) {
		logger.info("NotificationService : addNotification :: \n Notification  {} \n ListOfUsers {}", request,
				userIdList);
		NotificationBean notificationBean = new NotificationBean();
		notificationBean.setClientDetail(oneConnectServiceUtility.getClientById(request.getClientId()));
		notificationBean.setPortfolio(oneConnectServiceUtility.getPortfolioById(request.getPortfolioId()));
		notificationBean.setProjectDetail(oneConnectServiceUtility.getProjectDetailById(request.getProjectId()));
		notificationBean.setOperationType(request.getOperationType());
		notificationBean.setReportType(request.getReportType());
		notificationBean.setStartDate(
				(dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT, request.getStartDate())));
		notificationBean.setNotificationBody(request.getNotificationBody());

		if (request.getIsMessage()) {
			notificationBean.setMessageId(request.getMessageId());
			notificationBean.setRoom(request.getRoom());
			notificationBean.setIsMessage(request.getIsMessage());
		} else {
			notificationBean.setIsMessage(false);
		}

		NotificationBean savedNotificationBean = notificationDAO.addNotification(notificationBean);

		if (null != savedNotificationBean && notificationDAO.publishToUsers(savedNotificationBean, userIdList)) {
			baseResponse.setMessage("Notification added and published ");
			return new ResponseEntity<>(baseResponse, HttpStatus.OK);
		} else {
			logger.info(
					"NotificationService : addNotification :: Error Saving in notification \n savedNotificationBean {}",
					savedNotificationBean);
			baseResponse.setMessage("Error Saving in notification ");
			return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<Long> getUserList(@RequestParam Long projectId) {
		return oneConnectServiceUtility.getUsersByProject(projectId);
	}

	private List<Long> getAdminList() {
		return oneConnectServiceUtility.getAdminList();
	}

	private List<Long> getExecetiveList() {
		return oneConnectServiceUtility.getExecetiveList();
	}

	public NotificationGroupResponse convertToNotificationGroupJSONResponse(
			NotificationGroupBean notificationGroupBean) {

		NotificationGroupResponse notificationGroupResponse = new NotificationGroupResponse();
		notificationGroupResponse.setId(notificationGroupBean.getId());
		notificationGroupResponse.setCreatedBy(notificationGroupBean.getCreatedBy());
		notificationGroupResponse.setCreatedDate(notificationGroupBean.getCreatedDate());
		notificationGroupResponse.setLastModifiedBy(notificationGroupBean.getLastModifiedBy());
		notificationGroupResponse.setLastModifiedDate(notificationGroupBean.getLastModifiedDate());
		notificationGroupResponse.setIsRead(notificationGroupBean.getIsRead());
		notificationGroupResponse.setClientId(notificationGroupBean.getNotification().getClientDetail().getId());
		notificationGroupResponse.setPortfolioId(notificationGroupBean.getNotification().getPortfolio().getId());
		notificationGroupResponse.setProjectId(notificationGroupBean.getNotification().getProjectDetail().getId());
		notificationGroupResponse.setOperationType(notificationGroupBean.getNotification().getOperationType());
		notificationGroupResponse.setReportType(notificationGroupBean.getNotification().getReportType());
		notificationGroupResponse.setNotificationBody(notificationGroupBean.getNotification().getNotificationBody());
		notificationGroupResponse.setStartDate(notificationGroupBean.getNotification().getStartDate());
		notificationGroupResponse.setIsMessage(notificationGroupBean.getNotification().getIsMessage());
		notificationGroupResponse.setRoom(notificationGroupBean.getNotification().getRoom());
		notificationGroupResponse.setMessageId(notificationGroupBean.getNotification().getMessageId());
		return notificationGroupResponse;
	}

	public ResponseEntity<?> getUserNotifications(Long userId) {
		List<NotificationGroupBean> notificationBeanList = notificationDAO.getUserNotifications(userId);
		List<NotificationGroupResponse> notificationGroupResponseList = new ArrayList<>();
		if (notificationBeanList != null && notificationBeanList.size() > 0) {
			for (NotificationGroupBean notificationGroup : notificationBeanList) {
				notificationGroupResponseList.add(convertToNotificationGroupJSONResponse(notificationGroup));
			}

			return new ResponseEntity<>(notificationGroupResponseList, HttpStatus.OK);

		} else {
			logger.info("Notification list not found");
			baseResponse.setMessage("Notification list not found");
			return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> getUnReadNotifications(Long userId) {
		List<NotificationGroupBean> notificationBeanList = notificationDAO.getUnReadNotifications(userId);
		List<NotificationGroupResponse> notificationGroupResponseList = new ArrayList<>();
		if (notificationBeanList != null && !notificationBeanList.isEmpty()) {
			for (NotificationGroupBean notificationGroup : notificationBeanList) {
				notificationGroupResponseList.add(convertToNotificationGroupJSONResponse(notificationGroup));
			}

			return new ResponseEntity<>(notificationGroupResponseList, HttpStatus.OK);

		} else {
			logger.info("Notification list not found");
			baseResponse.setMessage("Notification list not found");
			return new ResponseEntity<>(notificationGroupResponseList, HttpStatus.NO_CONTENT);
		}
	}

	public ResponseEntity<?> markAllNotificationsAsRead(IdJSONRequest user) {
		Long userId = Long.parseLong(user.getId());
		return notificationDAO.markAllNotificationsAsRead(userId);

	}

	public ResponseEntity<?> markNotificationAsRead(IdJSONRequest notification) {
		Long notificationId = Long.parseLong(notification.getId());
		return notificationDAO.markNotificationAsRead(notificationId);

	}

}
