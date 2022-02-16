
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Day20 {
    public String _puzzleinput;
    public String _filename;
    static boolean[] check_list = new boolean[512];
    Set<Point> image_set = new HashSet<>();

    public Day20(String file_name) {
        _filename = file_name;
        getPuzzleInput();
        var input_stream = _puzzleinput.trim().split("\n\n");
        var rules = input_stream[0];
        for (var i = 0; i < rules.length(); i++) {
            check_list[i] = rules.charAt(i) == '#';
        }
        var image_string = input_stream[1].split("\n");
        for (var i = 0; i < image_string.length; i++) {
            for (var j = 0; j < image_string[i].length(); j++) {
                if (image_string[i].charAt(j) == '#') {
                    image_set.add(new Point(j, i));
                }
            }
        }
    }

    public void getPuzzleInput() {
        Path fileName = Path.of(_filename);
        try {
            _puzzleinput = Files.readString(fileName);
        } catch (IOException e) {
            System.out.println("Error at file read");
            e.printStackTrace();
        }
        return;

    }
    public Set<Point> enhancement(Set<Point> image_set, int step){
        var max_x = image_set.stream().mapToInt(Point::x).max().orElse(0) + 3;
        var max_y = image_set.stream().mapToInt(Point::y).max().orElse(0) + 3;
        var min_x = image_set.stream().mapToInt(Point::x).min().orElse(0) - 3;
        var min_y = image_set.stream().mapToInt(Point::y).min().orElse(0) - 3;
        var new_pixels = new HashSet<Point>();
        // Invert known zeros, 
        for (var y = min_y; y <= max_y; y++) {
            for (var x = min_x; x <= max_x; x++) {
                var value = 0;
                for (var dy = -1; dy <= 1; dy++) {
                    for (var dx = -1; dx <= 1; dx++) {
                        value <<= 1;
                        if (image_set.contains(new Point(x + dx, y + dy)) == infiniteRegionCheck(step)) {
                            value |= 1;
                        }
                    }
                }
        
                if (check_list[value] == infiniteRegionCheck(step + 1)) {
                    new_pixels.add(new Point(x, y));
                }
            }
        }
        return new_pixels;
    }

    public boolean infiniteRegionCheck(int step)
    {
        if(check_list[0] && !check_list[511])
        {
           return step % 2 == 0; //assume boundry regions are 1 for second step if  
        }
        else{
        return !check_list[0];
        }
    }


    public String  solvePart1() {
        var buf_image_set = this.image_set;
        var step = 0;
        buf_image_set = enhancement(buf_image_set, step++);
        buf_image_set = enhancement(buf_image_set, step);
        return String.valueOf(buf_image_set.size());
    }

    public String solvePart2() {
        var buf_image_set = this.image_set;
        var step = 0;
        for (var i = 0; i<50; i++)
        {
            buf_image_set = enhancement(buf_image_set, step++);
        }
        return String.valueOf(buf_image_set.size());
    }

    public record Point(int x, int y) {
    }

}