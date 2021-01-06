package com.beitech.store.exception.handler;

import com.beitech.store.constants.ErrorMessages;
import com.beitech.store.exception.InvalidOrderException;
import com.beitech.store.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author James Martinez
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class StoreExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {InvalidOrderException.class, NoContentException.class, DataIntegrityViolationException.class, ConstraintViolationException.class,  NullPointerException.class})
    public final ResponseEntity handleConflict(RuntimeException ex) {
        if (ex instanceof DataIntegrityViolationException || ex instanceof ConstraintViolationException) {
            log.error(ErrorMessages.SQL_TRANSACTION, ex);
            return new ResponseEntity(ErrorMessages.SQL_TRANSACTION,HttpStatus.CONFLICT);
        }
        if (ex instanceof InvalidOrderException) {
            log.info(ErrorMessages.SAVE_ERROR, ex);
            return new ResponseEntity(((InvalidOrderException)ex).getCustomError(),HttpStatus.CONFLICT);
        }
        if (ex instanceof NoContentException) {
            NoContentException _ex = (NoContentException) ex;
            log.error("El item not exist " + _ex.getId());
            return ResponseEntity.noContent().build();
        }
        log.error(ErrorMessages.NOT_CONTROLLED_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

}
