package com.niliusjulius.lolinfo.riot.lol.component;

import com.niliusjulius.lolinfo.riot.lol.util.CacheLifetime;
import com.niliusjulius.lolinfo.riot.lol.util.CustomFileSystemCacheProvider;
import lombok.Getter;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RiotApiConfig {
    private final R4J r4J;

    public RiotApiConfig(@Value("${riot.api-key}") String LEAGUE_KEY) {
        r4J = new R4J(new APICredentials(LEAGUE_KEY));

        DataCall.setCacheProvider(new CustomFileSystemCacheProvider());
        CacheLifetime.setCacheLifetime();
    }
}
