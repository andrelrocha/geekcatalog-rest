package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.GameGenre;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.genres.DTO.GenreCountDTO;
import rocha.andre.api.domain.user.UseCase.GetUserIdByJWT;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GetAllGameListGenresByUser {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private GetUserIdByJWT getUserIdByJWT;
    @Autowired
    private GameGenreRepository gameGenreRepository;

    public Page<GenreCountDTO> getAllGameListGenresByUserId(String tokenJWT, Pageable pageable) {
        var user = getUserIdByJWT.getUserByJWT(tokenJWT);

        var gameListsByUser = gameListRepository.findAllByUserId(UUID.fromString(user.userId()));

        var pageableGenres = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());

        var gameGenres = gameListsByUser.stream()
                .flatMap(gameList -> gameGenreRepository.findAllGameGenresByGameId(gameList.getGame().getId(), pageableGenres).stream())
                .toList();

        Map<UUID, GenreCountDTO> genreCountMap = new HashMap<>();
        for (GameGenre gameGenre : gameGenres) {
            var genreId = gameGenre.getGenre().getId();
            var genreName = gameGenre.getGenre().getName();
            genreCountMap.put(genreId, new GenreCountDTO(genreId, genreName, genreCountMap.getOrDefault(genreId, new GenreCountDTO(genreId, genreName, 0)).count() + 1));
        }

        List<GenreCountDTO> genreCountList = new ArrayList<>(genreCountMap.values());

        PagedListHolder<GenreCountDTO> pagedListHolder = new PagedListHolder<>(genreCountList);
        pagedListHolder.setPageSize(pageable.getPageSize());

        MutableSortDefinition sortDefinition = new MutableSortDefinition();
        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> {
                sortDefinition.setProperty(order.getProperty());
                sortDefinition.setAscending(order.isAscending());
                sortDefinition.isToggleAscendingOnProperty();
            });
        } else {
            sortDefinition.setProperty("count");
            sortDefinition.setAscending(true);
        }

        pagedListHolder.setSort(sortDefinition);
        pagedListHolder.resort();

        int page = pageable.getPageNumber();
        pagedListHolder.setPage(page);

        int startIndex = pagedListHolder.getPage() * pagedListHolder.getPageSize();
        int endIndex = Math.min(startIndex + pagedListHolder.getPageSize(), genreCountList.size());

        List<GenreCountDTO> currentPageList = genreCountList.subList(startIndex, endIndex);

        return new PageImpl<>(currentPageList, pageable, genreCountList.size());
    }
}