package com.niliusjulius.lolinfo;

import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.URLEndpoint;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.lol.builders.spectator.SpectatorBuilder;
import no.stelar7.api.r4j.impl.lol.builders.summoner.SummonerBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class RateLimitTest
{

    @Test
    @Tag(Tags.MANUAL_TEST)
    public void testRateLimit()
    {
        String summonerName = new SpectatorBuilder().withPlatform(LeagueShard.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        for (int i2 = 0; i2 < 41; i2++)
        {
            new SummonerBuilder().withPlatform(LeagueShard.EUW1).withName(summonerName).get();
            DataCall.getCacheProvider().clear(URLEndpoint.V4_SUMMONER_BY_NAME, Collections.emptyMap());
        }
    }
}