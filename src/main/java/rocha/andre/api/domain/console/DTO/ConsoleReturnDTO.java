package rocha.andre.api.domain.console.DTO;

import rocha.andre.api.domain.console.Console;

import java.util.UUID;

public record ConsoleReturnDTO(UUID id, String name) {
    public ConsoleReturnDTO(Console console) {
        this(console.getId(), console.getName());
    }
}
