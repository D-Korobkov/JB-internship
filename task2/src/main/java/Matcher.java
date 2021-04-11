import java.time.Duration;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class Matcher {
    /**
     * <p>This function matches the given string against the given regular expression.</p>
     * <p>Task runs in the {@link ForkJoinPool#commonPool()} during the specified time period</p>
     * @param text the string to be matched
     * @param regex the regular expression to be compiled
     * @param timeout the time period we are ready to wait for matching
     * @return {@link Future} which executes no longer than given {@code timeout} and contains the result of matching
     */
    public static Future<Boolean> matches(String text, String regex, Duration timeout) {
        return CompletableFuture
                .supplyAsync(() -> Pattern.matches(regex, text))
                .orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS);
    }
}
