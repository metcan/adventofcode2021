import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.nio.Buffer;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.lang.model.util.ElementScanner14;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import java.util.Map;
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
        ArrayList<String> filelines = readFile("test_set.txt");
        Map<String, String> pair_insertion = new HashMap<String, String>();
        String[] start_sequence = { "A", "B" };
        for (int i = 0; i < filelines.size(); i++) {
            if (i == 0)
                start_sequence = filelines.get(i).split("|");
            else {
                if (!filelines.get(i).equals("")) {
                    String[] sequence = filelines.get(i).split(" -> ");
                    pair_insertion.put(sequence[0], sequence[1]);
                }
            }
        }
        int step = 40;
        for (int i = 0; i < step; i++) {
            ArrayList<String> next_sequence = new ArrayList<String>();
            for (int j = 0; j < start_sequence.length - 1; j++) {
                String sequence = start_sequence[j] + start_sequence[j + 1];
                next_sequence.add(start_sequence[j]);
                next_sequence.add(pair_insertion.get(sequence));
            }
            next_sequence.add(start_sequence[start_sequence.length - 1]);
            start_sequence = next_sequence.toArray(start_sequence);
        }
        Map<String, Long> character_counter = new HashMap<String, Long>();
        Long low_key_count = 8142736692756L;
        Long high_key_count = Long.valueOf(0);
        for (int i = 0; i < start_sequence.length; i++) {
            if (character_counter.containsKey(start_sequence[i])) {
                character_counter.put(start_sequence[i], character_counter.get(start_sequence[i]) + 1);
            } else {
                Long temp = Long.valueOf(1);
                character_counter.put(start_sequence[i], temp);
            }
        }
        for (Map.Entry<String, Long> set : character_counter.entrySet()) {
            if (set.getValue() > high_key_count)
                high_key_count = set.getValue();
            if (set.getValue() < low_key_count)
                low_key_count = set.getValue();
        }
        System.out.println(high_key_count - low_key_count);

    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        Map<String, String> pair_insertion = new HashMap<String, String>();
        Map<String, String[]> pair_generator_seq = new HashMap<String, String[]>();
        Map<String, Long> pair_count = new HashMap<String, Long>();

        String[] start_sequence = { "A", "B" };
        for (int i = 0; i < filelines.size(); i++) {
            if (i == 0)
                start_sequence = filelines.get(i).split("|");
            else {
                if (!filelines.get(i).equals("")) {
                    String[] sequence = filelines.get(i).split(" -> ");
                    pair_insertion.put(sequence[0], sequence[1]);
                    pair_count.put(sequence[0], Long.valueOf(0));
                }
            }
        }
        // start_sequence = next_sequence.toArray(start_sequence);
        for (Map.Entry<String, String> set : pair_insertion.entrySet()) {
            String key = set.getKey();
            String val = set.getValue();
            String[] new_seq = { key.charAt(0) + val, val + key.charAt(1) };
            pair_generator_seq.put(key, new_seq);
        }
        String start_character = start_sequence[0];
        String end_character = start_sequence[start_sequence.length - 1];
        int step = 40;
        for (int j = 0; j < start_sequence.length - 1; j++) {
            String sequence = start_sequence[j] + start_sequence[j + 1];
            pair_count.put(sequence, pair_count.get(sequence) + 1);
        }
        for (int i = 0; i < step; i++) {
            Map<String, Long> pair_count_buffer = new HashMap<String, Long>();
            for (Map.Entry<String, Long> set : pair_count.entrySet()) {
                pair_count_buffer.put(set.getKey(), Long.valueOf(0));
            }
            for (Map.Entry<String, Long> set : pair_count.entrySet()) {
                String[] string_array = pair_generator_seq.get(set.getKey());
                if (pair_count_buffer.containsKey(string_array[0])) {
                    pair_count_buffer.put(string_array[0], pair_count_buffer.get(string_array[0]) + set.getValue());
                } else {
                    pair_count_buffer.put(string_array[0], set.getValue());
                }
                if (pair_count_buffer.containsKey(string_array[1])) {
                    pair_count_buffer.put(string_array[1], pair_count_buffer.get(string_array[1]) + set.getValue());
                } else {
                    pair_count_buffer.put(string_array[1], set.getValue());
                }
            }
            for (Map.Entry<String, Long> set : pair_count_buffer.entrySet()) {
                pair_count.put(set.getKey(), set.getValue());
            }
        }
        System.out.println(pair_count);
        Map<Character, Long> character_counter = new HashMap<Character, Long>();
        Long low_key_count = 8142736692756L;
        Long high_key_count = Long.valueOf(0);
        for (Map.Entry<String, Long> set : pair_count.entrySet()) {
            char[] chars = set.getKey().toCharArray();
            for (char ch : chars) {
                if (character_counter.containsKey(ch)) {
                    character_counter.put(ch, character_counter.get(ch) + Long.valueOf(set.getValue()));
                } else {
                    character_counter.put(ch, Long.valueOf(set.getValue()));
                }
            }
        }
        character_counter.put(start_character.charAt(0), character_counter.get(start_character.charAt(0)) + 1);
        character_counter.put(end_character.charAt(0), character_counter.get(end_character.charAt(0)) + 1);
        for (Map.Entry<Character, Long> set : character_counter.entrySet()) {
            if (set.getValue() > high_key_count)
                high_key_count = set.getValue();
            if (set.getValue() < low_key_count)
                low_key_count = set.getValue();
        }
        System.out.println((high_key_count - low_key_count) / 2);
    }

    public static void main(String[] args) {
        // question_1();
        question_2();
    }
}
