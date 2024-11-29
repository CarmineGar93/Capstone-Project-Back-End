package CarmineGargiulo.Capstone_Project_Back_End.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
