package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.IGDB.useCase.GetCoverByGameId;
import rocha.andre.api.domain.IGDB.useCase.GetGameIDByGameName;

@RestController
@RequestMapping("/igdb")
public class IGDBApiController {
    @Autowired
    private GetCoverByGameId getCoverByGameId;
    @Autowired
    private GetGameIDByGameName getGameIDByGameName;

    @GetMapping("/getid/{gameName}")
    public ResponseEntity getGameIdByGameName(@PathVariable String gameName) {
        var response = getGameIDByGameName.getGameId(gameName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getcover/{gameId}")
    public ResponseEntity getCoverByGameId(@PathVariable String gameId) {
        var response = getCoverByGameId.getCover(gameId);
        return ResponseEntity.ok(response);
    }

    //FAZER SISTEMA DE VALIDAÇÃO DE CONFIRMAR O NOME DO JOGO
    //UMA FUNÇÃO CHAMA A OUTRA, DEVE TER UM SERVICE QUE RECEBA NO BODY DE UM POST O NOME DO JOGO
}
