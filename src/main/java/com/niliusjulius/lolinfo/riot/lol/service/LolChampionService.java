package com.niliusjulius.lolinfo.riot.lol.service;

import lombok.RequiredArgsConstructor;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.staticdata.champion.StaticChampion;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class LolChampionService {

    public List<StaticChampion> retrieveChampionsList() {
        return new ArrayList<>(DDragonAPI.getInstance().getChampions().values());
    }
}
