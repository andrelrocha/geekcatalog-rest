package rocha.andre.api.domain.utils.API.IGDB.DTO;

public record CountryInfo(Name name, String cca2) {
    public record Name(String common) {}
}
