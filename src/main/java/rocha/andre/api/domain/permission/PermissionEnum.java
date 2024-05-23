package rocha.andre.api.domain.permission;

public enum PermissionEnum {
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    ADD_GAME("ADD_GAME"),
    DELETE_GAME("DELETE_GAME");


    private final String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
    @Override
    public String toString() {
        return permission;
    }
}
