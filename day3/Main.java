import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.RuntimeException;



public class Main {
  public static final int BIT_LENGHT = 12;
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
    int bitcounter[] = new int[BIT_LENGHT];
    Arrays.fill(bitcounter, 0);
    int gamma_rate = 0;
    int epsilon_rate = 0;
    int total_readings = filelines.size();
    for(int index=0; index<filelines.size() ; index++)
    {
      String bitstream = filelines.get(index);
      for(int i=0; i<bitstream.length(); i++)
      {
        if (bitstream.charAt(i) == '1')
        {
        bitcounter[i] += 1;
        }
      }
    }
    for(int i=0; i<bitcounter.length; i++)
    {
      if (bitcounter[i] > total_readings/2)
      {
        gamma_rate += 1<<(bitcounter.length - i - 1);
      }
      else
      {
        epsilon_rate += 1<<(bitcounter.length - i - 1);
      }
    }
    System.out.println("Gamma Rate = "+ gamma_rate);
    System.out.println("Epsilon Rate = " + epsilon_rate);
    System.out.println("Total Power Consumption " + (gamma_rate * epsilon_rate));

  }
  static void question_2()
  {
    ArrayList<String> filelines = readFile("data.txt");
    int bitcounter[] = new int[BIT_LENGHT];
    Arrays.fill(bitcounter, 0);
    int oxygen_generator_rating = 0;
    int co2_crubber_rating = 0;
    int total_readings = filelines.size();
    for(int index=0; index<filelines.size() ; index++)
    {
      String bitstream = filelines.get(index);
      for(int i=0; i<bitstream.length(); i++)
      {
        if (bitstream.charAt(i) == '1')
        {
        bitcounter[i] += 1;
        }
      } 
      
    }
    for (int i=0; i<bitcounter.length; i++)
    {
      if (bitcounter[i] > total_readings/2)
      bitcounter[i] = 1;
      else
      bitcounter[i] = 0;
    }

    String oxygen_string = Integer.toString(bitcounter[0]);
    String co2_string = Integer.toString(bitcounter[0]^1);
    if (bitcounter[0] == 1)
      oxygen_generator_rating += 1<<(bitcounter.length - 1);
    else
      co2_crubber_rating += 1<<(bitcounter.length - 1);

    for(int i=1; i<bitcounter.length; i++)
    {
      int oxygen_bit_counter = 0;
      int oxygen_bit_total_entry_count = 0;
      int co2_bit_counter = 0; 
      int co2_bit_total_entry_count = 0;
      for (int index=0; index<filelines.size(); index++)
      {
        String bitstream = filelines.get(index);

        if (bitstream.startsWith(oxygen_string))
        {
          if (bitstream.charAt(i) == '1')
          {
            oxygen_bit_counter += 1;
          }
          oxygen_bit_total_entry_count += 1;
        }
        if (bitstream.startsWith(co2_string))
        {
          if (bitstream.charAt(i) == '1')
          {
            co2_bit_counter += 1;
          }
          co2_bit_total_entry_count += 1;
        }
      }
      if(oxygen_bit_total_entry_count == 1) {
        if (oxygen_bit_counter == 1)
        {
          oxygen_generator_rating += 1<<(bitcounter.length - i - 1);
        }
      }
      else {
        if (oxygen_bit_counter >= (oxygen_bit_total_entry_count- oxygen_bit_counter))
        {
          oxygen_generator_rating += 1<<(bitcounter.length - i - 1);
          oxygen_bit_counter = 1; 
        }
        else{
          oxygen_bit_counter = 0;
        }
      }
      
      if (co2_bit_total_entry_count == 1)
      {
        if (co2_bit_counter == 1)
        {
          co2_crubber_rating += 1<<(bitcounter.length - i - 1);
        }
      }
      else{
        if (co2_bit_counter < (co2_bit_total_entry_count - co2_bit_counter))
        {
          co2_crubber_rating += 1<<(bitcounter.length - i - 1);
          co2_bit_counter = 1;
        }
        else{
          co2_bit_counter = 0;
        }
    }
      oxygen_string = oxygen_string + oxygen_bit_counter;
      co2_string = co2_string + co2_bit_counter;
    }
    System.out.println("Oxygen Generator Rating Rate = "+ oxygen_generator_rating);
    System.out.println("CO2 Crubber Rating = " + co2_crubber_rating);
    System.out.println("Total Power Consumption " + (co2_crubber_rating * oxygen_generator_rating));

  }
  public static void main(String[] args) {
    //question_1();
    question_2();
  }
}

