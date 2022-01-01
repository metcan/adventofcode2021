import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.nio.Buffer;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.random.RandomGenerator;
import java.util.regex.*;
import java.lang.Math;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.List;

public class Main {

    static List<Long> find_divisors(long destination_y) {
        List<Long> divisor_steps = new ArrayList<Long>();
        for (long i = 1; i <= destination_y; i++) {
            if (destination_y % i == 0) {
                divisor_steps.add(i);
            }
        }
        return divisor_steps;
    }

    static List<Long[]> generate_steps_for_y(long destination_y) {
        long buf = 2 * destination_y; // 2 y/ n = 2 y1 - n^2
        List<Long> possible_step_list = find_divisors(Math.abs(buf));
        List<Long[]> start_y_list = new ArrayList<Long[]>();
        for (long i : possible_step_list) {
            long vel_y = (buf / i) + i - 1; // discrete time derivative
            if (vel_y % 2 == 0) {
                Long[] data = { vel_y / 2, i };
                start_y_list.add(data);
            }
        }
        return start_y_list;
    }

    static List<Long> check_possible_for_x(Long step, long[] x_range) {
        List<Long> checked_x = new ArrayList<Long>();
        Long x_min = Long.valueOf(x_range[0]);
        Long x_max = Long.valueOf(x_range[1]);
        // Two possible situations, we have velocity at end or not.
        // If step < speed , find total distance that ball goes and find int divider
        Long drag_distance = (step * (step - 1)) / 2;
        for (Long i = drag_distance + x_min; i <= drag_distance + x_max; i++) {
            if (i % step == 0) {
                Long x_vel = i / step;
                if (x_vel >= step)
                    checked_x.add(x_vel);
            }
        }
        // For velocities zeros before last step. iterate for lower steps
        for (Long small_steps = Long.valueOf(1); small_steps < step; small_steps++) {
            Long small_drag_distance = (small_steps * (small_steps + 1)) / 2;
            if (small_drag_distance <= x_max && x_min <= small_drag_distance) {
                Long x_vel = small_steps;
                if (x_vel < step)
                    checked_x.add(x_vel);
            }
        }
        return checked_x;
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

    static void question_1() {
        ArrayList<String> filelines = readFile("data.txt");
        String line = filelines.get(0);
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile(".[xy]=-?\\w*..-?\\w*")
                .matcher(line);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        int[] x_coordinate_range = new int[2];
        int[] y_coordinate_range = new int[2];
        for (String range : allMatches) {
            if (range.charAt(1) == 'x') {
                x_coordinate_range = Arrays.stream(range.substring(3).split("\\.\\.")).mapToInt(Integer::parseInt)
                        .toArray();
            } else if (range.charAt(1) == 'y') {
                y_coordinate_range = Arrays.stream(range.substring(3).split("\\.\\.")).mapToInt(Integer::parseInt)
                        .toArray();
            }
        }
        int max_speed = y_coordinate_range[0];
        System.out.println(max_speed * (max_speed + 1) / 2);

    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        String line = filelines.get(0);
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile(".[xy]=-?\\w*..-?\\w*")
                .matcher(line);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        long[] x_coordinate_range = new long[2];
        long[] y_coordinate_range = new long[2];
        for (String range : allMatches) {
            if (range.charAt(1) == 'x') {
                x_coordinate_range = Arrays.stream(range.substring(3).split("\\.\\.")).mapToLong(Long::parseLong)
                        .toArray();
            } else if (range.charAt(1) == 'y') {
                y_coordinate_range = Arrays.stream(range.substring(3).split("\\.\\.")).mapToLong(Long::parseLong)
                        .toArray();
            }
        }
        ArrayList<Long[]> velocities_set = new ArrayList<Long[]>();
        for (long y = y_coordinate_range[0]; y <= y_coordinate_range[1]; y++) {
            List<Long[]> test_y = generate_steps_for_y(Long.valueOf(y));
            for (Long[] possible_y : test_y) {
                List<Long> possible_x = new ArrayList<Long>();
                possible_x = check_possible_for_x(possible_y[1], x_coordinate_range);
                for (Long i : possible_x) {
                    Long[] initial_vel_set = { i, Long.valueOf(possible_y[0]) };
                    if (!velocities_set.contains(initial_vel_set))
                        velocities_set.add(initial_vel_set);
                }
            }
        }
        ArrayList<Long[]> unique_coordinates = new ArrayList<Long[]>();
        unique_coordinates.add(velocities_set.get(0));
        for (Long[] i : velocities_set) {
            boolean no_match = true;
            for (Long[] j : unique_coordinates) {
                if (j[0] == i[0] && j[1] == i[1]) {
                    no_match = false;
                }
            }
            if (no_match) {
                unique_coordinates.add(i);
            }

        }
        System.out.println(unique_coordinates.size());
        int print = 0;
        for (Long[] i : unique_coordinates) {
            System.out.print(i[0] + "," + i[1] + "  ");
            print += 1;
            if (print == 10) {
                System.out.println("");
                print = 0;
            }
        }
        System.out.println("");

    }

    public static void main(String[] args) {
        // question_1();
        question_2();
    }
}
