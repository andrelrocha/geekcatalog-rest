package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.fullGame_mobile.DTO.FullGameUserDTO;
import rocha.andre.api.domain.fullGame_mobile.useCase.GetFullGameAdminInfoService;
import rocha.andre.api.domain.fullGame_mobile.useCase.GetFullGameInfoService;
import rocha.andre.api.domain.gameGenre.DTO.UpdateGameGenreDTO;
import rocha.andre.api.domain.gameGenre.useCase.UpdateGameGenres;

import java.util.ArrayList;

@RestController
@RequestMapping("/fullgame")
public class FullGameMobileController {
    @Autowired
    private GetFullGameAdminInfoService getFullGameAdminInfoService;
    @Autowired
    private GetFullGameInfoService getFullGameInfoService;

    @GetMapping("/admin/info/{gameId}")
    public ResponseEntity getFullGameInfoByGameId(@PathVariable String gameId) {
        var gameInfo = getFullGameAdminInfoService.getFullGameInfoAdmin(gameId);
        return ResponseEntity.ok(gameInfo);
    }

    @GetMapping("/user/info/{gameId}")
    public ResponseEntity<FullGameUserDTO> getFullGameInfoForRegularUser(@PathVariable String gameId) {
        var fullGameInfo = getFullGameInfoService.getFullGameInfo(gameId);
        return ResponseEntity.ok(fullGameInfo);
    }


    @Autowired
    private UpdateGameGenres updateGameGenres;

    @GetMapping("/teste")
    public ResponseEntity teste() {
        //var gameId = "1246d567-4a0d-445a-a1f7-026eaedf760c";
        var gameId = "e9189719-62ac-44cd-8e69-07666e1316fc";
        ArrayList<String> listaDeGeneros = new ArrayList<>();
        //var uuid1 = UUID.fromString("5c2d015e-1ac1-4342-b239-679303433fc1");
        //var uuid2 = UUID.fromString("5d761275-54cb-42bd-9787-7283023fbe03");
        var uuid3 = "1dc267d2-970e-44d6-988a-cd40545171c7";
        var uuid1 = "e1413d4b-5eb1-4de9-a814-ef9c4cc05b43";
        var uuid2 ="4e0bd7e0-aa04-4088-b2ee-ffec59cf53c6";
        listaDeGeneros.add(uuid1);
        listaDeGeneros.add(uuid2);
        listaDeGeneros.add(uuid3);

        var dto = new UpdateGameGenreDTO(listaDeGeneros);

        updateGameGenres.updateGameGenres(dto, gameId);
        return ResponseEntity.ok("ola");
    }
}
