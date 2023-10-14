package com.github.rusichpt.Messenger.controllers;

import com.github.rusichpt.Messenger.dto.MessageRequest;
import com.github.rusichpt.Messenger.dto.StoryRequest;
import com.github.rusichpt.Messenger.dto.StoryResponse;
import com.github.rusichpt.Messenger.models.Message;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.MessageService;
import com.github.rusichpt.Messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/message")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Message API")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @Operation(summary = "Send a message to the user")
    @PostMapping(path = "send")
    public void sendMessage(@AuthenticationPrincipal User userFrom, @RequestBody MessageRequest request) {
        User userTo = userService.findUserByUsername(request.getUsernameTo());
        messageService.sendMessage(new Message(userFrom.getId(), userTo.getId(), request.getContent()));
    }

    @Operation(summary = "Get story from the user")
    @PostMapping(path = "story")
    public List<StoryResponse> getStory(@AuthenticationPrincipal User userTo, @RequestBody StoryRequest request) {
        User userFrom = userService.findUserByUsername(request.getUsernameFrom());
        List<Message> messages = messageService.getMessages(userFrom.getId(), userTo.getId(), request.getPageNumber(), request.getPageSize());
        return messages.stream()
                .map(m -> new StoryResponse(userFrom.getUsername(), userTo.getUsername(), m.getContent(), m.getDate()))
                .toList();
    }
}
