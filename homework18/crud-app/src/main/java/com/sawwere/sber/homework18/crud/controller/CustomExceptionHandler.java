package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.exception.NotFoundException;
import com.sawwere.sber.homework18.crud.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Глобальный обработчик возникающих в процессе работы приложения исключений.
 * Служит для возвращения сообщений об ошибках в едином формате при их возникновении.
 */
@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * Базовый обработчик для ловли непойманных другими методами исключений
     * @param ex возникшее исключение
     * @param request текущий запрос
     * @return страница, содержащая информацию об ошибке
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ModelAndView handleAllExceptions(Exception ex, WebRequest request) {
        ModelAndView mav = new ModelAndView("error");
        ErrorInfo errorInfo = ErrorInfo.builder()
                .title("Не удалось обработать запрос")
                .description(ex.getMessage() + "; Стек исключения для отладки выводится в консоли")
                .build();
        mav.addObject("errorInfo", errorInfo);
        ex.printStackTrace();
        return mav;
    }

    /**
     * Обрабатывает исключения, связанные с отсутствующими записями в бд
     * @param ex возникшее исключение
     * @param request текущий запрос
     * @return страница, содержащая информацию об ошибке
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView handleNotFoundException(NotFoundException ex, WebRequest request) {
        ModelAndView mav = new ModelAndView("error");

        ErrorInfo errorInfo = ErrorInfo.builder()
                .title("Данные не найдены")
                .description(ex.getMessage())
                .build();
        mav.addObject("errorInfo", errorInfo);
        return mav;
    }

    /**
     * Обрабатывает исключения, связанные с передачей некорректных данных
     * @param ex возникшее исключение
     * @param request текущий запрос
     * @return страница, содержащая информацию об ошибке
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ModelAndView handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        ModelAndView mav = new ModelAndView("error");

        ErrorInfo errorInfo = ErrorInfo.builder()
                .title("Переданы некорректные данные")
                .description(String.join("\n",
                        ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(FieldError::getField)
                                .toList())
                )
                .build();
        mav.addObject("errorInfo", errorInfo);
        return mav;
    }
}
