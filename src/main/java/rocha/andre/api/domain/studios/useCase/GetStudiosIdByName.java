package rocha.andre.api.domain.studios.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.StudioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetStudiosIdByName {

    @Autowired
    private StudioRepository studioRepository;

    public List<StudioReturnDTO> getStudiosByName(List<StudioDTO> data) {
        List<String> names = data.stream()
                .map(StudioDTO::name)
                .map(name -> name.replaceAll("[\\(\\)\\[\\]]", ""))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        var studios = studioRepository.findAllByNamesIgnoreCaseAndTrimmed(names);

        return studios.stream()
                .map(StudioReturnDTO::new)
                .collect(Collectors.toList());
    }
}