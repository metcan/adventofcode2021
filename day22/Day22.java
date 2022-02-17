
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;


public class Day22 {
    private String _puzzleinput;
    private String _filename;
    private final CubeInstruction[] instructions;
    int start1;
    int start2;

    public Day22(String file_name) {
        _filename = file_name;
        getPuzzleInput();
        var input_stream = _puzzleinput.trim().split("\n"); 
        instructions = new CubeInstruction[input_stream.length];
        var pattern = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
        for(var i=0; i<input_stream.length; i++)
        {
            var line = input_stream[i];
            var matcher = pattern.matcher(line);
            if(matcher.matches())
            {
                var instruction = matcher.group(1).equals("on");
                var minX = Integer.parseInt(matcher.group(2));
                var maxX = Integer.parseInt(matcher.group(3));
                var minY = Integer.parseInt(matcher.group(4));
                var maxY = Integer.parseInt(matcher.group(5));
                var minZ = Integer.parseInt(matcher.group(6));
                var maxZ = Integer.parseInt(matcher.group(7));
                instructions[i] = new CubeInstruction(new Cube(minX, maxX, minY, maxY, minZ, maxZ), instruction);
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
    
    

    public String solvePart1() {
        var start_region = new boolean[101][101][101];
        for(var instruction: instructions){
            for(var x=-50; x<51; x++)
            {
                for(var y=-50; y<51; y++)
                {
                    for(var z=-50; z<51; z++)
                    {
                        if(instruction.cube().contains(x, y, z))
                        {
                            start_region[x+50][y+50][z+50] = instruction.instruction();
                        }
                    }
                }
            }
        }
        var ons_count = 0;
        for(var i = 0; i<101; i++)
        {
            for(var j = 0; j<101; j++)
            {
                for(var k = 0; k<101; k++){
                    if(start_region[i][j][k])
                    {
                        ons_count++;
                    }
                }
            }
        }
        return String.valueOf(ons_count);
    }

    public String solvePart2() {
        var cubes = new HashMap<Cube, Long>();
        for(var instruction: instructions)
        {
            var cube1 = instruction.cube();
            var update = new HashMap<Cube, Long>(); // if intersection, hold reverse of intersection region. 
            cubes.forEach((cube2, count) -> cube1.intersection(cube2).ifPresent(cube -> update.merge(cube, -count , Long::sum) ) );
            if(instruction.instruction())
            {
                update.merge(cube1, 1L, Long::sum);
            }
            update.forEach((cube, count) -> cubes.merge(cube, count, Long::sum));
        }
        return String.valueOf(cubes.entrySet().stream().mapToLong(e->e.getKey().volume() * e.getValue()).sum());
    }
    public record CubeInstruction(Cube cube, boolean instruction){

    }

    public record Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ){
        boolean contains(int x, int y, int z)
        {
            return x>=minX && x<=maxX && y>=minY && y<=maxY && z>=minZ && z<=maxZ;
        }

        Optional<Cube> intersection(Cube cube2){
            var minX = Math.max(this.minX, cube2.minX);
            var maxX = Math.min(this.maxX, cube2.maxX);
            var minY = Math.max(this.minY, cube2.minY);
            var maxY = Math.min(this.maxY, cube2.maxY);
            var minZ = Math.max(this.minZ, cube2.minZ);
            var maxZ = Math.min(this.maxZ, cube2.maxZ);
            if(minX<=maxX&&minY<=maxY&&minZ<=maxZ){
                return Optional.of(new Cube(minX, maxX, minY, maxY, minZ, maxZ));
            }
            else{
                return Optional.empty();
            }
        }

        long volume()
        {
            long x = maxX - minX + 1;
            long y = maxY - minY + 1;
            long z = maxZ - minZ + 1;
            return x * y * z;
        }
    }
}