package br.com.fiap.lanchonete.validations;

import java.util.List;

public class ValidationErrorResponse {
    private List<String> errors;

    public ValidationErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
