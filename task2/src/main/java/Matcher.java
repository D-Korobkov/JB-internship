import java.time.Duration;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class Matcher {
    /**
     * <p>This function matches the given string against the given regular expression.</p>
     * <p>Task runs in the {@link ForkJoinPool#commonPool()} during the specified time period</p>
     * @param text the string to be matched
     * @param regex the regular expression to be compiled
     * @param timeOut the time period we are ready to wait for matching
     * @return {@code true} if {@code text} matches to {@code regexp}
     * and matching ends with out any errors before specified {@code timeout}; {@code false} otherwise
     */
    public static boolean matches(String text, String regex, Duration timeOut) {
        if (text == null || regex == null) {
            return false;
        }
        try {
            return CompletableFuture
                    .supplyAsync(() -> Pattern.matches(regex, text))
                    .get(timeOut.getSeconds(), TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException e) {
            return false;
        }
    }
}
