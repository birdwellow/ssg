package net.fvogel.hooks;

import org.junit.Assert;
import org.junit.Test;

public class RandomTest {

    @Test
    public void testRandomInt() {
        DataMigrationHook hook = new DataMigrationHook();

        for (int step = 0; step < 1000; step++) {
            int randomInt = hook.getRandomInt();
            Assert.assertTrue(randomInt <= 1 && randomInt >= 0);
        }
    }

    @Test
    public void testRandomRangeInt() {
        DataMigrationHook hook = new DataMigrationHook();

        for (int step = 0; step < 1000; step++) {
            int randomRangeInt = hook.getRandomInt(5);
            Assert.assertTrue(randomRangeInt <= 5 && randomRangeInt >= 0);
        }

    }

}
