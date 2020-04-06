package org.rythmos.oneconnect.controller;

import org.rythmos.oneconnect.json.request.ChatJSONRequest;
import org.rythmos.oneconnect.json.response.MessageResponse;
import org.rythmos.oneconnect.response.ChatMessageResponse;
import org.rythmos.oneconnect.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping("/saveChat")
	public ChatMessageResponse<?> saveChat(@RequestBody ChatJSONRequest chatJSONRequest) {
		return chatService.saveChat(chatJSONRequest);

	}

	@PutMapping("/updateChat")
	public ChatMessageResponse<?> updateChat(@RequestBody ChatJSONRequest chatJSONRequest) {
		return chatService.updateChat(chatJSONRequest);

	}

	@GetMapping("/getMessage")
	public ChatMessageResponse<?> getMessage(@RequestParam String room) {
		return chatService.getMessage(room);
	}

	public MessageResponse getLastMessageForRoom(String room) {
		return chatService.getLastMessageForRoom(room);
	}
}
