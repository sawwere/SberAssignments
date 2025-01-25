package com.sawwere.sber.homework15.exception;

import org.springframework.http.HttpStatusCode;

/**
 * Исключение, генерируемое в случае возврата API сервисом ошибки с кодом 5xx в ответ на выполненный запрос
 */
public class ApiServerSideException extends RuntimeException {
    /**
     * Код полученной ошибки
     */
    protected HttpStatusCode httpStatusCode;

    public ApiServerSideException(HttpStatusCode statusCode) {
        super(statusCode.toString());
        this.httpStatusCode = statusCode;
    }
}
