package rocha.andre.api.domain.studios.useCase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.StudioRepository;

@Component
public class GetAllStudios {
    @Autowired
    private StudioRepository repository;

    public Page<StudioReturnDTO> getAllStudios(Pageable pageable) {
        var studios = repository.findAllStudiosOrderedByName(pageable).map(StudioReturnDTO::new);
        return studios;
    }
}
