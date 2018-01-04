package net.fvogel.hooks;

import net.fvogel.service.RandomizerService;
import org.junit.Assert;
import org.junit.Test;

public class RandomizerServiceTest {

    @Test
    public void testRandomInt() {
        RandomizerService randomizerService = new RandomizerService();

        for (int step = 0; step < 1000; step++) {
            int randomInt = randomizerService.getRandomInt();
            Assert.assertTrue(randomInt <= 1 && randomInt >= 0);
        }
    }

    @Test
    public void testRandomRangeInt() {
        RandomizerService randomizerService = new RandomizerService();

        for (int step = 0; step < 1000; step++) {
            int randomRangeInt = randomizerService.getRandomInt(5);
            Assert.assertTrue(randomRangeInt <= 5 && randomRangeInt >= 0);
        }

    }

}
