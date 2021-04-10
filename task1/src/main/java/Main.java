import java.util.*;

public class Main {
    private static final Scanner IN = new Scanner(System.in);
    private static final Map<Character, List<Character>> GRAPH = new HashMap<>();

    public static void main(String[] args) {
        initGraph();
        fillGraph(IN.nextInt());
        if (graphContainsCycles()) {
            System.out.print("Impossible");
        } else {
            for (char letter : topSort()) {
                System.out.print(letter);
            }
        }
    }

    private static void initGraph() {
        GRAPH.put('\0', new ArrayList<>());
        for (char letter = 'a'; letter <= 'z'; letter++) {
            GRAPH.get('\0').add(letter);
            GRAPH.put(letter, new ArrayList<>());
        }
    }

    private static void fillGraph(int n) {
        String prevName = IN.next();
        for (int i = 1; i < n; ++i) {
            String name = IN.next();
            int j = 0;
            while (j < prevName.length() && j < name.length() && prevName.charAt(j) == name.charAt(j)) {
                j++;
            }
            if (j < prevName.length()) {
                char key = prevName.charAt(j);
                char value = j == name.length() ? '\0' : name.charAt(j);
                GRAPH.get(key).add(value); // все строки различны => петли не будет
            }
            prevName = name;
        }
    }

    private static boolean graphContainsCycles() {
        short[] colors = new short[27];
        for (char letter : GRAPH.keySet()) {
            if (colors[getIdx(letter)] == 0) {
                if (check(letter, colors)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check(char from, short[] colors) {
        colors[getIdx(from)] = 1;
        for (char to : GRAPH.get(from)) {
            if (colors[getIdx(to)] == 1 || colors[getIdx(to)] == 0 && check(to, colors)) {
                return true;
            }
        }
        colors[getIdx(from)] = 2;
        return false;
    }

    private static List<Character> topSort() {
        boolean[] used = new boolean[27];
        List<Character> alphabet = new ArrayList<>();
        for (char letter : GRAPH.keySet()) {
            if (!used[getIdx(letter)]) {
                dfs(letter, used, alphabet);
            }
        }
        Collections.reverse(alphabet);
        return alphabet;
    }

    private static void dfs(char from, boolean[] used, List<Character> alphabet) {
        used[getIdx(from)] = true;
        for (char to : GRAPH.get(from)) {
            if (!used[getIdx(to)]) {
                dfs(to, used, alphabet);
            }
        }
        alphabet.add(from);
    }

    private static int getIdx(char c) {
        return Math.max(0, c - 'a' + 1);
    }
}
