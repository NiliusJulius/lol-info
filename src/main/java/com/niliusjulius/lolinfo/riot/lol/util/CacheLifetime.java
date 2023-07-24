package com.niliusjulius.lolinfo.riot.lol.util;

import no.stelar7.api.r4j.basic.cache.CacheLifetimeHint;
import no.stelar7.api.r4j.basic.constants.api.URLEndpoint;

import java.util.concurrent.TimeUnit;

public class CacheLifetime {

    private static final long DAYS_7 = TimeUnit.DAYS.toMillis(7);
    private static final long DAYS_1 = TimeUnit.DAYS.toMillis(1);
    private static final long HOURS_1 = TimeUnit.HOURS.toMillis(1);
    private static final long MINUTES_30 = TimeUnit.MINUTES.toMillis(30);
    private static final long MINUTES_10 = TimeUnit.MINUTES.toMillis(10);
    private static final long MINUTES_5 = TimeUnit.MINUTES.toMillis(5);
    private static final long SECONDS_30 = TimeUnit.SECONDS.toMillis(30);

    public static void setCacheLifetime() {
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_SUMMONER_BY_ACCOUNT, MINUTES_5);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_SUMMONER_BY_PUUID, MINUTES_5);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_SUMMONER_BY_NAME, MINUTES_5);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_SUMMONER_BY_ID, MINUTES_5);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_SPECTATOR_FEATURED, MINUTES_10);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_SPECTATOR_CURRENT, SECONDS_30);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_MASTERY_BY_ID, MINUTES_10);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_MASTERY_BY_CHAMPION, MINUTES_10);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_MASTERY_SCORE, MINUTES_10);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V3_CHAMPION_ROTATIONS, HOURS_1);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_LEAGUE, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_LEAGUE_ENTRY, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_LEAGUE_RANK, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_LEAGUE_MASTER, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_LEAGUE_GRANDMASTER, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V4_LEAGUE_CHALLENGER, MINUTES_30);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V5_MATCHLIST, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V5_MATCH, MINUTES_30);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V5_TIMELINE, MINUTES_30);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V1_CHALLENGES_PERCENTILES, MINUTES_10);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V1_CHALLENGES_PERCENTILES_BY_ID, MINUTES_10);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V1_CHALLENGES_CONFIG, DAYS_1);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V1_CHALLENGES_CONFIG_BY_ID, DAYS_1);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V1_CHALLENGES_LEADERBOARD_BY_LEVEL, HOURS_1);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.V1_CHALLENGES_BY_PLAYER, MINUTES_10);

        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_CHAMPION_MANY, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_ITEMS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_LANGUAGE_STRINGS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_LANGUAGES, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_MAPS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_MASTERIES, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_PROFILEICONS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_RUNES, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_PERKS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_PERKPATHS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_SUMMONER_SPELLS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_VERSIONS, HOURS_1);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_REALMS, DAYS_7);
        CacheLifetimeHint.DEFAULTS.add(URLEndpoint.DDRAGON_TARBALL, DAYS_7);
    }
}
