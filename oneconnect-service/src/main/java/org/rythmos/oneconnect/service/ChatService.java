package org.rythmos.oneconnect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.rythmos.oneconnect.bean.ChatMessageBean;
import org.rythmos.oneconnect.bean.SenderBean;
import org.rythmos.oneconnect.dao.ChatDAO;
import org.rythmos.oneconnect.dao.UserProfileDAO;
import org.rythmos.oneconnect.json.request.ChatJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.MessageResponse;
import org.rythmos.oneconnect.response.ChatMessageResponse;
import org.rythmos.oneconnect.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ChatService {
	public static Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private ChatDAO chatDAO;
	@Autowired
	private UserProfileDAO userProfileDAO;

	@Autowired
	private BaseResponse baseResponse;

	public ChatMessageResponse<?> saveChat(@RequestBody ChatJSONRequest chatJSONRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (chatJSONRequest.getMessage().getSender().getEmailId()
				.equals(((UserPrincipal) authentication.getPrincipal()).getEmail())) {
			ChatMessageBean chatMessageBean = new ChatMessageBean();

			chatMessageBean.setRoom(chatJSONRequest.getRoom());
			chatMessageBean.setMessage(chatJSONRequest.getMessage().getMessage());
			chatMessageBean.setIsEdited(false);
			return chatDAO.saveChat(chatMessageBean);
		} else {
			logger.info("Invalid user");
			baseResponse.setMessage("Invalid user");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	public ChatMessageResponse<?> updateChat(@RequestBody ChatJSONRequest chatJSONRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (chatJSONRequest.getMessage().getSender().getEmailId()
				.equals(((UserPrincipal) authentication.getPrincipal()).getEmail())) {
			ChatMessageBean chatMessageBean = new ChatMessageBean();

			chatMessageBean.setId(chatJSONRequest.getMessage().getId());
			chatMessageBean.setRoom(chatJSONRequest.getRoom());
			chatMessageBean.setMessage(chatJSONRequest.getMessage().getMessage());
			chatMessageBean.setIsEdited(true);
			return chatDAO.updateChat(chatMessageBean);
		} else {
			logger.info("Invalid user");
			baseResponse.setMessage("Invalid user");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	public ChatMessageResponse<?> getMessage(@RequestParam String room) {
		List<ChatMessageBean> chatMessageList = chatDAO.getMessage(room);
		List<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
		if (chatMessageList != null && chatMessageList.size() > 0) {
			for (ChatMessageBean message : chatMessageList) {
				messageResponseList.add(convertToMessageJSONResponse(message));

			}
			return new ChatMessageResponse<List<MessageResponse>>(messageResponseList, null, HttpStatus.OK);

		} else {
			logger.info("No Messages found");
			baseResponse.setMessage("No Messages found");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public MessageResponse getLastMessageForRoom(@RequestParam String room) {
		ChatMessageBean chatMessage = chatDAO.getLastMessageForRoom(room);

		if (null != chatMessage) {
			return convertToMessageJSONResponse(chatMessage);

		} else {
			logger.info("No Last Messages found");
			return null;
		}
	}

	public MessageResponse convertToMessageJSONResponse(ChatMessageBean messageBean) {

		SenderBean senderBean = new SenderBean();
		senderBean.setName(messageBean.getCreatedBy());
		Optional<String> email = userProfileDAO.findEmailByUserName(messageBean.getCreatedBy());
		if (email.isPresent()) {
			senderBean.setEmailId(email.get());
		}
		return new MessageResponse(messageBean.getId(), messageBean.getMessage(), messageBean.getIsEdited(),
				messageBean.getCreatedDate(), senderBean);

	}
}
