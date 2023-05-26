package com.example.crypto_currency_watcher.excepton;

public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException() {
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }

    public CurrencyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyNotFoundException(Throwable cause) {
        super(cause);
    }
}
