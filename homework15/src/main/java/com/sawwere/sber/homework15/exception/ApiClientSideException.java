package com.sawwere.sber.homework15.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * Исключение, генерируемое в случае возврата API сервисом ошибки с кодом 4xx в ответ на выполненный запрос
 */
@Getter
public class ApiClientSideException extends RuntimeException{
    /**
     * Код полученной ошибки
     */
    protected HttpStatusCode httpStatusCode;

    public ApiClientSideException(HttpStatusCode statusCode) {
        super(statusCode.toString());
        this.httpStatusCode = statusCode;
    }
}
