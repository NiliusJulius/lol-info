package com.niliusjulius.lolinfo.riot.lol.util;

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

import java.util.stream.Stream;

public enum SupportStoleMyBlueMember {
    FERRANTEPESCARA("WptnNxE7pxcySX2bZEaEvbwfP8HDO03N7ZnpMdeagOOHYYw", LeagueShard.EUW1),
    LOLBROEK("D8qSwv6l9roJhrSBC-B8IKGdW-H8Iz32_b3nOEMZq_Ko", LeagueShard.EUW1),
    BOTERHAMDEGEKSTE("wRRsrsXf1nBvsJfCerogdsCaOaGCNDCvh93kY2M2kBW-gs-i", LeagueShard.EUW1),
    SHUNIATH("qZEzjA93tONFFfJ4afwnUTWq8Gazna2DgUuaqC8GxN8iElI", LeagueShard.EUW1),
    FEMIT("u_RImbqsMT61_NXJf7ZTaSFlZJkCDAGoltZuNyvLMh7RSgM", LeagueShard.EUW1),
    HAMSTERTJE("ScmRoamoGEJPhE1oNEIF7OcRoUqi2lhdKOqBFEp4EqS7Mhs", LeagueShard.EUW1),
    DRRICHARD("hJXJro8vtEOd7dKDYRmiigC-ItKG8WBO9yoj2nIRmxWMz30", LeagueShard.EUW1),
    THEMASTEROFDOME("RtvYH_TPDHDCwIGbZMLJE4-WQN1ruwLuZ9tLyK4oMtSm_qk", LeagueShard.EUW1),
    KRAKELKRANS("gQ0MaA6a0iUpn-4yIcqfgnMDRIFkyQdK1_fpASO2l-YnjJGXPNBF85rxwQ", LeagueShard.EUW1),
    TJEHU("rMwU0Y3L_rWItbRDwBfBvLAycBSm3fyti99dDSEX_uk6mto", LeagueShard.EUW1);


    public final String summonerId;
    public final LeagueShard leagueShard;

    SupportStoleMyBlueMember(String summonerId, LeagueShard leagueShard) {
        this.summonerId = summonerId;
        this.leagueShard = leagueShard;
    }

    public static Stream<SupportStoleMyBlueMember> stream() {
        return Stream.of(SupportStoleMyBlueMember.values());
    }
}
