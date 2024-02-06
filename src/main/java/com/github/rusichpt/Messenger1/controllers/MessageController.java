package com.github.rusichpt.Messenger1.controllers;

import com.github.rusichpt.Messenger1.dto.MessageRequest;
import com.github.rusichpt.Messenger1.dto.StoryRequest;
import com.github.rusichpt.Messenger1.dto.StoryResponse;
import com.github.rusichpt.Messenger1.entities.Message;
import com.github.rusichpt.Messenger1.entities.User;
import com.github.rusichpt.Messenger1.services.MessageService;
import com.github.rusichpt.Messenger1.services.UserService;
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
    public void sendMessage(@AuthenticationPrincipal User userFrom, @Valid @RequestBody MessageRequest request) {
        User userTo = userService.findUserByUsername(request.getUsernameTo());
        messageService.sendMessage(userFrom, userTo, request.getContent());
    }

    @Operation(summary = "Get story from the user")
    @PostMapping(path = "story")
    public List<StoryResponse> getStory(@AuthenticationPrincipal User userTo, @Valid @RequestBody StoryRequest request) {
        User userFrom = userService.findUserByUsername(request.getUsernameFrom());
        List<Message> messages = messageService.getMessages(userFrom, userTo, request.getPageNumber(), request.getPageSize());

        return messages.stream()
                .map(m -> new StoryResponse(userFrom.getUsername(), userTo.getUsername(), m.getContent(), m.getDate()))
                .toList();
    }
}
