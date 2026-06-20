package com.gamebuilder.gamedef;

import java.util.List;

public record ValidationResult(boolean valid, List<String> errors) {
    public static ValidationResult ok() {
        return new ValidationResult(true, List.of());
    }

    public static ValidationResult fail(List<String> errors) {
        return new ValidationResult(false, errors);
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrors() {
        return errors;
    }
}