package com.seller.panel.config;

import com.seller.panel.util.AppConstants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default AppConstants.MUSTNOTBEEMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
