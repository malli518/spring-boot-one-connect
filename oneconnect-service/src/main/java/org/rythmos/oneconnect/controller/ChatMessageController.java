package org.rythmos.oneconnect.controller;

import org.rythmos.oneconnect.json.request.ChatJSONRequest;
import org.rythmos.oneconnect.json.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

	@Autowired
	private ChatController chatController;

	@MessageMapping("/chat/{roomId}")
	@SendTo("/topic/responsechannel/{roomId}")
	public MessageResponse messageRequestChannel(ChatJSONRequest message) throws Exception {
		chatController.saveChat(message);
		return chatController.getLastMessageForRoom(message.getRoom());
	}

	@MessageMapping("/chat/update/{roomId}")
	@SendTo("/topic/responsechannel/{roomId}")
	public MessageResponse messageUpdateRequestChannel(ChatJSONRequest message) throws Exception {
		chatController.updateChat(message);
		return chatController.getLastMessageForRoom(message.getRoom());
	}
}