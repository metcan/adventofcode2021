import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

class first_day {
    static void first_question() {
        try {
            File myObj = new File("data.txt");
            Scanner myReader = new Scanner(myObj);
            int current_depth = -1;
            int next_depth = -1;
            int total_increase = 0;
            int total_decrease = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (current_depth == -1) {
                    current_depth = Integer.parseInt(data);
                } else {
                    next_depth = Integer.parseInt(data);
                    if (current_depth > next_depth) {
                        total_decrease += 1;
                    } else if (current_depth < next_depth) {
                        total_increase += 1;
                    } else {

                    }
                    current_depth = next_depth;
                }

            }

            System.out.println(String.format("Total increase %d", total_increase));
            System.out.println(String.format("Total decrease %d", total_decrease));

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static int window_sum(int[] array) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += array[i];
        }
        return sum;

    }

    static void second_question() {
        try {
            File myObj = new File("data.txt");
            Scanner myReader = new Scanner(myObj);
            int current_sum = -1;
            int previous_sum = -1;
            int total_increase = 0;
            int total_decrease = 0;
            int[] depth_window = { -1, -1, -1 };
            int index = 0;
            while (myReader.hasNextLine()) {
                String reading = myReader.nextLine();
                int current_depth = Integer.parseInt(reading);
                depth_window[index] = current_depth;
                index += 1;
                if (previous_sum == -1) {
                    if (index == 3) {
                        previous_sum = window_sum(depth_window);
                    }

                } else {
                    current_sum = window_sum(depth_window);
                    if (current_sum > previous_sum) {
                        total_increase += 1;
                    } else if (current_sum < previous_sum) {
                        total_decrease += 1;
                    }
                    previous_sum = current_sum;
                }
                index %= 3;

            }

            System.out.println(String.format("Total increase %d", total_increase));
            System.out.println(String.format("Total decrease %d", total_decrease));

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        second_question();
    }
}
