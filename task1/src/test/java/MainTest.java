import org.junit.*;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class MainTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().mute();

    @Test
    public void test0() {
        systemInMock.provideLines("0");
        Main.main(null);
        Assert.assertEquals("zyxwvutsrqponmlkjihgfedcba", systemOutRule.getLog());
    }

    @Test
    public void test1() {
        systemInMock.provideLines("1", "z");
        Main.main(null);
        Assert.assertEquals("zyxwvutsrqponmlkjihgfedcba", systemOutRule.getLog());
    }

    @Test
    public void test2() {
        systemInMock.provideLines("3", "a", "r", "b");
        Main.main(null);
        Assert.assertEquals("zyxwvutsqponmlkjihgfedcarb", systemOutRule.getLog());
    }

    @Test
    public void test3() {
        systemInMock.provideLines(
                "26", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d",
                "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"
        );
        Main.main(null);
        Assert.assertEquals("qwertyuiopasdfghjklzxcvbnm", systemOutRule.getLog());
    }

    @Test
    public void test4() {
        systemInMock.provideLines("5", "asd", "ad", "qwe", "qas", "sdf");
        Main.main(null);
        Assert.assertEquals("zyxwvutrponmlkjihgfecbaqsd", systemOutRule.getLog());
    }

    @Test
    public void test5() {
        systemInMock.provideLines("5", "asd", "asdfg", "asdf", "f", "d");
        Main.main(null);
        Assert.assertEquals("Impossible", systemOutRule.getLog());
    }

    @Test
    public void test6() {
        systemInMock.provideLines("4", "asd", "asfg", "f", "d");
        Main.main(null);
        Assert.assertEquals("Impossible", systemOutRule.getLog());
    }

    @Test
    public void test7() {
        systemInMock.provideLines(
                "26", "ritieo", "ritiep", "ritiez", "a", "aaa", "aag", "aaz", "aab", "o", "p", "pppo", "pppp", "pppz",
                "zzzz", "zzzza", "zzzzay", "zzzzaj", "zb", "by", "bj", "yj", "j", "x", "xw", "xj", "xx");
        Main.main(null);
        Assert.assertEquals("wvutsrqnmlkihfedcaopgzbyjx", systemOutRule.getLog());
    }

    @Test
    public void test8() {
        systemInMock.provideLines(
                "26", "ritieo", "ritiep", "ritiez", "a", "aaa", "aag", "aaz", "aab", "o", "p", "pppo", "pppp", "pppz",
                "zzzz", "zzzga", "zzzzay", "zzzzaj", "zb", "by", "bj", "yj", "j", "x", "xw", "xj", "xx");
        Main.main(null);
        Assert.assertEquals("Impossible", systemOutRule.getLog());
    }
}
