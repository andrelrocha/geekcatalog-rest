package rocha.andre.api.domain.permission;

import lombok.Getter;

@Getter
public enum PermissionEnum {
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    INVITE("INVITE"),
    ADD_GAME("ADD_GAME"),
    DELETE_GAME("DELETE_GAME"),
    UPDATE_GAME("UPDATE_GAME");


    private final String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return permission;
    }
}
