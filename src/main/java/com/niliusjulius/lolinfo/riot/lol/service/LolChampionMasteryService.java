package com.niliusjulius.lolinfo.riot.lol.service;

import com.niliusjulius.lolinfo.riot.lol.validation.LeagueShardConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.lol.builders.championmastery.ChampionMasteryBuilder;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class LolChampionMasteryService {

    private final ChampionMasteryBuilder championMasteryBuilder;

    public List<ChampionMastery> retrieveChampionMasteries(@LeagueShardConstraint String shardName, @NotBlank(message = "{summoner.error.name.not.empty}") String summonerId) {
        return championMasteryBuilder.withPlatform(LeagueShard.fromString(shardName).orElse(null)).withSummonerId(summonerId).getChampionMasteries();
    }

    public List<ChampionMastery> retrieveTop5ChampionMasteries(@LeagueShardConstraint String shardName, @NotBlank(message = "{summoner.error.name.not.empty}") String summonerId) {
        return championMasteryBuilder.withPlatform(LeagueShard.fromString(shardName).orElse(null)).withSummonerId(summonerId).getTopChampions(5);
    }
}
