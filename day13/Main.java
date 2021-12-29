import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.nio.Buffer;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Vector;

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
        ArrayList<ArrayList<Integer>> instructions = new ArrayList<ArrayList<Integer>>();
        Boolean first_parser = true;
        int highest_x = 0;
        int count = 0;
        for (int i = 0; i < filelines.size(); i++)
        {
            if(filelines.get(i).equals(""))
            {
                first_parser = false;
                continue;
            }
            if (first_parser)
            {
                String[] coordinates = filelines.get(i).split(",");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                
                if(y > instructions.size()-1)
                {
                    for(int k = instructions.size()-1; k <y; k++)
                    {
                        ArrayList<Integer> vector = new ArrayList<Integer>();
                        instructions.add(vector);
                    }
                }
                if (highest_x < x)
                    highest_x = x;
                if(x > instructions.get(y).size() - 1)
                {
                    for(int k = instructions.get(y).size() - 1; k<x; k++)
                    {
                        instructions.get(y).add(0);
                    }
                }
                instructions.get(y).set(x, 1);
                for(int j = 0; j<instructions.size(); j++)
                {
                    while(instructions.get(j).size()-1 < highest_x)
                    {
                        instructions.get(j).add(0);
                    }
                }
            }
            else
            {
                String[] buffer = filelines.get(i).split(" ");
                buffer = buffer[buffer.length - 1].split("="); 
                String direction = buffer[0];
                int amount = Integer.parseInt(buffer[1]);
                if(direction.equals("y"))
                {
                    int reverse_index = amount;
                    for(int j = amount+1; j<instructions.size(); j++)
                    {
                        reverse_index -= 1;
                        for(int k=0; k<instructions.get(j).size(); k++)
                        {                            
                            instructions.get(reverse_index).set(k, instructions.get(reverse_index).get(k) | instructions.get(j).get(k));
                            instructions.get(j).set(k, 0);
                        }
                    }
                }
                else
                {
                    for(int j = 0; j<instructions.size(); j++)
                    {
                        for(int k=amount; k<instructions.get(j).size(); k++)
                        {
                            int temp = instructions.get(j).get(k);
                            instructions.get(j).set(k, 0);
                            int reverse_index = amount - (k - amount);
                            instructions.get(j).set(reverse_index, instructions.get(j).get(reverse_index) | temp);
                        }
                    }
                }
            }
            
        }
        for (int j = 0; j<instructions.size(); j++)
        {
            for(int i=0; i<instructions.get(j).size(); i++)
            {
                if(instructions.get(j).get(i) == 0)
                count +=1;
            }
        }
        System.out.println(count); 
    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");

    }

    public static void main(String[] args) {
        question_1();
        //question_2();
    }
}
