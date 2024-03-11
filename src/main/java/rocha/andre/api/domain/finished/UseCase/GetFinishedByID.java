package rocha.andre.api.domain.finished.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.finished.DTO.FinishedReturnDTO;
import rocha.andre.api.domain.finished.FinishedRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class GetFinishedByID {
    @Autowired
    private FinishedRepository repository;

    public FinishedReturnDTO getFinishedById(long id) {
        var opinion = repository.findById(id)
                .orElseThrow(() -> new ValidationException("Não foi encontrada opinião com o id informado."));

        return new FinishedReturnDTO(opinion);
    }
}
