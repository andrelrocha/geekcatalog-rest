package rocha.andre.api.domain.utils.sheet;

import java.util.UUID;

public record GamesOnUserListInfoDTO(
        String name,

        int yearOfRelease,

        String genres,

        String studios,

        String consolePlayed,

        int rating,
        UUID gameListId,
        UUID gameId,
        String note
) {}