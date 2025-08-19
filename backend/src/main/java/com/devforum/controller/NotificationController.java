package com.devforum.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
	private final SimpMessagingTemplate messagingTemplate;

	public NotificationController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/notify")
	public void notifyAll(String message) {
		messagingTemplate.convertAndSend("/topic/notifications", message);
	}
} 