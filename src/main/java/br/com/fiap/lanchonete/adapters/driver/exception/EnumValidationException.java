package br.com.fiap.lanchonete.adapters.driver.exception;

public class EnumValidationException extends RuntimeException{
    public EnumValidationException(String fieldName, String invalidValue) {
        super("Invalid value for field '" + fieldName + "': " + invalidValue);
    }
}
