package com.niliusjulius.lolinfo.riot.lol.service;

import com.niliusjulius.lolinfo.riot.lol.entity.SummonerDetails;
import com.niliusjulius.lolinfo.riot.lol.util.SupportStoleMyBlueMember;
import com.niliusjulius.lolinfo.riot.lol.validation.LeagueShardConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.lol.builders.summoner.SummonerBuilder;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class LolSummonerService {

    private final SummonerBuilder summonerBuilder;

    public Summoner retrieveSummonerByName(@LeagueShardConstraint String shardName, @NotBlank(message = "{summoner.error.name.not.empty}") String summonerName) {
        return summonerBuilder.withPlatform(LeagueShard.fromString(shardName).orElse(null)).withName(summonerName).get();
    }

    public Summoner retrieveSummonerBySummonerId(@LeagueShardConstraint String shardName, @NotBlank(message = "{summoner.error.id.not.empty}") String summonerId) {
        return summonerBuilder.withPlatform(LeagueShard.fromString(shardName).orElse(null)).withSummonerId(summonerId).get();
    }

    public List<SummonerDetails> retrieveSupportStoleMyBlueExtendedSummoners() {
        List<SummonerDetails> summonerList = new ArrayList<>();
        SupportStoleMyBlueMember.stream()
                .forEach(member -> {
                    Summoner summoner = retrieveSummonerBySummonerId(member.leagueShard.getValue(), member.summonerId);
                    SummonerDetails summonerDetails = new SummonerDetails(summoner);
                    summonerDetails.setMasteryScore(summoner.getMasteryScore());
                    for (LeagueEntry leagueEntry: summoner.getLeagueEntry()) {
                        if (leagueEntry.getQueueType().equals(GameQueueType.RANKED_SOLO_5X5)) {
                            summonerDetails.setSolo_5v5_rank(leagueEntry);
                        } else if (leagueEntry.getQueueType().equals(GameQueueType.RANKED_FLEX_SR)) {
                            summonerDetails.setFlex_5v5_rank(leagueEntry);
                        }
                    }
                    if (summonerDetails.getSolo_5v5_rank() == null) {
                        LeagueEntry leagueEntry = new LeagueEntry();
                        summonerDetails.setSolo_5v5_rank(leagueEntry);
                    }
                    if (summonerDetails.getFlex_5v5_rank() == null) {
                        LeagueEntry leagueEntry = new LeagueEntry();
                        summonerDetails.setFlex_5v5_rank(leagueEntry);
                    }
                    summonerList.add(summonerDetails);
                });
        return summonerList;
    }
}
