package com.niliusjulius.lolinfo.shell.component.lol.command;

import com.github.fonimus.ssh.shell.SshShellHelper;
import com.github.fonimus.ssh.shell.commands.SshShellComponent;
import com.niliusjulius.lolinfo.component.Messages;
import com.niliusjulius.lolinfo.riot.lol.component.LolSummoner;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@SshShellComponent
@ShellCommandGroup("LOL Commands")
@AllArgsConstructor
public class LolSummonerCommands {

    private final LolSummoner lolSummoner;
    private final SshShellHelper helper;

    @ShellMethod(key = "get-level", value = "retrieve a Summoner's level")
    public String getSummonerLevel(@ShellOption(value = "server") String serverName, @ShellOption(value = "name") String summonerName) {

        Summoner summoner;
        try {
            summoner = lolSummoner.retrieveSummoner(serverName, summonerName);
        } catch (ConstraintViolationException ce) {
            return handleConstraintViolations(ce);
        }

        if (summoner == null) {
            return helper.getError(Messages.getMessage("summoner.not.found", summonerName));
        } else {
            return Messages.getMessage("summoner.has.level", summoner.getName(), summoner.getSummonerLevel());
        }
    }

    @ShellMethod(key = "get-mastery-score", value = "retrieve a Summoner's mastery score")
    public String getSummonerMasteryScore(@ShellOption(value = "server") String serverName, @ShellOption(value = "name") String summonerName) {

        Summoner summoner;
        try {
            summoner = lolSummoner.retrieveSummoner(serverName, summonerName);
        } catch (ConstraintViolationException ce) {
            return handleConstraintViolations(ce);
        }

        if (summoner == null) {
            return helper.getError(Messages.getMessage("summoner.not.found", summonerName));
        } else {
            return Messages.getMessage("summoner.has.mastery.score", summoner.getName(), summoner.getMasteryScore());
        }
    }

    private String handleConstraintViolations(ConstraintViolationException ce) {
        StringBuilder message = new StringBuilder();
        ce.getConstraintViolations().forEach(cv -> {
            if (!message.isEmpty()) {
                message.append(System.getProperty("line.separator"));
            }
            message.append(helper.getError(cv.getMessage()));
        });
        return message.toString();
    }
}
