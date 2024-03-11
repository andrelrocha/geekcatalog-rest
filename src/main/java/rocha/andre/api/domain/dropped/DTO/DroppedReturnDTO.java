package rocha.andre.api.domain.dropped.DTO;

import rocha.andre.api.domain.dropped.Dropped;

public record DroppedReturnDTO(long id, String name, String console, int note, String reason, long gameId) {
    public DroppedReturnDTO(Dropped dropped) {
        this(dropped.getId(), dropped.getName(), dropped.getConsole(), dropped.getNote(), dropped.getReason(), dropped.getGame().getId());
    }
}
