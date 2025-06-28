package pe.edu.vallegrande.movilsurbackend.infrastructure.exception;

import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.ErrorMessage;

public class CustomException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public CustomException(int errorCode, String message, String details) {
        super(message);
        this.errorMessage = new ErrorMessage(errorCode, message, details);
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
