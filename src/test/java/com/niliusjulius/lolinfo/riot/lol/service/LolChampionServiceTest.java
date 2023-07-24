package com.niliusjulius.lolinfo.riot.lol.service;

import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.staticdata.champion.StaticChampion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LolChampionServiceTest {
    @Mock
    private DDragonAPI dDragonAPI;

    @InjectMocks
    private LolChampionService lolChampionService;

    private final String testChampionName = "testChampionName";

    private Map<Integer, StaticChampion> createChampionsMap(int count) {
        Map<Integer, StaticChampion> championsMap = new HashMap<>();
        for (int i = 0; i < count; i++) {
            StaticChampion staticChampion = mock(StaticChampion.class);
            when(staticChampion.getName()).thenReturn(testChampionName + i);
            when(staticChampion.getId()).thenReturn(i);
            championsMap.put(i, staticChampion);
        }
        return championsMap;
    }

    @Nested
    @DisplayName("RetrieveChampionsMap should")
    public class RetrieveChampionsMapTest {

        @Test
        @DisplayName("make a call to retrieve a map of all champions")
        public void retrieveChampionsMapTest() {
            final int championCount = 3;
            Map<Integer, StaticChampion> championsMap = createChampionsMap(championCount);

            try (MockedStatic<DDragonAPI> dDragonAPIMockedStatic = mockStatic(DDragonAPI.class)) {

                dDragonAPIMockedStatic.when(DDragonAPI::getInstance).thenReturn(dDragonAPI);
                when(dDragonAPI.getChampions()).thenReturn(championsMap);

                Map<Integer, StaticChampion> championsResultMap = lolChampionService.retrieveChampionsMap();

                verify(dDragonAPI, times(1)).getChampions();
                assertEquals(championCount, championsResultMap.size());
                for (int i = 0; i < championCount; i++) {
                    assertEquals(championsMap.get(i).getName(), championsResultMap.get(i).getName());
                    assertEquals(championsMap.get(i).getId(), championsResultMap.get(i).getId());
                }
            }
        }
    }

    @Nested
    @DisplayName("RetrieveChampionsList should")
    public class RetrieveChampionsListTest {

        @Test
        @DisplayName("make a call to retrieve a list of all champions")
        public void retrieveChampionsListTest() {
            final int championCount = 3;
            Map<Integer, StaticChampion> championsMap = createChampionsMap(championCount);

            try (MockedStatic<DDragonAPI> dDragonAPIMockedStatic = mockStatic(DDragonAPI.class)) {

                dDragonAPIMockedStatic.when(DDragonAPI::getInstance).thenReturn(dDragonAPI);
                when(dDragonAPI.getChampions()).thenReturn(championsMap);

                List<StaticChampion> championsResultList = lolChampionService.retrieveChampionsList();

                verify(dDragonAPI, times(1)).getChampions();
                assertEquals(championCount, championsResultList.size());
                for (int i = 0; i < championCount; i++) {
                    assertEquals(championsMap.get(i).getName(), championsResultList.get(i).getName());
                    assertEquals(championsMap.get(i).getId(), championsResultList.get(i).getId());
                }
            }
        }
    }
}
