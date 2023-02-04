package com.niliusjulius.lolinfo.riot.lol.component;

import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Credentials {

    public Credentials(@Value("${riot.api-key}") String LEAGUE_KEY) {
        new R4J(new APICredentials(LEAGUE_KEY));
    }
}
