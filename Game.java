import javax.swing.*;

import java.awt.*;

import java.applet.Applet;

import java.util.Scanner;


//class that handles the game mechanics, contains the main method and handles the applet displays
public class Game extends Applet{

    int n=0;
    //used for applet
    String label[];
    //used for applet
    int value[];
    //used for applet
    static int scissorsWinsVar=0;
    //used for applet
    static int rockWinsVar=0;
    //used for applet
    static int paperWinsVar=0;
    //used for applet
    static int drawsVar=0;

    //Total number of rounds that the game runs
    public static final int rounds = 1000;


    //Main function for the program.
    public static void main(String[] args)
    {
        //creats a new game object
        Game game = new Game();

        //initiates the game to run.
        game.init();
    }



// handles I/O interactions and initiates Player objects and outputs statistics about game.
    public void runMainProg() {

// User inputs number here

        Scanner reader = new Scanner(System.in); // Reading from System.in

        System.out.println("Enter the number of players: ");

        int numPlayers = reader.nextInt(); // Scans the next token of the input as an int.

        while (numPlayers < 2 || numPlayers > 10) {

            System.out.println("Oops... Enter a valid number between 2 and 10");

            numPlayers = reader.nextInt();

        }

        Barrierimp go = new Barrierimp(numPlayers);

        Player[] p;

        p = new Player[numPlayers];

        //creates an array that holds the threads depending on how many players in the game are selected
        Thread Players[] = new Thread[numPlayers];

        //initiates win counts to 0 to start.
        int rockWins = 0, paperWins = 0, scissorsWins = 0;

        //creates the players for the game and starts each thread in the program.
        for (int i = 0; i < numPlayers; i++) {

            p[i] = new Player(go, i, p, numPlayers, rounds);

            Players[i] = new Thread(p[i]);

            Players[i].start();

        }

        for (int j = 0; j < numPlayers; j++) {

            try {

                Players[j].join();

            } catch (InterruptedException e) {

            }

            ;

        }
        //Keeps track of the total number of wins.
        for (int j = 0; j < numPlayers; j++) {

            scissorsWins += p[j].scissorsWins();

            paperWins += p[j].paperWins();

            rockWins += p[j].rockWins();

        }

        System.out.println("Summary Statistics:");

        System.out.println("Number of times scissors won: " + scissorsWins);

        System.out.println("Number of times rock won: " + rockWins);

        System.out.println("Number of times paper won: " + paperWins);

        System.out.println("Number of draws: "

                + (rounds - (scissorsWins + rockWins + paperWins)));

        int draws = rounds - (scissorsWins + rockWins + paperWins);

        scissorsWinsVar=scissorsWins;

        rockWinsVar = rockWins;

        paperWinsVar = paperWins;

        drawsVar = draws;

    }


    // Assigns the data and formats the data for display on the applet
    public void setData(int scissorsWins,int rockWins, int paperWins,int draws) {

        setBackground(Color.white);

        try {

            int n = 4;

            label = new String[n];

            value = new int[n];

            label[0] = "Number of times scissors won";

            label[1] = "Number of times rock won";

            label[2] = "Number of times paper won";

            label[3] = "Number of draws";

            value[0] = scissorsWins;

            value[1] = rockWins;

            value[2] = paperWins;

            value[3] = draws;

        }

        catch(NumberFormatException e){}

    }

    //initializes the applet to run.
    public void init() {

        runMainProg();

        setData(scissorsWinsVar, rockWinsVar, paperWinsVar, drawsVar);

    }

    //prints out the graphics on the applet.
    public void paint(Graphics g)

    {

        for(int i=0;i<4;i++) {

            g.setColor(Color.black);

            g.drawString(label[i],20,i*50+30);

            if(i == 1)
                g.setColor(Color.red);
            else if (i == 2)
                g.setColor(Color.cyan);
            else if (i ==3)
                g.setColor(Color.green);
            else
                g.setColor(Color.magenta);

            g.fillRect(250,i*50+10,value[i],40);

            g.setColor(Color.black);

            g.drawString((Integer.toString(value[i])),320,i*50+30);

        }

    }

}