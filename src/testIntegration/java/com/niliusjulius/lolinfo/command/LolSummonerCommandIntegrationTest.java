package com.niliusjulius.lolinfo.command;

import com.github.fonimus.ssh.shell.SshShellHelper;
import com.niliusjulius.lolinfo.component.Messages;
import com.niliusjulius.lolinfo.shell.component.lol.command.LolSummonerCommands;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LolSummonerCommandIntegrationTest {

    @Autowired
    private LolSummonerCommands lolSummonerCommands;
    @Autowired
    private SshShellHelper helper;

    @Nested
    @DisplayName("get summoner level")
    class GetSummonerLevelTests {

        String invalidLeagueShardName = "Made Up Shard Name";
        String summonerName = "FerrantePescara";

        @Nested
        @DisplayName("parameter validation should")
        class ValidationTests {

            @Test
            @DisplayName("return an error message when an invalid shard/server name is used.")
            public void invalidShardName() {
                String expectedErrorMessage = helper.getError(Messages.getMessage("shard.name.invalid"));
                assertEquals(expectedErrorMessage, lolSummonerCommands.getSummonerLevel(invalidLeagueShardName, summonerName));
            }

            @Test
            @DisplayName("return an error message when an empty summoner name is used.")
            public void invalidSummonerName() {
                String expectedErrorMessage = helper.getError(Messages.getMessage("summoner.error.name.not.empty"));
                assertEquals(expectedErrorMessage, lolSummonerCommands.getSummonerLevel(LeagueShard.EUW1.getValue(), ""));
                assertEquals(expectedErrorMessage, lolSummonerCommands.getSummonerLevel(LeagueShard.EUW1.getValue(), null));
            }

            @Test
            @DisplayName("return error messages when an invalid shard/server name and invalid summoner name are used.")
            public void invalidShardAndName() {
                String message = lolSummonerCommands.getSummonerLevel(invalidLeagueShardName, "");
                assertTrue(message.contains(helper.getError(Messages.getMessage("shard.name.invalid"))),
                        "Expected to receive invalid shard name error.");
                assertTrue(message.contains(helper.getError(Messages.getMessage("summoner.error.name.not.empty"))),
                        "Expected to receive invalid summoner name error.");
            }

            @Test
            @DisplayName("return a message with the summoner name and level.")
            public void validShardAndName() {
                String expectedMessage = Messages.getMessage("summoner.has.level", ".*", ".*");
                String message = lolSummonerCommands.getSummonerLevel(LeagueShard.EUW1.getValue(), summonerName);
                assertTrue(Pattern.compile(expectedMessage).matcher(message).find());
            }
        }
    }
}
