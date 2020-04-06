package org.rythmos.oneconnect.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.rythmos.oneconnect.bean.ChatMessageBean;
import org.rythmos.oneconnect.entity.ChatMessage;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.ChatMessageRepository;
import org.rythmos.oneconnect.response.ChatMessageResponse;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ChatDAO {
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	public static Logger logger = LoggerFactory.getLogger(ChatDAO.class);

	public ChatMessageResponse<?> saveChat(@RequestBody ChatMessageBean chatJSONBean) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setRoom(chatJSONBean.getRoom());
		chatMessage.setMessage(chatJSONBean.getMessage());
		chatMessage.setIsEdited(chatJSONBean.getIsEdited());
		if (chatMessageRepository.save(chatMessage) != null) {
			logger.info("Message added successfully");
			baseResponse.setMessage("Message added successfully");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Unable to add message");
			baseResponse.setMessage("Unable to add message");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ChatMessageResponse<?> updateChat(@RequestBody ChatMessageBean chatJSONBean) {
		ChatMessage chatMessage = chatMessageRepository.findChatMessageByRoomAndId(chatJSONBean.getRoom(),
				chatJSONBean.getId());
		chatMessage.setMessage(chatJSONBean.getMessage());
		chatMessage.setIsEdited(chatJSONBean.getIsEdited());
		if (chatMessageRepository.save(chatMessage) != null) {
			logger.info("Message updated successfully");
			baseResponse.setMessage("Message updated successfully");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Unable to update message");
			baseResponse.setMessage("Unable to update message");
			return new ChatMessageResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public List<ChatMessageBean> getMessage(@RequestBody String room) {
		List<ChatMessage> messagesList = chatMessageRepository.findChatMessageByRoom(room);
		List<ChatMessageBean> chatMessageList = messagesList.stream().map(message -> convertToBean(message))
				.collect(Collectors.toList());
		return chatMessageList;

	}

	public ChatMessageBean getLastMessageForRoom(@RequestBody String room) {
		ChatMessage message = chatMessageRepository.findLastChatMessageByRoom(room);
		return convertToBean(message);

	}

	private ChatMessageBean convertToBean(ChatMessage chatMessage) {
		ChatMessageBean chatMessageBean = oneConnectDAOUtility.modelMapper.map(chatMessage, ChatMessageBean.class);
		return chatMessageBean;
	}

}
