package com.niliusjulius.lolinfo.web.controller;

import com.niliusjulius.lolinfo.riot.lol.entity.SummonerDetails;
import com.niliusjulius.lolinfo.riot.lol.service.LolChampionMasteryService;
import com.niliusjulius.lolinfo.riot.lol.service.LolSummonerService;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class LolSummonerController {

    private LolSummonerService lolSummonerService;
    private LolChampionMasteryService lolChampionMasteryService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("version", DDragonAPI.getInstance().getVersions().get(0));
        List<SummonerDetails> summonerList = lolSummonerService.retrieveSupportStoleMyBlueExtendedSummoners();
        summonerList.sort(Comparator.comparing((SummonerDetails s) -> s.getSummoner().getSummonerLevel()).reversed());
        model.addAttribute("ssmbSummonerDetailsByLevel", summonerList);
        List<SummonerDetails> summonerListByMastery = new ArrayList<>(summonerList);
        summonerListByMastery.sort(Comparator.comparing(SummonerDetails::getMasteryScore).reversed());
        model.addAttribute("ssmbSummonerDetailsByMastery", summonerListByMastery);
        List<SummonerDetails> summonerListBySoloRank = new ArrayList<>(summonerList);
        summonerListBySoloRank.sort(Comparator.comparing((SummonerDetails s) -> s.getSolo_5v5_rank().getTierDivisionType()));
        model.addAttribute("ssmbSummonerDetailsBySoloRank", summonerListBySoloRank);
        List<SummonerDetails> summonerListByFlexRank = new ArrayList<>(summonerList);
        summonerListByFlexRank.sort(Comparator.comparing((SummonerDetails s) -> s.getFlex_5v5_rank().getTierDivisionType()));
        model.addAttribute("ssmbSummonerDetailsByFlexRank", summonerListByFlexRank);
        return "index";
    }

    @GetMapping("summoner/{server-name}/{summoner-name}")
    public String summonerLevel(@PathVariable("server-name") String serverName,
                                @PathVariable("summoner-name") String summonerName,
                                Model model) {
        Summoner summoner = lolSummonerService.retrieveSummonerByName(serverName, summonerName);
        model.addAttribute("version", DDragonAPI.getInstance().getVersions().get(0));
        model.addAttribute("summoner", summoner);
        if (summoner != null) {
            model.addAttribute("masteries", lolChampionMasteryService.retrieveTop5ChampionMasteries(serverName, summoner.getSummonerId()));
        }
        return "summoner";
    }
}
