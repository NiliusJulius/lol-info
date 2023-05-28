package com.niliusjulius.lolinfo.web.controller;

import com.niliusjulius.lolinfo.riot.lol.service.LolSummonerService;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class LolSummonerController {

    private LolSummonerService lolSummonerService;

    @GetMapping("")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("summoner/{server-name}/{summoner-name}")
    public String summonerLevel(@PathVariable("server-name") String serverName,
                                @PathVariable("summoner-name") String summonerName,
                                Model model) {
        Summoner summoner = lolSummonerService.retrieveSummoner(serverName, summonerName);
        model.addAttribute("summoner", summoner);
        return "summoner";
    }
}
