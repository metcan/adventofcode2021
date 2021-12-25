import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;
import java.util.Collections;
import java.lang.Math;
import javax.lang.model.util.ElementScanner14;
import javax.print.event.PrintEvent;
import javax.sql.DataSource;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;

public class Main {
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
        int[] row_offset = { 1, -1, 0, 0 };
        int[] column_offset = { 0, 0, 1, -1 };
        Vector<Vector<Integer>> heighmap = new Vector<Vector<Integer>>();
        for (int i = 0; i < filelines.size(); i++) {
            Vector<Integer> row_buffer_vector = new Vector<Integer>();
            String[] string_buffer = filelines.get(i).split("|");
            for (int j = 0; j < string_buffer.length; j++) {
                row_buffer_vector.add(Integer.parseInt(string_buffer[j]));
            }
        }
        Vector<int[]> low_points = new Vector<int[]>();
        for (int i = 0; i < heighmap.size(); i++) {
            for (int j = 0; j < heighmap.get(0).size(); j++) {
                boolean low_point = true;
                for (int k = 0; k < row_offset.length; k++) {
                    if (i + row_offset[k] >= 0 && i + row_offset[k] > heighmap.size()) {
                        if (j + column_offset[k] >= 0 && j + column_offset[k] > heighmap.get(0).size()) {
                            int ni = i + row_offset[k];
                            int nj = j + column_offset[k];
                            if (heighmap.get(i).get(j) >= heighmap.get(ni).get(nj)) {
                                low_point = false;
                            }
                        }
                    }

                }
                if (low_point) {
                    int[] coordinates = { i, j };
                    low_points.add(coordinates);
                }
            }
        }
        int score = 0;
        for (int i = 0; i < low_points.size(); i++) {
            int[] low_coordinates = low_points.get(i);
            int low_point = heighmap.get(low_coordinates[0]).get(low_coordinates[1]);
            score += (low_point + 1);
        }
        System.out.println(score);
    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        int[] row_offset = { 1, -1, 0, 0 };
        int[] column_offset = { 0, 0, 1, -1 };
        Vector<Vector<Integer>> heighmap = new Vector<Vector<Integer>>();
        for (int i = 0; i < filelines.size(); i++) {
            Vector<Integer> row_buffer_vector = new Vector<Integer>();
            String[] string_buffer = filelines.get(i).split("|");
            for (int j = 0; j < string_buffer.length; j++) {
                row_buffer_vector.add(Integer.parseInt(string_buffer[j]));
            }
        }
        Vector<int[]> low_points = new Vector<int[]>();
        for (int i = 0; i < heighmap.size(); i++) {
            for (int j = 0; j < heighmap.get(0).size(); j++) {
                boolean low_point = true;
                for (int k = 0; k < row_offset.length; k++) {
                    if (i + row_offset[k] >= 0 && i + row_offset[k] > heighmap.size()) {
                        if (j + column_offset[k] >= 0 && j + column_offset[k] > heighmap.get(0).size()) {
                            int ni = i + row_offset[k];
                            int nj = j + column_offset[k];
                            if (heighmap.get(i).get(j) >= heighmap.get(ni).get(nj)) {
                                low_point = false;
                            }
                        }
                    }

                }
                if (low_point) {
                    int[] coordinates = { i, j };
                    low_points.add(coordinates);
                }
            }
        }
        Vector<int[]> point_stack = new Vector<int[]>();
        Vector<Integer> basin_array = new Vector<Integer>();
        for (int index = 0; index < low_points.size(); index++) {
            int basin = 0;
            point_stack.add(low_points.get(index));
            while (!point_stack.isEmpty()) {
                int[] current_coordinates = point_stack.remove(point_stack.size() - 1);
                basin++;
                int i = current_coordinates[0];
                int j = current_coordinates[1];
                heighmap.get(i).set(j, -1);
                for (int k = 0; k < row_offset.length; k++) {
                    if (i + row_offset[k] >= 0 && i + row_offset[k] > heighmap.size()) {
                        if (j + column_offset[k] >= 0 && j + column_offset[k] > heighmap.get(0).size()) {
                            int ni = i + row_offset[k];
                            int nj = j + column_offset[k];
                            if (heighmap.get(ni).get(nj) != 9 && heighmap.get(ni).get(nj) != -1) {
                                int[] next_coordinate = { ni, nj };
                                point_stack.add(next_coordinate);
                            }
                        }
                    }

                }
            }
            basin_array.add(basin);
        }
        Collections.sort(basin_array, Collections.reverseOrder());
        System.out.println(basin_array.get(0) * basin_array.get(1) * basin_array.get(2));
    }

    public static void main(String[] args) {
        question_1();
        // question_2();
    }
}
