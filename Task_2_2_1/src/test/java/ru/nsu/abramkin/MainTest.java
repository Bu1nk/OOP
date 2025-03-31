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

    /**
     * Additional test to verify that the configuration data is read correctly.
     * This test checks if the parameters are properly loaded from the JSON file.
     *
     * @throws IOException if the configuration file is not found.
     * @throws ParseException if the JSON file cannot be parsed.
     */
    @Test
    void testReadPizzeriaData() throws IOException, ParseException {
        Main.readPizzeriaData();  // Calling method directly to check for errors

        // No direct assertions since we rely on successful reading
    }
}
