import java.util.Random;

//Each players runs as its own thread in the game.
class Player extends Thread
{
    private Barrier go;
    private int playernum;
    private int selection;
    private Player Players[];
    private int numPlayers;
    private int Totalrounds;

    //player victory conditions
    private boolean win;
    private boolean draw;
    private boolean loss;

    private int rockWins = 0, paperWins = 0, scissorsWins = 0;

    //initiates player
    public Player(Barrier B, int value, Player P[], int nP, int r)
    {
        go = B;
        playernum = value+1;
        Players = P;
        numPlayers = nP;
        Totalrounds = r;
    }

    //returns current player selection
    public int selection()
    {
        return selection;
    }

    //returns total wins
    public int rockWins()
    {
        return rockWins;
    }

    //returns total loss
    public int paperWins()
    {
        return paperWins;
    }

    //returns total loss
    public int scissorsWins()
    {
        return scissorsWins;
    }



    //returns weather or not the player won
    public boolean didsomeonewin()
    {
        return win;
    }

    //contains the logic for determining wins, loses and draws for each thread.
    private void DidIwin()
    {
        int count = 0;
        while(count < numPlayers)
        {
            if(count+1 != playernum)
            {
                int enemypick;
                enemypick = Players[count].selection();

                if(selection == 0)
                {
                    if(enemypick == 0 || enemypick == 1)
                        draw = true;
                    if(enemypick == 2 && draw == false && loss == false)
                        win = true;
                }
                if(selection == 1)
                {
                    if(enemypick == 1 || enemypick == 2)
                        draw = true;
                    if(enemypick == 0 && draw == false && loss == false)
                        win = true;
                }
                if(selection == 2)
                {
                    if(enemypick == 2 || enemypick == 0)
                        draw = true;
                    if(enemypick == 1 && draw == false && loss == false)
                        win = true;
                }
            }
            //a catch for all players if they
            //have a draw or loss they cannot win
            if(draw == true || loss == true)
                win = false;

            count++;
        }
        //handles the counts for the winds and draws
        if (win)
        {
            if (selection == 0)
                rockWins++;
            if (selection == 1)
                paperWins++;
            if (selection == 2)
                scissorsWins++;
        }


    }

    //Player will make a selection then wait for other players
    //next they check to see how they did, then wait
    //finally they report on how they did compared to others.
    public void run()
    {
        int round = 1;

        while(round <= Totalrounds)
        {

            Random rand = new Random();
            //A random number between 0 and 2
            selection = rand.nextInt(3);

            //rock = 0
            if(selection == 0)
                System.out.println ("Round " + round + ": Player " + playernum + " has selected Rock");
                //paper = 1
            else if(selection == 1)
                System.out.println ("Round " + round + ": Player " + playernum + " has selected Paper");
                //scissors = 2
            else if(selection == 2)
                System.out.println ("Round " + round + ": Player " + playernum + " has selected Scissors");

            go.waitForOthers();

            win = false;
            draw = false;
            loss = false;
            //Selection of winning status
            DidIwin();

            //the player waits for the other players.
            go.waitForOthers();

            //handles draw logic
            if(draw)
                for(int i = 0; i < numPlayers; i++)
                {
                    //if one player won that means all other players lost
                    if(Players[i].didsomeonewin())
                    {
                        loss = true;
                        draw = false;
                    }
                }
            //outputs to the terminal the state of the player for the particular round.
            if(win == true)
                System.out.println (" Player " + playernum + " " + "wins");
            else if(draw == true)
                System.out.println (" Player " + playernum + " " + "draw");
            else if(loss == true)
                System.out.println (" Player " + playernum + " " +  "loses");
            round++;
        }
    }
}
