package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JSONDataParserTest {

    private JSONDataParser parser;
    private JSONObject basicJSON;
    private JSONObject extraJSON;

    @BeforeEach
    void setUp() {
        parser = new JSONDataParser();

        extraJSON = new JSONObject()
                .put("range", 5)
                .put("found", "GROUND")
                .put("biomes", new JSONArray().put("OCEAN"))
                .put("creeks", new JSONArray().put("creek"))
                .put("sites", new JSONArray().put("site"));

        basicJSON = new JSONObject()
                .put("heading", "N")
                .put("cost", 20)
                .put("status", "OK")
                .put("extras", extraJSON);
    }

    @Test
    void testGetHeading() {
        assertEquals("N", parser.getHeading(basicJSON));
    }

    @Test
    void testGetCost() {
        assertEquals(20, parser.getCost(basicJSON));
    }

    @Test
    void testGetStatus() {
        assertEquals("OK", parser.getStatus(basicJSON));
    }

    @Test
    void testGetExtraInfo() {
        assertEquals(extraJSON.toString(), parser.getExtraInfo(basicJSON).toString());
    }

    @Test
    void testGetRange() {
        assertEquals(5, parser.getRange(basicJSON));
    }

    @Test
    void testGetFound() {
        assertEquals("GROUND", parser.getFound(basicJSON));
    }

    @Test
    void testGetBiomes() {
        assertEquals("OCEAN", parser.getBiomes(basicJSON));
    }

    @Test
    void testGetSiteIDs() {
        assertEquals("site", parser.getSiteIDs(basicJSON));
    }

    @Test
    void testGetCreekIDs() {
        assertEquals("creek", parser.getCreekIDs(basicJSON));
    }

    @Test
    void testHasCreek() {
        assertTrue(parser.hasCreek(basicJSON));
    }

    @Test
    void testHasESite() {
        assertTrue(parser.hasESite(basicJSON));
    }

    @Test
    void testHasOcean() {
        assertTrue(parser.hasOcean(basicJSON));
    }

    @Test
    void testGroundTrue() {
        assertTrue(parser.ground(basicJSON));
    }

    @Test
    void testGroundFalse() {
        extraJSON.put("found", "OUT_OF_RANGE");
        basicJSON.put("extras", extraJSON);
        assertFalse(parser.ground(basicJSON));
    }
}