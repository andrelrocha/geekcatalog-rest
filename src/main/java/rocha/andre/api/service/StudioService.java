package rocha.andre.api.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;

import java.util.List;

public interface StudioService {
    Page<StudioReturnDTO> getAllStudios(Pageable pageable);
    StudioReturnDTO createStudio(StudioDTO data);
    List<StudioReturnDTO> getStudiosByName(List<StudioDTO> data);
}
