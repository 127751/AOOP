package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class NumberleModelTest {
    INumberleModel model;
    /**
     * Setup for tests: Initializes a new instance of NumberleModel.
     * This setup must ensure that the model is fresh and not null before each test.
     */
    @BeforeEach
    void setUp() {
        model = new NumberleModel();
    }
    /**
     * Tear down for tests: Clears the model instance.
     * This teardown must ensure that the model is set to null after each test to avoid unintended reuse across tests.
     */
    @AfterEach
    void tearDown() {
        model = null;
    }
    /**
     * Test for processing a valid input.
     * Ensures that processing a correct equation decreases the remaining attempts by one and maintains the invariant.
     */
    @org.junit.jupiter.api.Test
    void processInputValid() {
        model.initialize();
        model.processInput("1+2+3=6");
        assertEquals(INumberleModel.MAX_ATTEMPTS - 1, model.getRemainingAttempts());
        assertTrue(model.invariant());
    }
    /**
     * Test for processing an invalid input.
     * Ensures that processing an incorrect equation (multiple equal signs) does not decrease the remaining attempts and maintains the invariant.
     */
    @org.junit.jupiter.api.Test
    void processInputInvalid() {
        model.initialize();
        model.processInput("=======");
        assertEquals(INumberleModel.MAX_ATTEMPTS, model.getRemainingAttempts());
        assertTrue(model.invariant());
    }
    /**
     * Test for processing multiple inputs leading to game over.
     * Ensures that the game is marked as over after the maximum number of allowed attempts and maintains the invariant.
     */
    @org.junit.jupiter.api.Test
    void processInputGameOver() {
        model.initialize();
        model.processInput("1+2+3=6");
        model.processInput("1+2+3=6");
        model.processInput("1+2+3=6");
        model.processInput("1+2+3=6");
        model.processInput("1+2+3=6");
        model.processInput("1+2+3=6");
        assertTrue(model.isGameOver());
        assertTrue(model.invariant());
    }
}