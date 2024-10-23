package rocha.andre.api.infra.utils.sheets.xlsx;

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