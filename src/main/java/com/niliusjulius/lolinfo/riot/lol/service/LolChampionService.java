package com.niliusjulius.lolinfo.riot.lol.service;

import lombok.RequiredArgsConstructor;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.staticdata.champion.StaticChampion;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Validated
@RequiredArgsConstructor
public class LolChampionService {

    public Map<Integer, StaticChampion> retrieveChampionsMap() {
        return DDragonAPI.getInstance().getChampions(DDragonAPI.getInstance().getVersions().get(0), null);
    }

    public List<StaticChampion> retrieveChampionsList() {
        return new ArrayList<>(DDragonAPI.getInstance().getChampions(
                DDragonAPI.getInstance().getVersions().get(0),
                null
        ).values());
    }

    public StaticChampion retrieveChampion(String championKey) {
        Map<Integer, StaticChampion> championMap = retrieveChampionsMap();
        for (StaticChampion champion : championMap.values()) {
            if (champion.getKey().equals(championKey)) {
                return champion;
            }
        }
        return null;
    }
}
