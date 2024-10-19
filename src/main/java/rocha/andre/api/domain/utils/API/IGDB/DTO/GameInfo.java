package rocha.andre.api.domain.utils.API.IGDB.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GameInfo(String name, List<Integer> genres, @JsonProperty("involved_companies") List<Integer> involvedCompanies) {

}