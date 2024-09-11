package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.domain.fullGame.DTO.FullGameUpdateDTO;
import rocha.andre.api.domain.fullGame.DTO.FullGameUserDTO;
import rocha.andre.api.domain.fullGame.useCase.GetFullGameAdminInfoService;
import rocha.andre.api.domain.fullGame.useCase.GetFullGameInfoService;
import rocha.andre.api.domain.fullGame.useCase.UpdateFullGameAdmin;

@RestController
@RequestMapping("/fullgame")
@Tag(name = "Game with Full Info Routes Mapped on Controller")
public class FullGameMobileController {
    @Autowired
    private GetFullGameAdminInfoService getFullGameAdminInfoService;
    @Autowired
    private GetFullGameInfoService getFullGameInfoService;
    @Autowired
    private UpdateFullGameAdmin updateFullGameAdmin;

    @GetMapping("/admin/info/{gameId}")
    public ResponseEntity<FullGameReturnDTO> getFullGameInfoByGameId(@PathVariable String gameId) {
        var gameInfo = getFullGameAdminInfoService.getFullGameInfoAdmin(gameId);
        return ResponseEntity.ok(gameInfo);
    }

    @GetMapping("/user/info/{gameId}")
    public ResponseEntity<FullGameUserDTO> getFullGameInfoForRegularUser(@PathVariable String gameId) {
        var fullGameInfo = getFullGameInfoService.getFullGameInfo(gameId);
        return ResponseEntity.ok(fullGameInfo);
    }

    @PutMapping("/admin/update/{gameId}")
    public ResponseEntity<FullGameReturnDTO> updateFullGame(@RequestBody FullGameUpdateDTO data, @PathVariable String gameId) {
        var fullGameUpdated = updateFullGameAdmin.updateFullGameInfos(data, gameId);
        return ResponseEntity.ok(fullGameUpdated);
    }
}
