package com.niliusjulius.lolinfo.web.controller;

import com.niliusjulius.lolinfo.component.Messages;
import com.niliusjulius.lolinfo.riot.lol.service.LolChampionMasteryService;
import com.niliusjulius.lolinfo.riot.lol.service.LolSummonerService;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LolSummonerController.class)
@OverrideAutoConfiguration(enabled = true)
public class LolSummonerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LolSummonerService lolSummonerService;

    @MockBean
    private LolChampionMasteryService lolChampionMasteryService;

    @Mock
    private Summoner mockSummoner;

    private final String testServerName = "TestServerName";
    private final String testSummonerName = "TestSummonerName";
    private final Integer testSummonerLevel = 123;
    private final String testSummonerId = "TestSummonerId";

    @Nested
    @DisplayName("summoner should")
    @OverrideAutoConfiguration(enabled = true)
    public class SummonerTest {

        @Test
        @DisplayName("display the summoner info")
        public void summonerValidInputTest() throws Exception {
            when(lolSummonerService.retrieveSummoner(anyString(), anyString())).thenReturn(mockSummoner);
            when(mockSummoner.getName()).thenReturn(testSummonerName);
            when(mockSummoner.getSummonerLevel()).thenReturn(testSummonerLevel);
            when(mockSummoner.getSummonerId()).thenReturn(testSummonerId);

            List<ChampionMastery> championMasteryList = new ArrayList<>();

            when(lolChampionMasteryService.retrieveTop5ChampionMasteries(anyString(), eq(testSummonerId))).thenReturn(championMasteryList);

            String url = "/summoner/" + testServerName + "/" + testSummonerName;

            mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString(testSummonerName)))
                    .andExpect(content().string(containsString(testSummonerLevel.toString())))
                    .andExpect(content().string(containsString(Messages.getMessage("web.mastery.top5.header"))));
        }

        @Test
        @DisplayName("display not found message when summoner name does not exist")
        public void summonerInvalidSummonerNameTest() throws Exception {
            when(lolSummonerService.retrieveSummoner(anyString(), anyString())).thenReturn(null);

            String url = "/summoner/" + testServerName + "/" + testSummonerName;

            mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString(Messages.getMessage("web.summoner.not.found"))));
        }
    }
}
