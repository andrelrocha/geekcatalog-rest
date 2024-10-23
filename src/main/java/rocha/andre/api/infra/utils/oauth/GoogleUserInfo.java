package rocha.andre.api.infra.utils.OAuth;

public record GoogleUserInfo(
        String sub,       // ID único do usuário Google
        String email,     // Email do usuário
        String name,      // Nome do usuário
        String given_name, // Nome próprio
        String family_name, // Sobrenome
        String picture,   // URL da foto de perfil
        String locale     // Localização (idioma)
) {}
