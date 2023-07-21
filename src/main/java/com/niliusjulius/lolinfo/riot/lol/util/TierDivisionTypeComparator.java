package com.niliusjulius.lolinfo.riot.lol.util;

import no.stelar7.api.r4j.basic.constants.types.lol.TierDivisionType;

import java.util.Comparator;

public class TierDivisionTypeComparator implements Comparator<TierDivisionType> {
    @Override
    public int compare(TierDivisionType tierDivisionType1, TierDivisionType tierDivisionType2) {
        String tier1 = tierDivisionType1.getTier();
        String tier2 = tierDivisionType2.getTier();
        return -1;
    }
}
