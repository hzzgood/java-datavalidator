package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    private boolean isValid;
    private String errorMsg;
}
