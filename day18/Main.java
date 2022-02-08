import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;
import java.util.stream.*;
import java.util.List;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Collections;

public class Main {
    static class Box {
        private Box parent;
        private Box right;
        private Box left;
        private int value;

        public Box(int value) {
            this.value = value;
        }

        public Box(Box parent, int value) {
            this.parent = parent;
            this.value = value;
        }

        public Box(Box left, Box right) {
            this.parent = null;
            this.left = left;
            this.right = right;
            left.parent = this;
            right.parent = this;
        }

        public Box deepcopy() {
            Box box = new Box(this.value);
            if (left != null) {
                box.left = left.deepcopy();
                box.left.parent = box;
            }
            if (right != null) {
                box.right = right.deepcopy();
                box.right.parent = box;
            }
            return box;
        }

        void reduce() {
            while (true) {
                if (!explode() && !split()) {
                    break;
                }
            }
        }

        boolean explode() {
            return explode(0);
        }

        boolean explode(int depth) {
            if (isleaf())
                return false;
            else if (depth >= 4 && isleafPair()) {
                var left_adj = leftadjacent();
                var right_adj = rightadjacent();
                if (left_adj != null) {
                    left_adj.value += left.value;
                }
                if (right_adj != null) {
                    right_adj.value += right.value;
                }
                left = null;
                right = null;
                value = 0;
                return true;
            } else {
                return left.explode(depth + 1) || right.explode(depth + 1);
            }

        }

        boolean split() {
            if (isleaf() && value >= 10) {
                var left = new Box(this, value / 2);
                var right = new Box(this, value - left.value);
                this.left = left;
                this.right = right;
                this.value = 0;
                return true;
            } else if (isleaf() && value < 10) {
                return false;
            } else {
                return left.split() || right.split();
            }

        }

        Box rightadjacent() {
            if (parent == null) {
                return null;
            } else if (parent.left == this) {
                var box = parent.right;
                while (!box.isleaf()) {
                    box = box.left;
                }
                return box;

            } else {
                return parent.rightadjacent();
            }
        }

        Box leftadjacent() {
            if (parent == null) {
                return null;
            } else if (parent.right == this) {
                var box = parent.left;
                while (!box.isleaf()) {
                    box = box.right;
                }
                return box;

            } else {
                return parent.leftadjacent();
            }
        }

        private boolean isleaf() {
            return left == null && right == null;
        }

        private boolean isleafPair() {
            return left.isleaf() && right.isleaf();
        }

        @Override
        public String toString() {
            if (isleaf()) {
                return String.valueOf(value);
            } else {
                return String.format("[%s,%s]", left, right);
            }
        }
    }

    static ArrayList<String> readFile(String file_name) {
        ArrayList<String> file_lines = new ArrayList<>();
        try {
            File myObj = new File(file_name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                file_lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return file_lines;
    }

    public static Box parse(String input) {
        var output = new Stack<Box>();
        var stack = new Stack<Character>();
        CharacterIterator it = new StringCharacterIterator(input);
        while (it.current() != CharacterIterator.DONE) {
            var token = it.current();
            switch (token) {
                case '[' -> stack.push(token);
                case ']' -> {
                    while (!stack.peek().equals('[')) {
                        var right = output.pop();
                        var left = output.pop();
                        output.push(new Box(left, right));
                        stack.pop();
                    }
                    stack.pop();
                }
                case ',' -> {
                    stack.push(token);
                }
                default -> output.push(new Box(Integer.parseInt(String.valueOf(token))));
            }
            it.next();
        }
        return output.pop();

    }

    static Box sum(Box... boxes) {
        var result = boxes[0];
        result.reduce();
        for (int i = 1; i < boxes.length; i++) {
            result = new Box(result, boxes[i]);
            result.reduce();
        }
        return result;
    }

    static int magnitude(Box box) {
        if (box.isleaf()) {
            return box.value;
        } else {
            return magnitude(box.left) * 3 + magnitude(box.right) * 2;
        }

    }

    static void question_1() {
        List<String> filelines = readFile("data.txt");
        var boxes = filelines.stream().map(Main::parse).toArray(Box[]::new);
        var box_sum = sum(boxes);
        System.out.println(magnitude(box_sum));

    }

    static void question_2() {
        List<String> filelines = readFile("data.txt");
        var boxes = filelines.stream().map(Main::parse).toArray(Box[]::new);
        ArrayList<Integer> pair_sums = new ArrayList<>();
        for (int i = 0; i < boxes.length; i++) {
            for (int j = i + 1; j < boxes.length; j++) {
                pair_sums.add(Integer.valueOf(magnitude(sum(boxes[i].deepcopy(), boxes[j].deepcopy()))));
                pair_sums.add(Integer.valueOf(magnitude(sum(boxes[j].deepcopy(), boxes[i].deepcopy()))));
            }
        }
        Collections.sort(pair_sums);
        System.out.println(pair_sums.get(pair_sums.size() - 1));
    }

    public static void main(String[] args) {
        question_1();
        question_2();
    }
}
