import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;
import java.lang.Math;
import javax.lang.model.util.ElementScanner14;
import javax.print.event.PrintEvent;
import javax.sql.DataSource;

import java.util.Arrays;




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
  static void question_1()
  {
    ArrayList<String> filelines = readFile("data.txt");
    ArrayList<Integer> lanternfish_list = new ArrayList<Integer>();
    String[] str_day_count = filelines.get(0).split(",");
    for(int i=0; i<str_day_count.length; i++)
    {
      lanternfish_list.add(Integer.parseInt(str_day_count[i]));
    }
    
    int days = 80; // after 18 day = 26, after 80 day = 5934
    for (int i = 0; i<days; i++)
    {
      //System.out.println(lanternfish_list);
      int current_lenght = lanternfish_list.size();
      for(int j= 0; j<current_lenght ; j++)
      {
        if(lanternfish_list.get(j).equals(0))
        {
          lanternfish_list.add(8);
          lanternfish_list.set(j, 6);
        }
        else
        {
        lanternfish_list.set(j, lanternfish_list.get(j) - 1);
        }

      }
    }
    System.out.println(lanternfish_list);
    System.out.println(lanternfish_list.size());
  }

  static void question_2()
  {
    ArrayList<String> filelines = readFile("data.txt");
    String[] str_day_count = filelines.get(0).split(",");
    Long[] fish_array = new Long[9];
    long value = 0;
    Arrays.fill(fish_array, value);
    int days = 256;
    for(int i=0; i<str_day_count.length; i++)
    {
      fish_array[Integer.parseInt(str_day_count[i])] += 1;
    }
    for(int i=0; i<days; i++)
    {
      Long temp = fish_array[0];
      for(int j=0; j<8; j++)
      {
        fish_array[j] = fish_array[j+1];
      }
      fish_array[6] += temp;
      fish_array[8] = temp;
    }
    long sum = 0;
    for(int j=0; j<9; j++)
    {
      sum += fish_array[j];
    }
    System.out.println(sum);
  }
  public static void main(String[] args) {
    //question_1();
    question_2();
  }
}

