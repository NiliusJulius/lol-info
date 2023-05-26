package com.niliusjulius.lolinfo.web.controller;

import com.niliusjulius.lolinfo.component.Messages;
import com.niliusjulius.lolinfo.riot.lol.service.LolSummonerService;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("lol")
public class LolSummonerController {

    @Autowired
    private LolSummonerService lolSummonerService;

    @GetMapping("summoner-level")
    public String summonerLevel(@RequestParam(name = "serverName") String serverName,
                                @RequestParam(name = "summonerName") String summonerName,
                                Model model) {
        Summoner summoner = lolSummonerService.retrieveSummoner(serverName, summonerName);
        model.addAttribute("summonerLevelText",
                Messages.getMessage("summoner.has.level", summoner.getName(), summoner.getSummonerLevel()));
        return "summoner-level";
    }
}
