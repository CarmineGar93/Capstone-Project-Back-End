package CarmineGargiulo.Capstone_Project_Back_End.exceptions;

import CarmineGargiulo.Capstone_Project_Back_End.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ErrorResponseDTO handleBadRequest(BadRequestException e) {
        return new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    public ErrorResponseDTO handleUnauthorized(UnauthorizedException e) {
        return new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ErrorResponseDTO handleForbidden(AuthorizationDeniedException e) {
        return new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ErrorResponseDTO handleNotFound(NotFoundException e) {
        return new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMissingParameter(MissingServletRequestParameterException e) {
        return new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ErrorResponseDTO handleGenericException(Exception e) {
        e.printStackTrace();
        return new ErrorResponseDTO("Internal server error", LocalDateTime.now());
    }

}
