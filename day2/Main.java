import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class Main {
  static ArrayList<String> readFile(String file_name)
  {
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
  public static void main(String[] args) {
      ArrayList<String> filelines = readFile("data.txt");
      int horizontal_position = 0;
      int depth = 0;
      int aim = 0;
      for(int index=0; index<filelines.size() ; index++)
      {
        String[] action = filelines.get(index).split(" ");
        String movement = action[0];
        int amount = Integer.parseInt(action[1]);
        if (movement.equals("forward")){
          horizontal_position += amount;
          depth += aim * amount;
        }
        else if (movement.equals("down")){
          aim += amount;
        }
        else if (movement.equals("up")){
          aim -= amount;
        }
      }
      System.out.println("Final Horizontal Position = " + horizontal_position);
      System.out.println("Final depth = " + depth);
      System.out.println("Final aim = " + aim);
      System.out.println("Result = " + (depth *horizontal_position));

  }
}

