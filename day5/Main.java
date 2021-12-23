import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;
import java.lang.Math;
import javax.lang.model.util.ElementScanner14;

import java.util.Arrays;




public class Main {
  static class Point2D
  {
      public int x; 
      public int y;
      public Point2D(int point_x, int point_y) {
        x = point_x; 
        y = point_y;
      }
      public Point2D(String string)
      {
        String[] points = string.split(",");
        x = Integer.parseInt(points[0]);
        y = Integer.parseInt(points[1]);
      }
      public boolean point_check(Point2D test_point)
      {
        if (test_point.x == x && test_point.y == y)
         return true;
        else
         return false;
      }
      
  };
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
  static ArrayList<Point2D> generate_line_points(Point2D start_point, Point2D end_point, boolean solve_diagonal)
  {
    ArrayList<Point2D> generated_points = new ArrayList<Point2D>();
    // Check is it straight line or else
    if (start_point.x == end_point.x || start_point.y == end_point.y)
    {
      // It is a line
      if(start_point.x == end_point.x)
      {
        if(start_point.y >= end_point.y)
        {
          for(int y = end_point.y; y<=start_point.y; y++)
          {
            generated_points.add(new Point2D(start_point.x, y));
          }
        }
        else
        {
          for(int y = start_point.y; y<=end_point.y; y++)
          {
            generated_points.add(new Point2D(start_point.x, y));
          }
        }
      }
      else
      {
        if(start_point.x >= end_point.x)
        {
          for(int x = end_point.x; x<=start_point.x; x++)
          {
            generated_points.add(new Point2D(x, start_point.y));
          }
        }
        else
        {
          for(int x = start_point.x; x<=end_point.x; x++)
          {
            generated_points.add(new Point2D(x, start_point.y));
          }
        }
      }
    }
    else
    {
      if (solve_diagonal)
      {
        // determine the direction
        int x = start_point.x - end_point.x;
        int y = start_point.y - end_point.y;
        Point2D direction;
        if (x>=0&& y>=0)
        direction = new Point2D(-1,-1);
        else if (x>=0&&y<=0)
        direction = new Point2D(-1, 1);
        else if (x<=0&&y>=0)
        direction = new Point2D(1, -1);
        else 
        direction = new Point2D(1, 1);

        for(int i=0; i<=Math.abs(x); i++)
        {
          generated_points.add(new Point2D(start_point.x, start_point.y));
          start_point.x += direction.x;
          start_point.y += direction.y;
        }
      }
    }
    return generated_points;
  }

  static void question_1()
  {
    ArrayList<String> filelines = readFile("test_set.txt");
    ArrayList<Point2D> coordinate_point_array = new ArrayList<Point2D>();
    for(int index=0; index<filelines.size(); index++)
    {
      String[] parser = filelines.get(index).split(" ");
      Point2D start_point = new Point2D(parser[0]);
      Point2D end_point = new Point2D(parser[2]);
      coordinate_point_array.addAll(generate_line_points(start_point, end_point, false));
    }
    int max_x_axis = 0;
    int max_y_axis = 0;
    for (int i=0; i<coordinate_point_array.size(); i++)
    {
      Point2D point = coordinate_point_array.get(i);
      if (point.x > max_x_axis)
        max_x_axis = point.x;
      if (point.y > max_y_axis)
        max_y_axis = point.y;
    }
    int[][] coordinate_grid = new int[max_x_axis+1][max_y_axis+1];
    Arrays.stream(coordinate_grid).forEach(a -> Arrays.fill(a, 0));
    for(int i=0; i<coordinate_point_array.size(); i++)
    {
      Point2D point = coordinate_point_array.get(i);
      coordinate_grid[point.x][point.y] += 1;
    }
    int count = 0;
    for(int i=0; i< coordinate_grid.length; i++)
    {
      for (int j=0; j< coordinate_grid[0].length; j++)
      {
        if (coordinate_grid[i][j] >= 2)
        {
          count += 1;
        }
      }
    }
    
    System.out.println(count);
  }
  static void question_2()
  {
    ArrayList<String> filelines = readFile("data.txt");
    ArrayList<Point2D> coordinate_point_array = new ArrayList<Point2D>();
    for(int index=0; index<filelines.size(); index++)
    {
      String[] parser = filelines.get(index).split(" ");
      Point2D start_point = new Point2D(parser[0]);
      Point2D end_point = new Point2D(parser[2]);
      coordinate_point_array.addAll(generate_line_points(start_point, end_point, true));
    }
    int max_x_axis = 0;
    int max_y_axis = 0;
    for (int i=0; i<coordinate_point_array.size(); i++)
    {
      Point2D point = coordinate_point_array.get(i);
      if (point.x > max_x_axis)
        max_x_axis = point.x;
      if (point.y > max_y_axis)
        max_y_axis = point.y;
    }
    int[][] coordinate_grid = new int[max_x_axis+1][max_y_axis+1];
    Arrays.stream(coordinate_grid).forEach(a -> Arrays.fill(a, 0));
    for(int i=0; i<coordinate_point_array.size(); i++)
    {
      Point2D point = coordinate_point_array.get(i);
      coordinate_grid[point.x][point.y] += 1;
    }
    int count = 0;
    for(int i=0; i< coordinate_grid.length; i++)
    {
      for (int j=0; j< coordinate_grid[0].length; j++)
      {
        if (coordinate_grid[i][j] >= 2)
        {
          count += 1;
        }
      }
    }
    System.out.println(count);
  }
  public static void main(String[] args) {
    //question_1();
    question_2();
  }
}

