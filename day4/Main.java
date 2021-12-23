import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.RuntimeException;
import java.lang.reflect.Array;
import java.util.Vector;



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
    int bingo_array[] = new int[1];
    Vector<Vector<Vector<Integer>>>  bingo_matrix_array = new Vector<Vector<Vector<Integer>>>();
    int space_counter = 0;
    int matrix_index = -1;
    int matrix_column_index = 0;
    Vector<Vector<Integer>> matrix_buffer = new Vector<Vector<Integer>>(); 
    for(int index=0; index<filelines.size() ; index++)
    {
      String datastream = filelines.get(index);
      if (index== 0)
      {
        String[] buffer = datastream.split(",");
        bingo_array = new int[buffer.length];
        for(int i=0; i< buffer.length; i++)
        {
          bingo_array[i] = Integer.parseInt(buffer[i]);
        }
      }
      else
      {
        if(datastream.equals(""))
        {
          space_counter += 1;
          matrix_column_index = 0;
        }
        else
        {
          Vector<Integer> vector_buffer = new Vector<Integer>();
          String[] string_buffer = datastream.trim().split("\\s+");
          
          if (space_counter == 1)
          {
            matrix_buffer = new Vector<Vector<Integer>>();
            bingo_matrix_array.add(matrix_buffer);
            matrix_index += 1;
          } 
          space_counter = 0;
          for(int i=0; i< string_buffer.length; i++)
          {
            if (string_buffer[i].equals(""))
            {

            }
            else
            {
              vector_buffer.add(Integer.parseInt(string_buffer[i]));
            }
            
          }
          matrix_buffer.add(vector_buffer);
        }
      }
      
    }
    for(int index=0; index<bingo_array.length; index++)
    {
      int bingo_value = bingo_array[index];
      Boolean check_matrix = false;
      for(int z = 0; z <bingo_matrix_array.size(); z++)
      {
        Vector<Vector<Integer>> matrix = bingo_matrix_array.get(z);
        int matrix_column_size = matrix.get(0).size();
        int matrix_row_size = matrix.size();
        for (int y = 0; y < matrix.size(); y++)
        {
          Vector<Integer> vector = matrix.get(y);
          for (int x = 0; x<vector.size(); x++)
          {
            if (vector.get(x) == bingo_value)
            {
              vector.set(x, -1);
              check_matrix = true;
            }
            if (check_matrix)
            {
              break;
            }
          }
          if (check_matrix)
          {
            break;
          }
        }
        if (check_matrix)
        {
          boolean bingo = false;
          for(int i=0; i<matrix_row_size; i++)
          {
            int row_sum = 0;
            for (int j=0; j<matrix_column_size; j++)
            {
              if(matrix.get(i).get(j) == -1)
                row_sum +=1;
            }
            if(row_sum == matrix_column_size)
            {
              bingo = true;
              break;
            }
          }
          for(int j=0; j < matrix_column_size; j++)
          {
            int column_sum = 0;
            for (int i=0; i < matrix_row_size; i++)
            {
              if(matrix.get(i).get(j) == -1)
                column_sum +=1;
            }
            if(column_sum == matrix_column_size)
            {
              bingo = true;
              break;
            }
          }
          if (bingo == true)
          {
            System.out.println("Bingo");
            int sum = 0;
            for(int i=0; i<matrix_row_size; i++)
            {
              for (int j=0; j<matrix_column_size; j++)
              {
                if(matrix.get(i).get(j) != -1)
                  sum +=matrix.get(i).get(j);
              }
            }
            System.out.println("Sum = " +sum);
            System.out.println("Answer = " +sum*bingo_value);
            return;
          }
        }
        check_matrix = false;
      }

    }
  }
  static void question_2()
  {
    ArrayList<String> filelines = readFile("data.txt");
    int bingo_array[] = new int[1];
    Vector<Vector<Vector<Integer>>>  bingo_matrix_array = new Vector<Vector<Vector<Integer>>>();
    int space_counter = 0;
    int matrix_index = -1;
    int matrix_column_index = 0;
    Vector<Vector<Integer>> matrix_buffer = new Vector<Vector<Integer>>(); 
    for(int index=0; index<filelines.size() ; index++)
    {
      String datastream = filelines.get(index);
      if (index== 0)
      {
        String[] buffer = datastream.split(",");
        bingo_array = new int[buffer.length];
        for(int i=0; i< buffer.length; i++)
        {
          bingo_array[i] = Integer.parseInt(buffer[i]);
        }
      }
      else
      {
        if(datastream.equals(""))
        {
          space_counter += 1;
          matrix_column_index = 0;
        }
        else
        {
          Vector<Integer> vector_buffer = new Vector<Integer>();
          String[] string_buffer = datastream.trim().split("\\s+");
          
          if (space_counter == 1)
          {
            matrix_buffer = new Vector<Vector<Integer>>();
            bingo_matrix_array.add(matrix_buffer);
            matrix_index += 1;
          } 
          space_counter = 0;
          for(int i=0; i< string_buffer.length; i++)
          {
            if (string_buffer[i].equals(""))
            {

            }
            else
            {
              vector_buffer.add(Integer.parseInt(string_buffer[i]));
            }
            
          }
          matrix_buffer.add(vector_buffer);
        }
      }
      
    }
    int start_matrix_size = 0;
    for(int index=0; index<bingo_array.length; index++)
    {
      //Boolean bingo_matrix_state[] = new Boolean[bingo_matrix_array.size()];
      //Arrays.fill(bingo_matrix_state, false);
      int bingo_value = bingo_array[index];
      Boolean check_matrix = false;
      int bingo_winner = -1;
      start_matrix_size = bingo_matrix_array.size();
      for(int z = bingo_matrix_array.size() - 1; z >=0; z--)
      {
        Vector<Vector<Integer>> matrix = bingo_matrix_array.get(z);
        int matrix_column_size = matrix.get(0).size();
        int matrix_row_size = matrix.size();
        for (int y = 0; y < matrix.size(); y++)
        {
          Vector<Integer> vector = matrix.get(y);
          for (int x = 0; x<vector.size(); x++)
          {
            if (vector.get(x) == bingo_value)
            {
              vector.set(x, -1);
              check_matrix = true;
              break;
            }
          }
          if (check_matrix)
          {
            break;
          }
        }
        if (check_matrix)
        {
          boolean bingo = false;
          for(int i=0; i<matrix_row_size; i++)
          {
            int row_sum = 0;
            for (int j=0; j<matrix_column_size; j++)
            {
              if(matrix.get(i).get(j) == -1)
                row_sum +=1;
            }
            if(row_sum == matrix_column_size)
            {
              bingo = true;
              break;
            }
          }
          for(int j=0; j < matrix_column_size; j++)
          {
            int column_sum = 0;
            for (int i=0; i < matrix_row_size; i++)
            {
              if(matrix.get(i).get(j) == -1)
                column_sum +=1;
            }
            if(column_sum == matrix_column_size)
            {
              bingo = true;
              break;
            }
          }
          if (bingo == true)
          {
            int sum = 0;
            for(int i=0; i<matrix_row_size; i++)
            {
              for (int j=0; j<matrix_column_size; j++)
              {
                if(matrix.get(i).get(j) != -1)
                  sum +=matrix.get(i).get(j);
              }
            }
            System.out.println("Remaining of bingo matrices " + bingo_matrix_array.size());
            if (bingo_matrix_array.size() == 1)
            {
              System.out.println("Sum = " +sum);  
              System.out.println("Answer = " +sum*bingo_value);
              return;
            }
            else
            {
              bingo_winner = z;
            }
            if (bingo_winner != -1)
            {
              bingo_matrix_array.remove(bingo_winner);
              bingo_winner = -1; 
            }
          }
        }
        check_matrix = false;
      }

    }
  }
  public static void main(String[] args) {
    //question_1();
    question_2();
  }
}

