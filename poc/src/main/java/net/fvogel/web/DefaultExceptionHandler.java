package net.fvogel.web;

import javax.persistence.EntityNotFoundException;

import net.fvogel.exception.ConflictingEntitiesException;
import net.fvogel.exception.NoEntityExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handle(IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({ConflictingEntitiesException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handle(ConflictingEntitiesException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({NoEntityExistingException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handle(NoEntityExistingException e) {
        return new ErrorResponse(e.getMessage());
    }

}
