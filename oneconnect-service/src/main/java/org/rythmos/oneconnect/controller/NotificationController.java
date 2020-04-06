package org.rythmos.oneconnect.controller;

import org.rythmos.oneconnect.json.request.IdJSONRequest;
import org.rythmos.oneconnect.json.request.NotificationJSONRequest;
import org.rythmos.oneconnect.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/notification")
public class NotificationController {

	public final static Logger logger = LoggerFactory.getLogger(NotificationController.class);
	@Autowired
	private NotificationService notificationService;

	@PostMapping("/notify")
	public ResponseEntity<?> notify(@RequestBody NotificationJSONRequest request) {
		logger.info("NotificationController Receieved Request :: {}", request);
		return notificationService.notify(request);
	}

	@GetMapping("/getUserNotifications")
	public ResponseEntity<?> getUserNotifications(@RequestParam Long userId) {
		return notificationService.getUserNotifications(userId);
	}

	@GetMapping("/getUnReadNotifications")
	public ResponseEntity<?> getUnReadNotifications(@RequestParam Long userId) {
		return notificationService.getUnReadNotifications(userId);
	}

	@PutMapping("/markAllNotificationsAsRead")
	public ResponseEntity<?> markAllNotificationsAsRead(@RequestBody IdJSONRequest userId) {
		return notificationService.markAllNotificationsAsRead(userId);

	}

	@PutMapping("/markNotificationAsRead")
	public ResponseEntity<?> markNotificationAsRead(@RequestBody IdJSONRequest notificationId) {
		return notificationService.markNotificationAsRead(notificationId);
	}

}
