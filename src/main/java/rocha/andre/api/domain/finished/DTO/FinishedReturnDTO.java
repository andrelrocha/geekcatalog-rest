package rocha.andre.api.domain.finished.DTO;

import rocha.andre.api.domain.finished.Finished;

public record FinishedReturnDTO(
        long id,
        String name,
        String console,
        int note,
        String opinion,
        long gameId,
        int metacritic,
        String genre
) {

    public FinishedReturnDTO(Finished finished) {
        this(
                finished.getId(),
                finished.getName(),
                finished.getConsole(),
                finished.getNote(),
                finished.getOpinion(),
                finished.getGame().getId(),
                finished.getMetacritic(),
                finished.getGenre()
        );
    }
}
