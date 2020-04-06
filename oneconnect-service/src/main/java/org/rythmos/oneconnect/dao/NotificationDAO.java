package org.rythmos.oneconnect.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rythmos.oneconnect.bean.NotificationBean;
import org.rythmos.oneconnect.bean.NotificationGroupBean;
import org.rythmos.oneconnect.entity.Notification;
import org.rythmos.oneconnect.entity.NotificationGroup;
import org.rythmos.oneconnect.entity.User;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.NotificationGroupRepository;
import org.rythmos.oneconnect.repository.NotificationRepository;
import org.rythmos.oneconnect.repository.UserRepository;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class NotificationDAO {

	@Autowired
	OneConnectDAOUtility oneConnectDAOUtility;
	@Autowired
	private NotificationGroupRepository notificationGroupRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private NotificationRepository notificationRepository;
	public static final Logger logger = LoggerFactory.getLogger(NotificationDAO.class);

	private Notification convertToEntity(NotificationBean notificationBean) {
		return oneConnectDAOUtility.modelMapper.map(notificationBean, Notification.class);
	}

	private NotificationGroupBean convertToBean(NotificationGroup notificationGroup) {
		return oneConnectDAOUtility.modelMapper.map(notificationGroup, NotificationGroupBean.class);
	}

	private NotificationBean convertToBean(Notification notification) {

		NotificationBean notificationBean = oneConnectDAOUtility.modelMapper.map(notification, NotificationBean.class);
		logger.info("NotificationDAO : convertToBean :: NotificationBean {}", notificationBean);
		return notificationBean;
	}

	public Boolean publishToUsers(NotificationBean savedNotificationBean, List<Long> userIdList) {
		Boolean status = true;
		logger.info("NotificationDAO : publishToUsers ::Start");
		try {
			Notification notification = convertToEntity(savedNotificationBean);
			for (Long id : userIdList) {
				Optional<User> user = userRepository.findById(id);
				if (user.isPresent()) {
					NotificationGroup notificationGroup = new NotificationGroup();
					notificationGroup.setIsRead(false);
					notificationGroup.setUser(user.get());
					notificationGroup.setNotification(notification);
					notificationGroupRepository.save(notificationGroup);
					logger.info("NotificationDAO : publishToUsers :: UserId {} NotificationId {} Added successfully",
							user.get().getId(), notification.getId());
				} else {
					status = false;
					logger.info(
							"NotificationDAO : publishToUsers :: UserId {} NotificationId {} Unable to add notification for the user with userId",
							user.get().getId(), notification.getId());

				}
			}
			logger.info("NotificationDAO : publishToUsers :: End Return {}", status);
			return status;
		} catch (Exception e) {

			logger.info("NotificationDAO : publishToUsers :: Exception -- End Return {}", false);

			return false;
		}

	}

	public NotificationBean addNotification(NotificationBean notificationBean) {
		logger.info("NotificationDAO : addNotification :: Start NotificationBean {} ", notificationBean);
		Notification notification = convertToEntity(notificationBean);
		try {
			notificationRepository.save(notification);
			logger.info("Query parameters::{} {}", notification.getLastModifiedBy(),
					notification.getLastModifiedDate());
			Notification RespNotification = notificationRepository.findByUserAndTime(notification.getLastModifiedBy());
			logger.info("NotificationDAO : addNotification :: End Notification saved and the response is {}",
					RespNotification);
			return convertToBean(RespNotification);
		} catch (Exception E) {
			logger.info("NotificationDAO : addNotification :: Exception End ");
			return null;

		}
	}

	public List<NotificationGroupBean> getUserNotifications(@RequestParam Long userId) {
		List<NotificationGroup> notificationGroupList = notificationGroupRepository.findNotificationByUserId(userId);
		return notificationGroupList.stream().map(notificationGroup -> convertToBean(notificationGroup))
				.collect(Collectors.toList());

	}

	public List<NotificationGroupBean> getUnReadNotifications(@RequestParam Long userId) {
		List<NotificationGroup> notificationGroupList = notificationGroupRepository
				.findUnReadNotificationByUserId(userId);
		return notificationGroupList.stream().map(notificationGroup -> convertToBean(notificationGroup))
				.collect(Collectors.toList());

	}

	public ResponseEntity<?> markAllNotificationsAsRead(@RequestParam Long userId) {
		List<NotificationGroup> notificationGroupList = notificationGroupRepository.findNotificationByUserId(userId);
		logger.info("Notification Group list is::{}", notificationGroupList);
		for (NotificationGroup notificationGroup : notificationGroupList) {

			notificationGroup.setIsRead(true);
			if (notificationGroupRepository.save(notificationGroup) != null) {
				logger.info("Is read updated");
				baseResponse.setMessage("Is read updated");

			}

		}
		return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);

	}

	public ResponseEntity<?> markNotificationAsRead(@RequestParam Long notificationId) {
		Optional<NotificationGroup> notification = notificationGroupRepository.findById(notificationId);
		logger.info("Is Notification Present :: {}", notification.isPresent());
		if (notification.isPresent()) {
			NotificationGroup notificationGroup = notification.get();
			notificationGroup.setIsRead(true);
			if (null != notificationGroupRepository.save(notificationGroup)) {
				logger.info("Is read updated");
				baseResponse.setMessage("Is read updated");
			}
			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
		} else {
			baseResponse.setMessage("Is read is not Updated");

			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_FOUND);
		}

	}

}
