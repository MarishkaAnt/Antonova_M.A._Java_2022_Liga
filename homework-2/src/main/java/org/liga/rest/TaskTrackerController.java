package org.liga.rest;

import lombok.RequiredArgsConstructor;
import org.liga.service.impl.CommandHandlerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.liga.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/", produces = "text/plain")
public class TaskTrackerController {

    private final CommandHandlerService commandHandlerService;
    private static String response;

    static {
        response = GREETINGS + HELP_TEXT;
    }

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cli")
    public ResponseEntity<String> execute(@RequestParam Optional<String> command) {
        String handleCommandResponse = commandHandlerService.handleCommand(command.orElse(""));
        response = handleCommandResponse + System.lineSeparator() + MESSAGE_AFTER_COMMAND_EXECUTING;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
