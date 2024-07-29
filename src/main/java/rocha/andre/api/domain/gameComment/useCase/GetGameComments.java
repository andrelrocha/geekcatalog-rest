package rocha.andre.api.domain.gameComment.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameComment.DTO.GameCommentReturnDTO;
import rocha.andre.api.domain.gameComment.GameCommentRepository;

@Component
public class GetGameComments {
    @Autowired
    public GameCommentRepository gameCommentRepository;

    public Page<GameCommentReturnDTO> getCommentsPageable(Pageable pageable, String gameId) {
        //TEM QUE MAPEAR CASO DE VOLTAR VAZIO, RETORNA page VAZIA NULL
        //TEM QUE SER MAPEADO E ORDENADO POR UPDATED_AT
        return null;
    }
}
