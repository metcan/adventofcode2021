
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.regex.Pattern;

public class Day21 {
    private String _puzzleinput;
    private String _filename;
    int start1;
    int start2;

    public Day21(String file_name) {
        _filename = file_name;
        getPuzzleInput();
        var input_stream = _puzzleinput.trim().split("\n");
        start1 = Character.getNumericValue(input_stream[0].charAt(input_stream[0].length()-1));
        start2 = Character.getNumericValue(input_stream[1].charAt(input_stream[1].length()-1));
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
        var dice = 0;
        var player1_score = 0;
        var player1_pos = start1 - 1;
        var player2_pos = start2 - 1;
        var player2_score = 0;
        var roll = 0;
        while(true)
        {
            roll = dice++ %100 + dice++ %100 + dice++ %100 + 3;
            player1_pos = (player1_pos + roll)%10;
            player1_score += player1_pos+1; 
            if(player1_score >= 1000){
            return String.valueOf(dice * player2_score);}
            
            roll = dice++ %100 + dice++ %100 + dice++ %100 + 3;
            player2_pos = (player2_pos + roll)%10;
            player2_score += player2_pos + 1;
            if(player2_score >= 1000)
            {
            return String.valueOf(dice * player1_score);
            }
        }
        
    }

    public String solvePart2() {
        long [][][][] universes = new long[10][10][22][22];
        universes[start1-1][start2-1][0][0] = 1;
        // role dice
        for(var score1=0; score1<21; score1++)
        {
            for(var score2=0; score2<21; score2++)
            {
                for(var player1=0; player1<10; player1++)
                {
                    for(var player2=0; player2<10; player2++)
                    {
                        for(var roll1: DICEROLL)
                        {
                            var newplayer1 = (player1 + roll1) % 10;
                            var newscore1 = Math.min(score1 + newplayer1 + 1, 21);
                            if( newscore1 == 21) // end the universe, no need to roll for second player
                            {
                                universes[newplayer1][player2][newscore1][score2] += universes[player1][player2][score1][score2]; 
                            }
                            else{
                                for(var roll2: DICEROLL)
                                {
                                    var newplayer2 = (player2 + roll2) % 10;
                                    var newscore2 = Math.min(score2 + newplayer2 + 1, 21);
                                    universes[newplayer1][newplayer2][newscore1][newscore2] += universes[player1][player2][score1][score2];
                                    
                                 
                                }
                            }      
                        }
                    }
                }
            }
        }

        var win1 = 0L;
        var win2 = 0L;
        for (var player1=0; player1<10; player1++)
        {
            for(var player2=0; player2<10; player2++)
            {
                for(var score=0; score<21; score++ )
                {
                    win1 += universes[player1][player2][21][score];                
                    win2 += universes[player1][player2][score][21];
                }
            }
        }
        return String.valueOf(Math.max(win1, win2));
    }
    static int [] DICEROLL = new int[27];
    static {
        var x = 0;
        for(var i = 1; i<=3; i++)
        {
            for(var l = 1; l<=3; l++)
            {
                for(var k =1; k<=3; k++)
                {
                    DICEROLL[x++] = i + l + k; // to simulate all three dice roll 
                }
            }
        }
    }
}