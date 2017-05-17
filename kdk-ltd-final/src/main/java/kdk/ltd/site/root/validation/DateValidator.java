package kdk.ltd.site.root.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class DateValidator implements ConstraintValidator<Date, LocalDate> {
    @Override
    public void initialize(Date date) {

    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate current = LocalDate.now().withDayOfMonth(1);
        return current.isBefore(localDate) || current.isEqual(localDate);
    }
}
