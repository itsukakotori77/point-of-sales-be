package com.posapps.utility;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ErrorParsingUtility {
    public static Map<String, String> parse(Errors errors)
    {
        Map<String, String> data = new HashMap<>();
        errors.getAllErrors().forEach((error) -> {
            data.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return data;
    }
}
