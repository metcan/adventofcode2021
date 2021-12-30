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
import javax.swing.text.html.HTMLDocument.BlockElement;
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
        ArrayList<String> filelines = readFile("data.txt");
        ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
        int largest_cost = 1<<31-1;
        for(int i=0; i<filelines.size(); i++)
        {
            ArrayList<Integer> vector = new ArrayList<Integer>();
            ArrayList<Integer> dummy_vector = new ArrayList<Integer>();
            String[] fileline = filelines.get(i).split("|");
            for(int j=0; j< fileline.length; j++)
            {
                vector.add(Integer.parseInt(fileline[j]));
                dummy_vector.add(largest_cost);
            }
            map.add(vector);
            weight.add(dummy_vector);
        } 
        ArrayList<int[]> search_stack = new ArrayList<int[]>();
        int[] start_location = {0,0};
        weight.get(0).set(0, 0);
        search_stack.add(start_location);
        int[] x_search = {1, -1 ,0, 0};
        int[] y_search = {0, 0, 1, -1};
        int max_i_size = map.size();
        int max_j_size = map.get(0).size();
        while(!(search_stack.isEmpty()))
        {
            int[] current_node = search_stack.remove(search_stack.size()-1);
            for(int i=0; i<x_search.length; i++)
            {
                int ni = current_node[0] + x_search[i];
                int nj = current_node[1] + y_search[i];
                if(ni>=0 && ni<max_i_size && nj>=0 && nj < max_j_size)
                {
                    int temp_cost = weight.get(current_node[0]).get(current_node[1]) + map.get(ni).get(nj);
                    if (temp_cost < weight.get(ni).get(nj))
                    {
                        int[] next_node = {ni, nj};
                        if (temp_cost < largest_cost)
                        {
                            search_stack.remove(next_node);
                        }
                        weight.get(ni).set(nj, temp_cost);
                        search_stack.add(next_node);
                    }
                }
                
            }
        }
        System.out.println("Cost =" +weight.get(weight.size()-1).get(weight.get(0).size()-1));
    }

    static void question_2() {
        ArrayList<String> filelines = readFile("data.txt");
        ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
        int largest_cost = 1<<31-1;
        for(int i=0; i<filelines.size(); i++)
        {
            ArrayList<Integer> vector = new ArrayList<Integer>();
            ArrayList<Integer> dummy_vector = new ArrayList<Integer>();
            String[] fileline = filelines.get(i).split("|");
            for(int j=0; j< fileline.length; j++)
            {
                vector.add(Integer.parseInt(fileline[j]));
                dummy_vector.add(largest_cost);
            }
            map.add(vector);
            weight.add(dummy_vector);
        } 
        int[][] new_map = new int[map.size()*5][map.get(0).size()*5];
        int[][] new_weight = new int[map.size()*5][map.get(0).size()*5];
        for(int i = 0; i<map.size(); i++)
        {
            for(int j = 0; j<map.get(0).size(); j++)
            {
                for(int k = 0; k<5; k++)
                {
                    for(int l = 0; l<5; l++)
                    {
                        new_map[k*map.size() + i][l*map.get(0).size() + j] = (map.get(i).get(j)+k+l)%9==0?9:(map.get(i).get(j)+k+l)%9;
                        new_weight[k*map.size() + i][l*map.get(0).size() + j] = largest_cost;
                    }
                }
            }
        }
        ArrayList<int[]> search_stack = new ArrayList<int[]>();
        int[] start_location = {0,0};
        new_weight[0][0] = 0;
        search_stack.add(start_location);
        int[] x_search = {1, -1 ,0, 0};
        int[] y_search = {0, 0, 1, -1};
        int max_i_size = map.size() * 5;
        int max_j_size = map.get(0).size() * 5;
        while(!(search_stack.isEmpty()))
        {
            int[] current_node = search_stack.remove(search_stack.size()-1);
            for(int i=0; i<x_search.length; i++)
            {
                int ni = current_node[0] + x_search[i];
                int nj = current_node[1] + y_search[i];
                if(ni>=0 && ni<max_i_size && nj>=0 && nj < max_j_size)
                {
                    //int temp_cost = weight.get(current_node[0]).get(current_node[1]) + map.get(ni).get(nj);
                    int temp_cost = new_weight[current_node[0]][current_node[1]] + new_map[ni][nj];
                    if (temp_cost < new_weight[ni][nj])
                    {
                        int[] next_node = {ni, nj};
                        if (temp_cost < largest_cost)
                        {
                            search_stack.remove(next_node);
                        }
                        //weight.get(ni).set(nj, temp_cost);
                        new_weight[ni][nj] = temp_cost;
                        search_stack.add(next_node);
                    }
                }
                
            }
        }
        System.out.println("Cost =" +new_weight[new_weight.length-1][new_weight[0].length-1]);
    }

    public static void main(String[] args) {
        //question_1();
        question_2();
    }
}
