package rocha.andre.api.domain.finished.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.finished.FinishedRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class DeleteFinished {
    @Autowired
    private FinishedRepository repository;
    
    public void deleteFinished(long id) {
        var opinionExists = repository.existsById(id);

        if (!opinionExists) {
            throw new ValidationException("Não foi encontrada opinião com o id informado.");
        }

        repository.deleteById(id);
    }

}
