package com.niliusjulius.lolinfo.riot.lol.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

public class LeagueShardValidator implements ConstraintValidator<LeagueShardConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return LeagueShard.fromString(value).isPresent();
    }
}
