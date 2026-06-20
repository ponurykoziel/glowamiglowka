package com.gamebuilder.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class ValidationResultDto {
    private final boolean valid;
    private final List<String> errors;

    @JsonCreator
    public ValidationResultDto(
            @JsonProperty("valid") boolean valid,
            @JsonProperty("errors") List<String> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    public static ValidationResultDto valid() {
        return new ValidationResultDto(true, List.of());
    }

    public static ValidationResultDto invalid(List<String> errors) {
        return new ValidationResultDto(false, errors);
    }

    public boolean isValid() { return valid; }
    public List<String> getErrors() { return errors; }
}
