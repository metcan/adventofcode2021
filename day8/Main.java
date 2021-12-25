import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;
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
        int pattern_counter = 0;
        for (int i = 0; i < filelines.size(); i++) {
            String buffer = filelines.get(i);
            String[] buffer_array = buffer.split("[|]+");
            for (int j = 1; j < buffer_array.length; j++) {
                String[] patters = buffer_array[j].split(" ");
                for (int k = 0; k < patters.length; k++) {
                    if (Arrays.asList(2, 3, 4, 7).contains(patters[k].length())) {
                        pattern_counter += 1;
                    }
                }
            }

        }
        System.out.println(pattern_counter);
    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        int sum = 0;
        for (int i = 0; i < filelines.size(); i++) {
            String single_line_record = filelines.get(i);
            String[] buffer_array = single_line_record.split("[|]+");
            // decoding part
            String[] patterns = buffer_array[0].split(" ");
            List<String> patterns_list = Arrays.asList(patterns);
            patterns_list.sort(Comparator.comparingInt(String::length));
            List<List<String>> numbers = new ArrayList<List<String>>();
            for (int j = 0; j < 10; j++) {
                List<String> dummy_list = new ArrayList<String>();
                numbers.add(dummy_list);
            }
            numbers.set(1, Arrays.asList(patterns_list.get(0).split("|")));
            numbers.set(4, Arrays.asList(patterns_list.get(2).split("|")));
            numbers.set(7, Arrays.asList(patterns_list.get(1).split("|")));
            numbers.set(8, Arrays.asList(patterns_list.get(9).split("|")));
            List<String> AAAA = new ArrayList<>(numbers.get(7));
            AAAA.removeAll(numbers.get(1));
            List<String> BBDD = new ArrayList<>(numbers.get(4));
            BBDD.removeAll(numbers.get(1));
            // find nine
            for (String pattern : patterns_list) {
                if (pattern.length() == 6) {
                    List<String> buffer = Arrays.asList(pattern.split("|"));
                    if (buffer.containsAll(numbers.get(4))) {
                        numbers.set(9, buffer);
                    } else if (buffer.containsAll(BBDD)) {
                        numbers.set(6, buffer);
                    } else {
                        numbers.set(0, buffer);
                    }

                }
                if (pattern.length() == 5) {
                    List<String> buffer = Arrays.asList(pattern.split("|"));
                    if (buffer.containsAll(numbers.get(1))) {
                        numbers.set(3, buffer);
                    } else if (buffer.containsAll(BBDD)) {
                        numbers.set(5, buffer);
                    } else {
                        numbers.set(2, buffer);

                    }
                }
            }
            // solution part
            String partial_sum = new String();
            String[] decoding_patterns = buffer_array[1].split(" ");
            for (int j = 0; j < decoding_patterns.length; j++) {
                String[] dummy_string = decoding_patterns[j].split(" ");
                List<String> single_pattern = Arrays.asList(dummy_string[0].split("|"));
                for (int k = 0; k < numbers.size(); k++) {
                    if (numbers.get(k).containsAll(single_pattern) && numbers.get(k).size() == single_pattern.size()) {
                        partial_sum = partial_sum + k;
                    }
                }
            }
            sum += Integer.parseInt(partial_sum);
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        // question_1();
        question_2();
    }
}
