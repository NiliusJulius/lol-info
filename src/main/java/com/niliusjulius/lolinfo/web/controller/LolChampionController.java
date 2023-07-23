package com.niliusjulius.lolinfo.web.controller;

import com.niliusjulius.lolinfo.riot.lol.service.LolChampionService;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.staticdata.champion.StaticChampion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/champions")
public class LolChampionController {

    private LolChampionService lolChampionService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("version", DDragonAPI.getInstance().getVersions().get(0));
        List<StaticChampion> championList = lolChampionService.retrieveChampionsList();
        championList.sort(Comparator.comparing(StaticChampion::getName));
        model.addAttribute("championList", championList);
        return "champions";
    }
}
