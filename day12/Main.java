import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;
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

    static int travel_paths(Map<String, ArrayList<String>> graph, Map<String, Boolean> small_caves, String from,
            String to, List<String> local_path) {
        if (from.equals(to)) {
            return 1;
        } else {
            int total_path = 0;
            if (graph.containsKey(from)) {
                for (String i : graph.get(from)) {
                    if (!(small_caves.containsKey(i) && small_caves.get(i))) {
                        local_path.add(i);
                        if (small_caves.containsKey(i))
                            small_caves.put(i, true);
                        System.out.println(local_path);
                        total_path += travel_paths(graph, small_caves, i, to, local_path);
                        if (small_caves.containsKey(i))
                            small_caves.put(i, false);
                        local_path.remove(i);
                    }
                }
            } else {
                return total_path;
            }
            return total_path;
        }
    }

    static int travel_paths_2(Map<String, ArrayList<String>> graph, Map<String, Integer> small_caves, String from,
            String to, List<String> local_path) {
        if (from.equals(to)) {
            System.out.println(local_path);
            return 1;
        } else {
            int total_path = 0;
            for (String i : graph.get(from)) {
                int visit_limit = 2;
                if (small_caves.containsKey(i)) {
                    /*
                     * for (Map.Entry<String, Integer> set : small_caves.entrySet()) {
                     * if (set.getValue() == 2 && set.getKey() != i) {
                     * visit_limit = 1;
                     * }
                     * }
                     */
                }
                if ((small_caves.containsKey(i) && small_caves.get(i) >= visit_limit)) {
                    return total_path;
                }
                local_path.add(i);
                if (small_caves.containsKey(i))
                    small_caves.put(i, small_caves.get(i) + 1);
                System.out.println(local_path);
                total_path += travel_paths_2(graph, small_caves, i, to, local_path);
                if (small_caves.containsKey(i))
                    small_caves.put(i, small_caves.get(i) - 1);
                local_path.remove(local_path.size() - 1);

            }
            return total_path;
        }
    }

    static void question_1() {
        ArrayList<String> filelines = readFile("data.txt");
        Map<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
        Map<String, Boolean> small_caves = new HashMap<String, Boolean>();
        for (int i = 0; i < filelines.size(); i++) {
            String[] buffer = filelines.get(i).split("-");
            if (Character.isLowerCase(buffer[0].charAt(0))) {
                small_caves.put(buffer[0], false);
            }
            if (graph.containsKey(buffer[0])) {
                ArrayList<String> bufferarray = graph.get(buffer[0]);
                bufferarray.add(buffer[1]);
                graph.put(buffer[0], bufferarray);
            } else {
                ArrayList<String> bufferarray = new ArrayList<String>();
                bufferarray.add(buffer[1]);
                graph.put(buffer[0], bufferarray);
            }
            if (graph.containsKey(buffer[1])) {
                ArrayList<String> bufferarray = graph.get(buffer[1]);
                bufferarray.add(buffer[0]);
                graph.put(buffer[1], bufferarray);
            } else {
                ArrayList<String> bufferarray = new ArrayList<String>();
                bufferarray.add(buffer[0]);
                graph.put(buffer[1], bufferarray);
            }
        }
        System.out.println(graph);
        String start_check = "start";
        String end_check = "end";
        List<String> travel_list = new ArrayList<String>();
        travel_list.add(start_check);
        small_caves.put(start_check, true);
        int test = travel_paths(graph, small_caves, start_check, end_check, travel_list);
        System.out.println(test);

    }

    static void question_2() {
        ArrayList<String> filelines = readFile("test_set_3.txt");
        Map<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
        Map<String, Integer> small_caves = new HashMap<String, Integer>();
        for (int i = 0; i < filelines.size(); i++) {
            String[] buffer = filelines.get(i).split("-");
            if (Character.isLowerCase(buffer[0].charAt(0))) {
                small_caves.put(buffer[0], 0);
            }
            if (Character.isLowerCase(buffer[1].charAt(0))) {
                small_caves.put(buffer[1], 0);
            }
            if (graph.containsKey(buffer[0])) {
                ArrayList<String> bufferarray = graph.get(buffer[0]);
                bufferarray.add(buffer[1]);
                graph.put(buffer[0], bufferarray);
            } else {
                ArrayList<String> bufferarray = new ArrayList<String>();
                bufferarray.add(buffer[1]);
                graph.put(buffer[0], bufferarray);
            }
            if (graph.containsKey(buffer[1])) {
                ArrayList<String> bufferarray = graph.get(buffer[1]);
                bufferarray.add(buffer[0]);
                graph.put(buffer[1], bufferarray);
            } else {
                ArrayList<String> bufferarray = new ArrayList<String>();
                bufferarray.add(buffer[0]);
                graph.put(buffer[1], bufferarray);
            }
        }
        System.out.println(graph);
        String start_check = "start";
        String end_check = "end";
        List<String> travel_list = new ArrayList<String>();
        travel_list.add(start_check);
        small_caves.put(start_check, 2);
        int test = travel_paths_2(graph, small_caves, start_check, end_check, travel_list);
        System.out.println(test);
    }

    public static void main(String[] args) {
        // question_1();
        question_2();
    }
}
