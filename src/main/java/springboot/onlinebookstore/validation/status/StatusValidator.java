package springboot.onlinebookstore.validation.status;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StatusValidator implements ConstraintValidator<Status, String> {
    private Set<Enum<?>> enumValues;

    @Override
    public void initialize(Status constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        this.enumValues = new HashSet<>(Arrays.asList(enumClass.getEnumConstants()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
