import java.util.*;

/**
 * Идея алгоритма:
 * <ul>
 *     <li>строим орграф; ребро x->y означает, что символ x лексикографически меньше, чем символ y</li>
 *     <li>кроме того добавим вершину {@code '\0'}, которая будет служить пустой строкой</li>
 *     <li>пустая строка лексикографически меньше любого символа</li>
 *     <li>считываем имена из стандартного ввода, рассматриваем по два идущих подряд слова, находим позицию, в которых символы не совпали, добавляем ребро в граф</li>
 *     <li>отметим, что могут появиться параллельные рёбра, которые не помешают корректной работе алгоритма</li>
 *     <li>проверяем получившийся граф на ацикличность</li>
 *     <li>если граф ацикличный, то производим топологическую сортировку и выводим символы алфавита</li>
 *     <li>если в графе нашёлся цикл, то требуемой перестановки не существует</li>
 * </ul>
 */
public class Main {

    public static void main(String[] args) {
        Map<Character, List<Character>> graph = initGraph();
        fillGraph(graph);
        if (graphContainsCycles(graph)) {
            System.out.print("Impossible");
        } else {
            for (char letter : topSort(graph)) {
                if (letter != '\0') {
                    System.out.print(letter);
                }
            }
        }
    }

    private static Map<Character, List<Character>> initGraph() {
        Map<Character, List<Character>> graph = new HashMap<>();
        graph.put('\0', new ArrayList<>());
        for (char letter = 'a'; letter <= 'z'; letter++) {
            graph.get('\0').add(letter);
            graph.put(letter, new ArrayList<>());
        }
        return graph;
    }

    private static void fillGraph(final Map<Character, List<Character>> graph) {
        final Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String prevName = in.next();

        for (int i = 1; i < n; ++i) {
            String name = in.next();
            int j = 0;
            while (j < prevName.length() && j < name.length() && prevName.charAt(j) == name.charAt(j)) {
                j++;
            }
            if (j < prevName.length()) {
                char key = prevName.charAt(j);
                char value = j == name.length() ? '\0' : name.charAt(j);
                graph.get(key).add(value); // все строки различны => петли не будет
            }
            prevName = name;
        }
    }

    private static boolean graphContainsCycles(final Map<Character, List<Character>> graph) {
        short[] colors = new short[27];
        for (char letter : graph.keySet()) {
            if (colors[getIdx(letter)] == 0 && check(letter, colors, graph)) {
                return true;
            }
        }
        return false;
    }

    private static boolean check(final char from, final short[] colors, final Map<Character, List<Character>> graph) {
        colors[getIdx(from)] = 1;
        for (char to : graph.get(from)) {
            if (colors[getIdx(to)] == 1 || colors[getIdx(to)] == 0 && check(to, colors, graph)) {
                return true;
            }
        }
        colors[getIdx(from)] = 2;
        return false;
    }

    private static List<Character> topSort(final Map<Character, List<Character>> graph) {
        boolean[] used = new boolean[27];
        List<Character> alphabet = new ArrayList<>();
        for (char letter : graph.keySet()) {
            if (!used[getIdx(letter)]) {
                dfs(letter, used, alphabet, graph);
            }
        }
        Collections.reverse(alphabet);
        return alphabet;
    }

    private static void dfs(
            final char from,
            final boolean[] used,
            final List<Character> alphabet,
            final Map<Character, List<Character>> graph
    ) {
        used[getIdx(from)] = true;
        for (char to : graph.get(from)) {
            if (!used[getIdx(to)]) {
                dfs(to, used, alphabet, graph);
            }
        }
        alphabet.add(from);
    }

    private static int getIdx(final char c) {
        return Math.max(0, c - 'a' + 1);
    }
}
