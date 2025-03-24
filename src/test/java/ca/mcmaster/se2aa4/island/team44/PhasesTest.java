package ca.mcmaster.se2aa4.island.team44;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.se2aa4.island.team44.explorer.Phases;

class PhasesTest {

    @Test
    public void testSpinToSpawn() {
        assertEquals(Phases.SPAWN, Phases.SPIN.switchPhase());
    }

    @Test
    public void testSpawnToGround() {
        assertEquals(Phases.GROUND, Phases.SPAWN.switchPhase());
    }

    @Test
    public void testGroundToGridSearch() {
        assertEquals(Phases.GRIDSEARCH, Phases.GROUND.switchPhase());
    }

    @Test
    public void testGridSearchToUTurn() {
        assertEquals(Phases.UTURN, Phases.GRIDSEARCH.switchPhase());
    }

    @Test
    public void testUTurnToCheckContinue() {
        assertEquals(Phases.CHECKCONTINUE, Phases.UTURN.switchPhase());
    }

    @Test
    public void testCheckContinueToGridSearch() {
        assertEquals(Phases.GRIDSEARCH, Phases.CHECKCONTINUE.switchPhase());
    }
}
