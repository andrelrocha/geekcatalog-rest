package rocha.andre.api.domain.auditLog;

public enum LoginStatus {
    SUCCESS("success"),
    FAILURE("failure");

    private String status;

    LoginStatus(String status) {
        this.status = status;
    }
}
