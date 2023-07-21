package com.niliusjulius.lolinfo.riot.lol.entity;


import lombok.Getter;
import lombok.Setter;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

import java.util.List;

@Getter
@Setter
public class SummonerDetails {

    private Summoner summoner;
    private int masteryScore;
    private List<ChampionMastery> championMasteryList;
    private LeagueEntry solo_5v5_rank;
    private LeagueEntry flex_5v5_rank;

    public SummonerDetails(Summoner summoner) {
        this.summoner = summoner;
    }
}
