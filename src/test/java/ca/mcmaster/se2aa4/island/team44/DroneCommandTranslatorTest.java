package ca.mcmaster.se2aa4.island.team44;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DroneCommandTranslatorTest {
    private DroneCommandTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new DroneCommandTranslator();
    }

    @Test
    void testStopCommand() {
        String result = translator.stop();
        JSONObject json = new JSONObject(result);
        assertEquals("stop", json.getString("action"));
        assertFalse(json.has("parameters"));
    }

    @Test
    void testFlyCommand() {
        String result = translator.fly();
        JSONObject json = new JSONObject(result);
        assertEquals("fly", json.getString("action"));
        assertFalse(json.has("parameters"));
    }

    @Test
    void testEchoCommandWithDirection() {
        String result = translator.echo(Compass.E);
        JSONObject json = new JSONObject(result);
        assertEquals("echo", json.getString("action"));
        JSONObject params = json.getJSONObject("parameters");
        assertEquals("E", params.getString("direction"));
    }

    @Test
    void testHeadingCommandWithDirection() {
        String result = translator.heading(Compass.S);
        JSONObject json = new JSONObject(result);
        assertEquals("heading", json.getString("action"));
        JSONObject params = json.getJSONObject("parameters");
        assertEquals("S", params.getString("direction"));
    }

    @Test
    void testScanCommand() {
        String result = translator.scan();
        JSONObject json = new JSONObject(result);
        assertEquals("scan", json.getString("action"));
        assertFalse(json.has("parameters"));
    }
}
