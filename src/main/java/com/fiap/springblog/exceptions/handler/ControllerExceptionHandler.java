package com.fiap.springblog.exceptions.handler;

import java.time.Instant;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fiap.springblog.errors.StandardErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private StandardErrorResponse error = new StandardErrorResponse();

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<StandardErrorResponse> handleOptimisticLockingFailureException(
                OptimisticLockingFailureException exception, HttpServletRequest request) {

                    HttpStatus status = HttpStatus.CONFLICT;
                    error.setTimestamp(Instant.now());
                    error.setStatus(status.value());
                    error.setError(String.format("Erro de Concorrência: o Artigo foi atualizado por outro usuário. Por favor, tente novamente"));
                    error.setMessage(exception.getMessage());
                    error.setPath(request.getRequestURI());

                    return ResponseEntity.status(status).body(error);
                }
}
