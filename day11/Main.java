import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;
import java.util.Collections;
import java.lang.Math;
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
        Vector<Vector<Integer>> array_2d = new Vector<Vector<Integer>>();
        int[] blast_area_i = { 1, 1, 1, 0, 0, -1, -1, -1 };
        int[] blast_area_j = { 1, 0, -1, 1, -1, 1, 0, -1 };

        for (int i = 0; i < filelines.size(); i++) {
            Vector<Integer> buffer_array = new Vector<Integer>();
            String[] buffer_string = filelines.get(i).split("|");
            for (int j = 0; j < buffer_string.length; j++) {
                buffer_array.add(Integer.parseInt(buffer_string[j]));
            }
            array_2d.add(buffer_array);
        }
        int step = 100;
        int blast_counter = 0;
        for (int s = 0; s < step; s++) {
            for (int i = 0; i < array_2d.size(); i++) {
                for (int j = 0; j < array_2d.get(0).size(); j++) {
                    if (array_2d.get(i).get(j) == -1) {
                        array_2d.get(i).set(j, 1);
                    } else {
                        array_2d.get(i).set(j, array_2d.get(i).get(j) + 1);
                    }
                }
            }
            Vector<int[]> point_stack = new Vector<int[]>();
            for (int i = 0; i < array_2d.size(); i++) {
                for (int j = 0; j < array_2d.get(0).size(); j++) {
                    if (array_2d.get(i).get(j) == 10) {
                        int[] new_point = { i, j };
                        point_stack.add(new_point);
                        while (!point_stack.isEmpty()) {

                            int[] current_point = point_stack.remove(point_stack.size() - 1);
                            if (array_2d.get(current_point[0]).get(current_point[1]) == -1) {
                                continue;
                            } else {
                                array_2d.get(current_point[0]).set(current_point[1], -1);
                                blast_counter++;
                            }
                            for (int k = 0; k < blast_area_i.length; k++) {
                                int ni = current_point[0] + blast_area_i[k];
                                int nj = current_point[1] + blast_area_j[k];
                                if (ni >= 0 && ni < array_2d.size()) {
                                    if (nj >= 0 && nj < array_2d.get(0).size()) {
                                        if (array_2d.get(ni).get(nj) != 10 && array_2d.get(ni).get(nj) != -1) {
                                            array_2d.get(ni).set(nj, array_2d.get(ni).get(nj) + 1);
                                        }
                                        if (array_2d.get(ni).get(nj) == 10) {
                                            int[] next_point = { ni, nj };
                                            point_stack.add(next_point);

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        System.out.println(blast_counter);

    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        Vector<Vector<Integer>> array_2d = new Vector<Vector<Integer>>();
        int[] blast_area_i = { 1, 1, 1, 0, 0, -1, -1, -1 };
        int[] blast_area_j = { 1, 0, -1, 1, -1, 1, 0, -1 };

        for (int i = 0; i < filelines.size(); i++) {
            Vector<Integer> buffer_array = new Vector<Integer>();
            String[] buffer_string = filelines.get(i).split("|");
            for (int j = 0; j < buffer_string.length; j++) {
                buffer_array.add(Integer.parseInt(buffer_string[j]));
            }
            array_2d.add(buffer_array);
        }
        int step = 5000;
        int blast_counter = 0;
        for (int s = 0; s < step; s++) {
            int step_blast_counter = 0;
            for (int i = 0; i < array_2d.size(); i++) {
                for (int j = 0; j < array_2d.get(0).size(); j++) {
                    if (array_2d.get(i).get(j) == -1) {
                        array_2d.get(i).set(j, 1);
                    } else {
                        array_2d.get(i).set(j, array_2d.get(i).get(j) + 1);
                    }
                }
            }
            Vector<int[]> point_stack = new Vector<int[]>();
            for (int i = 0; i < array_2d.size(); i++) {
                for (int j = 0; j < array_2d.get(0).size(); j++) {
                    if (array_2d.get(i).get(j) == 10) {
                        int[] new_point = { i, j };
                        point_stack.add(new_point);
                        while (!point_stack.isEmpty()) {

                            int[] current_point = point_stack.remove(point_stack.size() - 1);
                            if (array_2d.get(current_point[0]).get(current_point[1]) == -1) {
                                continue;
                            } else {
                                array_2d.get(current_point[0]).set(current_point[1], -1);
                                blast_counter++;
                                step_blast_counter++;
                            }
                            for (int k = 0; k < blast_area_i.length; k++) {
                                int ni = current_point[0] + blast_area_i[k];
                                int nj = current_point[1] + blast_area_j[k];
                                if (ni >= 0 && ni < array_2d.size()) {
                                    if (nj >= 0 && nj < array_2d.get(0).size()) {
                                        if (array_2d.get(ni).get(nj) != 10 && array_2d.get(ni).get(nj) != -1) {
                                            array_2d.get(ni).set(nj, array_2d.get(ni).get(nj) + 1);
                                        }
                                        if (array_2d.get(ni).get(nj) == 10) {
                                            int[] next_point = { ni, nj };
                                            point_stack.add(next_point);

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
            if (step_blast_counter == array_2d.size() * array_2d.get(0).size()) {
                System.out.println(s);
                return;
            }
        }
    }

    public static void main(String[] args) {
        // question_1();
        question_2();
    }
}
