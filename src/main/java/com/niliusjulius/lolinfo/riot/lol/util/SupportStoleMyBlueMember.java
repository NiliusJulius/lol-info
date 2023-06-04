package com.niliusjulius.lolinfo.riot.lol.util;

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

import java.util.stream.Stream;

public enum SupportStoleMyBlueMember {
    FERRANTEPESCARA("FerrantePescara", LeagueShard.EUW1),
    LOLBROEK("lolbroek", LeagueShard.EUW1),
    BOTERHAMDEGEKSTE("BoterhamDeGekste", LeagueShard.EUW1),
    SHUNIATH("Shuniath", LeagueShard.EUW1),
    FEMIT("Femit", LeagueShard.EUW1),
    HAMSTERTJE("Hamstertje", LeagueShard.EUW1),
    DRRICHARD("DRRichard", LeagueShard.EUW1),
    THEMASTEROFDOME("TheMasterofDome", LeagueShard.EUW1),
    KRAKELKRANS("Krakelkrans", LeagueShard.EUW1),
    TJEHU("Tjehu", LeagueShard.EUW1);


    public final String name;
    public final LeagueShard leagueShard;

    SupportStoleMyBlueMember(String name, LeagueShard leagueShard) {
        this.name = name;
        this.leagueShard = leagueShard;
    }

    public static Stream<SupportStoleMyBlueMember> stream() {
        return Stream.of(SupportStoleMyBlueMember.values());
    }
}
