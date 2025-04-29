package org.moodle.springlaboratorywork.validation;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final List<Violation> violations;
}