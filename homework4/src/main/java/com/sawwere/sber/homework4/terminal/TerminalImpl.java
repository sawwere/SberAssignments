package com.sawwere.sber.homework4.terminal;

import com.sawwere.sber.homework4.exception.AccountIsLockedException;
import com.sawwere.sber.homework4.exception.IllegalAmountException;
import com.sawwere.sber.homework4.exception.InvalidCredentialsException;

import java.util.Scanner;

public class TerminalImpl implements Terminal {
    private final TerminalServer terminalServer;
    private final TerminalDisplay terminalDisplay;
    private final PinValidator pinValidator;


    public TerminalImpl(TerminalServer terminalServer,
                        TerminalDisplay terminalDisplay,
                        PinValidator pinValidator) {
        this.terminalServer = terminalServer;
        this.terminalDisplay = terminalDisplay;
        this.pinValidator = pinValidator;
    }


    /**
     * Возвращает количество средств на счету
     */
    @Override
    public void check() {
        try {
            terminalDisplay.display("Текущий баланс на счету: %d.".formatted(terminalServer.check()));
        } catch (Exception e) {
            terminalDisplay.display(e.getMessage());
        }
    }

    /**
     * Пополняет счет на заданное количество средств
     */
    @Override
    public void deposit() {
        int amount = 500;
        try {
            terminalServer.deposit(amount);
            terminalDisplay.display("Операция выполнена успешно. Счет пополнен на %d.".formatted(amount));
        } catch (IllegalAmountException e) {
            terminalDisplay.display("Операция не выполнена, произведена попытка пополнить счет на некорректную сумму."
                    + e.getMessage()
            );
        }
    }

    /**
     * Снимает со счета заданное количество средств
     */
    @Override
    public void withdraw() {
        int amount = 500;
        try {
            terminalServer.withdraw(amount);
            terminalDisplay.display("Операция выполнена успешно. Со счета снято %d.".formatted(amount));
        } catch (IllegalAmountException e) {
            terminalDisplay.display("Операция не выполнена, произведена попытка вывести со счета некорректное денег."
                    + e.getMessage()
            );
        }
    }

    /**
     * Выполняет аутентификацию пользователя. Здесь происходит считывание и обработка пин-кода.
     */
    private void login() {
        String input = "";
        terminalDisplay.display("Ввод пин-кода");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            terminalDisplay.display("Введите следующий символ:");
            char c = scanner.next().toLowerCase().charAt(0);
            try {
                if (pinValidator.isValidSymbol(c)) {
                    input += c;
                } else {
                    terminalDisplay.display("Введен некорректный символ. Пин-код состоит из 4 цифр.");
                }
                if (pinValidator.validate(input)) {
                    terminalServer.login(input);
                    terminalDisplay.display("Вход выполнен успешно.");
                    return;
                }
            } catch (AccountIsLockedException e) {
                input = "";
                terminalDisplay.display(e.getMessage()
                        + "Повторите попытку входа через %s секунд.%n".formatted(e.getTimeout())
                );
            } catch (InvalidCredentialsException e) {
                input = "";
                terminalDisplay.display("Попытка входа отклонена. " +  e.getMessage());
            }
        }
    }

    /**
     * Инициирует запуск терминала
     */
    public void run() {
        login();

        Scanner scanner = new Scanner(System.in);
        String message = """
                Введите код операции
                1 - проверить счет
                2 - внести деньги
                3 - снять деньги
                0 - выход
                """;
        int code = -1;
        while (code != 4) {
            terminalDisplay.display(message);
            code = scanner.nextInt();
            switch (code) {
                case 0: {
                    terminalDisplay.display("Сеанс обслуживания завершен. Спасибо за пользование нашим терминалом.");
                    return;
                }
                case 1: {
                    check();
                    break;
                }
                case 2: {
                    deposit();
                    break;
                }
                case 3: {
                    withdraw();
                    break;
                }
                default: {
                    terminalDisplay.display("Введен некорректный код команды.");
                    break;
                }
            }
        }
    }
}
