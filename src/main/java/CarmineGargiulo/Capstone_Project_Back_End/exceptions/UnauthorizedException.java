package CarmineGargiulo.Capstone_Project_Back_End.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
