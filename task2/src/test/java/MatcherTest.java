import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MatcherTest {
    @Test
    public void simpleTest1() throws InterruptedException, ExecutionException {
        var future = Matcher.matches("aaa", "aaa", Duration.ofSeconds(1));
        Assert.assertTrue(future.get());
    }

    @Test
    public void simpleTest2() throws ExecutionException, InterruptedException {
        var future = Matcher.matches("aaa", "aaaa", Duration.ofSeconds(1));
        Assert.assertFalse(future.get());
    }

    @Test
    public void emailRegexTest1() throws ExecutionException, InterruptedException {
        var future = Matcher.matches(
                "user@mail.ru",
                "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
                Duration.ofSeconds(1)
        );
        Assert.assertTrue(future.get());
    }

    @Test
    public void emailRegexTest2() throws ExecutionException, InterruptedException {
        var future = Matcher.matches(
                "dog goes woof",
                "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
                Duration.ofSeconds(1)
        );
        Assert.assertFalse(future.get());
    }

    @Test
    public void smartRegexTest() throws ExecutionException, InterruptedException {
        var future = Matcher.matches("a".repeat(100000), "a+", Duration.ofSeconds(1));
        Assert.assertTrue(future.get());
    }

    @Test(expected = ExecutionException.class) // stack overflow error
    public void dumpRegexTest1() throws ExecutionException, InterruptedException {
        Matcher.matches("a".repeat(100000), "(a|aa)+", Duration.ofSeconds(1)).get();
    }

    @Test(expected = TimeoutException.class)
    public void dumpRegexTest2() throws Throwable {
        try {
            Matcher.matches("a".repeat(100000), "^(([a-z])+.)+[A-Z]([a-z])+$", Duration.ofSeconds(1)).get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    @Test(expected = TimeoutException.class)
    public void zeroTimeoutTest() throws Throwable {
        try {
            Matcher.matches("a".repeat(100000), "a+", Duration.ofSeconds(0)).get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }
}
