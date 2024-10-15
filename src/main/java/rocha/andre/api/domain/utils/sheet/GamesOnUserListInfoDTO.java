package rocha.andre.api.domain.utils.sheet;

import java.util.UUID;

public record GamesOnUserListInfoDTO(
        String name,

        int metacritic,

        int yearOfRelease,

        String genres,

        String studios,

        String consolePlayed,

        int rating,
        String note,
        UUID id
) {}