package rocha.andre.api.domain.consoles.DTO;

import rocha.andre.api.domain.consoles.Console;

import java.util.UUID;

public record ConsoleReturnDTO(UUID id, String name) {
    public ConsoleReturnDTO(Console console) {
        this(console.getId(), console.getName());
    }
}
