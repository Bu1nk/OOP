package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * Test class for the Main class.
 */
class MainTest {

    /**
     * Test method to verify the main method runs without errors.
     */
    @Test
    void testMain() throws InterruptedException, IOException, ParseException {
        Main.main(new String[] {});
    }
}
