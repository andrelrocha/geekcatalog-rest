package rocha.andre.api.domain.studios.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.CountryRepository;
import rocha.andre.api.domain.studios.DTO.CreateStudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.Studio;
import rocha.andre.api.domain.studios.StudioRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class CreateStudio {
    @Autowired
    private StudioRepository repository;

    @Autowired
    private CountryRepository countryRepository;

    public StudioReturnDTO createStudio(StudioDTO data) {
        var studioExists = repository.existsByNameAndCountry(data.name(), data.countryId());

        if (studioExists) {
            throw new ValidationException("Já existe um studio com o nome e país informados");
        }

        var country = countryRepository.findById(data.countryId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado país com o id informado na criação de studio"));

        var createDTO = new CreateStudioDTO(data.name(), country);

        var studio = new Studio(createDTO);

        var studioOnDB = repository.save(studio);

        return new StudioReturnDTO(studioOnDB);
    }
}
