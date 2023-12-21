package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.dto.MessageRequest;
import com.github.rusichpt.Messenger.dto.StoryRequest;
import com.github.rusichpt.Messenger.dto.StoryResponse;
import com.github.rusichpt.Messenger.entities.Chat;
import com.github.rusichpt.Messenger.entities.Message;
import com.github.rusichpt.Messenger.entities.User;
import com.github.rusichpt.Messenger.services.ChatService;
import com.github.rusichpt.Messenger.services.MessageService;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/message")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Message API")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;

    @Operation(summary = "Send a message to the user")
    @PostMapping(path = "send")
    public void sendMessage(@AuthenticationPrincipal User userFrom, @Valid @RequestBody MessageRequest request) {
        User userTo = userService.findUserByUsername(request.getUsernameTo());
        Optional<Chat> optChat;
        if (userFrom.getUsername().equals(userTo.getUsername())) {
            optChat = chatService.findSelfChat(userFrom);
        } else {
            optChat = chatService.findChatByUsers(userFrom, userTo);
        }
        Chat chat;
        if (optChat.isEmpty()) {
            chat = chatService.createChat(new Chat(userFrom, userTo));
        } else {
            chat = optChat.get();
        }
        messageService.sendMessage(new Message(userFrom, chat, request.getContent()));
    }

    @Operation(summary = "Get story from the user")
    @PostMapping(path = "story")
    public List<StoryResponse> getStory(@AuthenticationPrincipal User userTo, @Valid @RequestBody StoryRequest request) {
        User userFrom = userService.findUserByUsername(request.getUsernameFrom());
        Optional<Chat> optChat = chatService.findChatByUsers(userFrom, userTo);
        if (optChat.isPresent()) {
            Chat chat = optChat.get();
            List<Message> messages = messageService.getMessages(chat, request.getPageNumber(), request.getPageSize());
            return messages.stream()
                    .map(m -> new StoryResponse(userFrom.getUsername(), userTo.getUsername(), m.getContent(), m.getDate()))
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }
}
