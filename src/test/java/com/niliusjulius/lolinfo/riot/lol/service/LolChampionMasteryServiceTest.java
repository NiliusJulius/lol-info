package com.niliusjulius.lolinfo.riot.lol.service;

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.lol.builders.championmastery.ChampionMasteryBuilder;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class LolChampionMasteryServiceTest {
    @Mock
    ChampionMasteryBuilder championMasteryBuilder;

    @InjectMocks
    LolChampionMasteryService lolChampionMasteryService;

    @Nested
    @DisplayName("RetrieveChampionMasteries should")
    public class RetrieveChampionMasteriesTest {

        @Test
        @DisplayName("make a call to retrieve a summoner's champion masteries based on the given parameters")
        public void retrieveChampionMasteriesValidTest() {
            when(championMasteryBuilder.withPlatform(any(LeagueShard.class))).thenReturn(championMasteryBuilder);
            when(championMasteryBuilder.withSummonerId(anyString())).thenReturn(championMasteryBuilder);
            when(championMasteryBuilder.getChampionMasteries()).thenReturn(new ArrayList<>());

            LeagueShard leagueShard = LeagueShard.EUW1;
            String leagueShardName = LeagueShard.EUW1.name();
            String summonerId = "TestSummonerId";

            lolChampionMasteryService.retrieveChampionMasteries(leagueShardName, summonerId);

            verify(championMasteryBuilder, times(1)).withPlatform(leagueShard);
            verify(championMasteryBuilder, times(1)).withSummonerId(summonerId);
            verify(championMasteryBuilder, times(1)).getChampionMasteries();
        }
    }

    @Nested
    @DisplayName("RetrieveTop5ChampionMasteries should")
    public class RetrieveTop5ChampionMasteriesTest {

        @Test
        @DisplayName("make a call to retrieve a summoner's top 5 champion masteries based on the given parameters")
        public void retrieveTop5ChampionMasteriesValidTest() {
            when(championMasteryBuilder.withPlatform(any(LeagueShard.class))).thenReturn(championMasteryBuilder);
            when(championMasteryBuilder.withSummonerId(anyString())).thenReturn(championMasteryBuilder);
            when(championMasteryBuilder.getTopChampions(5)).thenReturn(new ArrayList<>());

            LeagueShard leagueShard = LeagueShard.EUW1;
            String leagueShardName = LeagueShard.EUW1.name();
            String summonerId = "TestSummonerId";

            lolChampionMasteryService.retrieveTop5ChampionMasteries(leagueShardName, summonerId);

            verify(championMasteryBuilder, times(1)).withPlatform(leagueShard);
            verify(championMasteryBuilder, times(1)).withSummonerId(summonerId);
            verify(championMasteryBuilder, times(1)).getTopChampions(5);
        }
    }
}
