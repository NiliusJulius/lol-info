package com.niliusjulius.lolinfo.riot.lol.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LeagueShardValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LeagueShardConstraint.List.class)
public @interface LeagueShardConstraint {
    String message() default "{shard.name.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target( { ElementType.PARAMETER, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        LeagueShardConstraint[] value();
    }
}
