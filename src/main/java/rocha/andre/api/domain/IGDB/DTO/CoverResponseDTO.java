package rocha.andre.api.domain.IGDB.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CoverResponseDTO(
        @JsonProperty("game") int game,
        @JsonProperty("height") int height,
        @JsonProperty("width") int width,
        @JsonProperty("url") String url
) {}
