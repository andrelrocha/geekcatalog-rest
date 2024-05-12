package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.useCase.CreateStudio;
import rocha.andre.api.domain.studios.useCase.GetAllStudios;
import rocha.andre.api.service.StudioService;

@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    private GetAllStudios getAllStudios;
    @Autowired
    private CreateStudio createStudio;

    @Override
    public Page<StudioReturnDTO> getAllStudios(Pageable pageable) {
        var studios = getAllStudios.getAllStudios(pageable);
        return studios;
    }

    @Override
    public StudioReturnDTO createStudio(StudioDTO data) {
        var newStudio = createStudio.createStudio(data);
        return newStudio;
    }
}
