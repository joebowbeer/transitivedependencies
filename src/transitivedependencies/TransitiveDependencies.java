package transitivedependencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TransitiveDependencies {

    public static void main(String[] args) throws IOException {
        assert args.length == 1; // path-to-dependencies
        Path path = FileSystems.getDefault().getPath(args[0]);
        TransitiveDependencies td = new TransitiveDependencies();
        try (BufferedReader r = Files.newBufferedReader(path, Charset.defaultCharset())) {
            r.lines().forEach(s -> {
                List<String> list = Arrays.asList(s.split("\\s+"));
                td.addDirect(list.get(0), list.subList(1, list.size()));
            });
        }
        td.dump(System.out);
    }

    private final Map<String, List<String>> map = new TreeMap<>();

    public void addDirect(String s, List<String> deps) {
        map.put(s, deps);
    }

    private List<String> getDirect(String s) {
        List<String> list = map.get(s);
        return (list != null) ? list : Collections.emptyList();
    }

    public List<String> dependenciesFor(String root) {
        Set<String> deps = new TreeSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.addAll(getDirect(root));
        while (!queue.isEmpty()) {
            String s = queue.remove();
            if (deps.add(s)) {
                queue.addAll(getDirect(s));
            }
        }
        return new ArrayList<>(deps);
    }

    public void dump(PrintStream ps) {
        map.entrySet().stream().forEach(ps::println);
    }
}
