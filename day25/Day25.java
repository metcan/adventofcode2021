
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
    is div z 1 add to stack
    if not remove from stack
*/

public class Day25 {
    private String _puzzleinput;
    private String _filename;

    public Day25(String file_name) {
        _filename = file_name;
        getPuzzleInput(_filename);
        _puzzleinput = _puzzleinput.trim();
    }

    public String getPuzzleInput(String filename) {
        Path fileName = Path.of(filename);
        try {
            _puzzleinput = Files.readString(fileName);
        } catch (IOException e) {
            System.out.println("Error at file read");
            e.printStackTrace();
            return "";
        }
        return _puzzleinput;

    }

    public String solvePart1() {
        SeeMap seeMap = new SeeMap(_puzzleinput.split("\n"));
        int i = 1;
        while (seeMap.step()) {
            i++;
        }
        return String.valueOf(i);
    }

    public static class SeeMap {
        private final Set<Point> horizontal = new HashSet<>();
        private final Set<Point> vertical = new HashSet<>();
        final int height;
        final int width;

        public SeeMap(String[] input) {
            height = input.length;
            width = input[0].length();
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    switch (input[j].charAt(i)) {
                        case '>' -> horizontal.add(new Point(i, j));
                        case 'v' -> vertical.add(new Point(i, j));
                    }
                }
            }
        }

        public boolean step() {
            Set<Point> next_horizontal = new HashSet<>();
            Set<Point> next_vertical = new HashSet<>();
            boolean state = false;
            for (var prev_point : horizontal) {
                var next_point = new Point((prev_point.x + 1) % width, prev_point.y);
                if (!horizontal.contains(next_point) && !vertical.contains(next_point)) {
                    next_horizontal.add(next_point);
                    state = true;
                } else {
                    next_horizontal.add(prev_point);
                }
            }
            horizontal.clear();
            horizontal.addAll(next_horizontal);
            for (var prev_point : vertical) {
                var next_point = new Point(prev_point.x, (prev_point.y + 1) % height);
                if (!horizontal.contains(next_point) && !vertical.contains(next_point)) {
                    next_vertical.add(next_point);
                    state = true;
                } else {
                    next_vertical.add(prev_point);
                }
            }
            vertical.clear();
            vertical.addAll(next_vertical);
            return state;
        }
    }

    private record Point(int x, int y) {

    }
}
