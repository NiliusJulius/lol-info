package com.niliusjulius.lolinfo.riot.lol.service;

import com.niliusjulius.lolinfo.riot.lol.service.LolSummonerService;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.lol.builders.summoner.SummonerBuilder;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LolSummonerServiceTest {
    @Mock
    private SummonerBuilder summonerBuilder;

    @InjectMocks
    private LolSummonerService lolSummonerService;

    @Nested
    @DisplayName("RetrieveSummoner should")
    public class RetrieveSummonerTest {

        @Test
        @DisplayName("make a call to retrieve a summoner based on the given parameters")
        public void retrieveSummonerCorrectTest() {
            when(summonerBuilder.withPlatform(any(LeagueShard.class))).thenReturn(summonerBuilder);
            when(summonerBuilder.withName(anyString())).thenReturn(summonerBuilder);
            when(summonerBuilder.get()).thenReturn(null);

            LeagueShard leagueShard = LeagueShard.EUW1;
            String leagueShardName = LeagueShard.EUW1.name();
            String summonerName = "TestSummonerName";

            lolSummonerService.retrieveSummoner(leagueShardName, summonerName);

            verify(summonerBuilder, times(1)).withPlatform(leagueShard);
            verify(summonerBuilder, times(1)).withName(summonerName);
            verify(summonerBuilder, times(1)).get();
        }

        @Test
        @DisplayName("return null when a summoner can not be found")
        public void retrieveSummonerNotFoundTest() {
            when(summonerBuilder.withPlatform(any(LeagueShard.class))).thenReturn(summonerBuilder);
            when(summonerBuilder.withName(anyString())).thenReturn(summonerBuilder);
            when(summonerBuilder.get()).thenReturn(null);

            String leagueShardName = LeagueShard.EUW1.name();
            String summonerName = "TestSummonerName";

            Summoner summoner = lolSummonerService.retrieveSummoner(leagueShardName, summonerName);

            assertNull(summoner);
            verify(summonerBuilder, times(1)).get();
        }
    }
}
