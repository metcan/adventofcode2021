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
        String[] start_characters = { "(", "[", "{", "<" };
        String[] end_characters = { ")", "]", "}", ">" };
        ArrayList<Long> scores = new ArrayList<Long>();
        for (int i = 0; i < filelines.size(); i++) {
            long line_score = 0;
            String[] single_line = filelines.get(i).split("|");
            Vector<String> match_stack = new Vector<String>();
            linecheck: for (int j = 0; j < single_line.length; j++) {
                for (int k = 0; k < start_characters.length; k++) {
                    if (start_characters[k].equals(single_line[j])) {
                        match_stack.add(end_characters[k]);
                        break;
                    }
                    if (end_characters[k].equals(single_line[j])) {
                        String end_character = match_stack.remove(match_stack.size() - 1);
                        if (!end_character.equals(single_line[j])) {
                            switch (end_character) {
                                case ")":
                                    line_score += 1;
                                    break linecheck;
                                case "]":
                                    line_score += 2;
                                    break linecheck;
                                case "}":
                                    line_score += 3;
                                    break linecheck;
                                case ">":
                                    line_score += 4;
                                    break linecheck;
                            }
                        }
                    }
                }

            }
        /*ArrayList<String> filelines = readFile("data.txt");
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
        for (int s = 0; s < step; s++) {
            for (int i = 0; i < array_2d.size(); i++) {
                for (int j = 0; j < array_2d.get(0).size(); j++) {
                    array_2d.get(i).set(j, array_2d.get(i).get(j) + 1);
                }
            }
            for (int i = 0; i < array_2d.size(); i++) {
                for (int j = 0; j < array_2d.get(0).size(); j++) {

                }
            }
        }*/

    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        String[] start_characters = { "(", "[", "{", "<" };
        String[] end_characters = { ")", "]", "}", ">" };
        ArrayList<Long> scores = new ArrayList<Long>();
        for (int i = 0; i < filelines.size(); i++) {
            long line_score = 0;
            String[] single_line = filelines.get(i).split("|");
            Vector<String> match_stack = new Vector<String>();
            linecheck: for (int j = 0; j < single_line.length; j++) {
                for (int k = 0; k < start_characters.length; k++) {
                    if (start_characters[k].equals(single_line[j])) {
                        match_stack.add(end_characters[k]);
                        break;
                    }
                    if (end_characters[k].equals(single_line[j])) {
                        String end_character = match_stack.remove(match_stack.size() - 1);
                        if (!end_character.equals(single_line[j])) {
                            match_stack.removeAllElements();
                            break linecheck;
                        }
                    }
                }

            }
            if (!match_stack.isEmpty()) {
                while (!match_stack.isEmpty()) {
                    String end_character = match_stack.remove(match_stack.size() - 1);
                    line_score = line_score * 5;
                    switch (end_character) {
                        case ")":
                            line_score += 1;
                            break;
                        case "]":
                            line_score += 2;
                            break;
                        case "}":
                            line_score += 3;
                            break;
                        case ">":
                            line_score += 4;
                            break;
                    }
                }
                scores.add(line_score);
            }

        }
        Collections.sort(scores);
        System.out.println(scores.get(scores.size() / 2));
    }

    public static void main(String[] args) {
        question_1();
        // question_2();
    }
}
