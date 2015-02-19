/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import java.util.*;
/**
 *
 * @author RITESH
 */

//class XORO created for an abstract data-type: has 2 members data. which stores X or O and value which makes it true(occupied position in the game)or false(free position)
class XORO
{
	char data;
	boolean value;	
	XORO()
	{
		value=false;
                data=' ';
	}
	public XORO(char adata)
	{
		data=adata;
		value=true;
	}
}

public class TicTacToe {
    	static XORO existed[]= new XORO[9];
        static int final_state=-1;
        //function to print the current status of the game
	public static void printGame()
	{
		for(int i=0;i<3;i++)
                {
                    
                    System.out.print(" "+existed[0+(3*i)].data+" | "+ existed[1+(3*i)].data+" | "+existed[2+(3*i)].data+" ");
                    System.out.println();
                    if(i!=2)
                        System.out.println("--- --- ---");
                }
	}	
        //function to check if the game has been won
	public static boolean checkWin(int position, char myChar)
	{
		if(position==-1)
			return false;
		int x_position= (position-1)/3;//get the x_position of the tic_tac toe game. Range from 0-2. Eg Position 6 has (x_position,y_position) (1,2) ..like a M* N matrix
		int y_position=(position-1)%3;//get the y_position

		for(int i=1;i<=3;i++)//check if there is a winner in the row of the last input given
                {			
			int check_position=i+(3*x_position);//tictactoe number 1-9
			char c= existed[check_position-1].data;

			if(c!=myChar)
				break;
			if(i==3)
				return true;//entire row of last inputted value is either X or O; hence game has been won.
		}		
		for(int i=0;i<=2;i++)
		{
			int check_position= (y_position+1)+(3*i);//tictactoe cell number 1-9
			char c= existed[check_position-1].data;
			if(c!=myChar)
				break;
			if(i==2)
				return true;//entire column of last inputted value is either X or O; hence game has been won.
		}		
		if((x_position==y_position) || ((x_position+y_position)==2))//diagonal or anti-diagonal check for the win
		{
			if(x_position-y_position==0)
			{
				if(existed[0].data==myChar && existed[4].data==myChar && existed[8].data==myChar)
					return true;				//winner across diagonal found
			}
			else
			{
				if(existed[2].data==myChar && existed[4].data==myChar && existed[6].data==myChar)
					return true;                            //winner across anti-diagonal found
			}
                }
		return false;
	}
        //function to check if the game is drawn as all the cells in the game are exhausted
	public static boolean exhausted()
	{
		for(int i=0;i<9;i++)
		{
			if(!existed[i].value)
				return false;
		}
                final_state=3;
		
		return true;
	}
        //get computer position of the game for the next move
	public static int getCompPosition()
	{
		Random rn = new Random();//initialization of random function to get the next move
		boolean	valid_position=false;
		int answer=-1;
		while(!valid_position)
		{	
			answer = rn.nextInt(9)+1;
			if(!existed[answer-1].value)//check if the given random position is not already filled-up
				valid_position=true;
		}
		System.out.println("computer chose "+answer);
		return answer;
	}
	public static void main(String args[])
	{
		Scanner sc= new Scanner(System.in);
		int last_position=-1;
		for(int i=0;i<9;i++)
		{			
			existed[i]=new XORO();
		}
                final_state=2;
		while((!checkWin(last_position, 'O')) && (!exhausted()))//while the game is drawn or won by someone, the loop executes
		{
			printGame();//print current state
			System.out.println("move (1-9): ");
			int human_position=sc.nextInt();	
			if((existed[human_position-1].value) || (human_position >9) || (human_position <1))//error check for invalid or already filled input in tictactoe game
			{
				System.out.println("Enter another number, position filled or invalid");
				continue;
			}
			existed[human_position-1]= new XORO('X');
			last_position=human_position;
			if((checkWin(last_position,'X')) || (exhausted()))//check to ensure the game is not drawn or won after the human input
                        {   if(!exhausted())        
                                final_state=1;
                            break;
                        }    
			int comp_position= getCompPosition();
			existed[comp_position-1]= new XORO('O');
			last_position=comp_position;
                        
		}
                printGame();
                if(final_state==1)
                    System.out.println("Human wins!!");
                else if(final_state==2)
                    System.out.println("Computer WIns!!");
                else
                    System.out.println("Draw");

	}
}