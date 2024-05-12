package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.fullGame_mobile.DTO.FullGameReturnDTO;
import rocha.andre.api.domain.fullGame_mobile.useCase.GetFullGameInfoService;

@RestController
@RequestMapping("/fullgame")
public class FullGameMobileController {
    @Autowired
    private GetFullGameInfoService getFullGameInfoService;

    @GetMapping("/info/{gameId}")
    public ResponseEntity<FullGameReturnDTO> getFullGameInfoByGameId(@PathVariable String gameId) {
        var fullGameInfo = getFullGameInfoService.getFullGameInfo(gameId);
        return ResponseEntity.ok(fullGameInfo);
    }
}