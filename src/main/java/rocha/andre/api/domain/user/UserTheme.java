package rocha.andre.api.domain.user;

public enum UserTheme {
    DARK("dark"),
    LIGHT("light");

    private String theme;

    UserTheme(String theme) {
        this.theme = theme;
    }
}
