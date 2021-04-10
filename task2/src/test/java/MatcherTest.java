import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class MatcherTest {
    @Test
    public void simpleTest1() {
        boolean result = Matcher.matches("aaa", "aaa", Duration.ofSeconds(1));
        Assert.assertTrue(result);
    }

    @Test
    public void simpleTest2() {
        boolean result = Matcher.matches("aaa", "aaaa", Duration.ofSeconds(1));
        Assert.assertFalse(result);
    }

    @Test
    public void emailRegexTest1() {
        boolean result = Matcher.matches(
                "user@mail.ru",
                "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
                Duration.ofSeconds(1)
        );
        Assert.assertTrue(result);
    }

    @Test
    public void emailRegexTest2() {
        boolean result = Matcher.matches(
                "dog goes woof",
                "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
                Duration.ofSeconds(1)
        );
        Assert.assertFalse(result);
    }

    @Test
    public void zeroTimeoutTest() {
        boolean result = Matcher.matches("a", "a", Duration.ofSeconds(0));
        Assert.assertFalse(result); // timeout error
    }

    @Test
    public void smartRegexTest() {
        boolean result = Matcher.matches("a".repeat(100000), "a+", Duration.ofSeconds(1));
        Assert.assertTrue(result);
    }

    @Test
    public void dumpRegexTest1() {
        boolean result = Matcher.matches("a".repeat(100000), "(a|aa)+", Duration.ofSeconds(1));
        Assert.assertFalse(result); // stack overflow error
    }

    @Test
    public void dumpRegexTest2() {
        boolean result = Matcher.matches("a".repeat(100000), "^(([a-z])+.)+[A-Z]([a-z])+$", Duration.ofSeconds(1));
        Assert.assertFalse(result); // timeout error
    }
}
