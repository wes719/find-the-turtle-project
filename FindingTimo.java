package bigAssignment;



//=======================================================================================================
//Program Name: FindingTimo
//Author: Wesley Liu
//Date: 2022/06/16
//Java (Neon.1a Release (4.6.1))
//=======================================================================================================
//Problem Definition - Required to create a game where the user navigates through a grid to find Timo: a lost turtle. The advanced solve features a 15x15 grid, moving police, walls, pitfalls, a moving (and visible) timo,
//and much more
//Input: Player's name, menu input options (such as w,a,s,d,1,2,3)
//Output: Game Title, Game Instructions, User Friendly Prompts, Menus, Gameboard, Highscores, Run animation
//Process: Use a two dimensional array to store the gameboard and use one dimensional arrays to hold the coordiantes for Timo, the player, and the police.
//Use methods to perform a variety of tasks such as moving the player in a desired direction, getting input, and printing out/setting up the gameboard.
//use many more arrays to serve different purposes, such as a 3d array to store the game's history
//=======================================================================================================
/*LIST OF IDENTIFIERS
 * Let SIZE represent the dimensions of the gameboard - Type: Integer (constant)
 * Let timoPosition represent Timo's position on the gameboard, storing both coordinates - Type: 1D Integer Array
 * Let playerPosition represent the player's position on the gameboard, storing both coordinates - Type: 1D Integer Array
 * Let currentPlayerPosition represent the player's position on the gameboard, storing both coordinates - Type: 1D Integer Array
 * Let policePosition represent a cop's position on the gameboard, storing both coordinates - Type: 1D Integer Array
 * Let policePosition2 represent a second cop's position on the gameboard, storing both coordinates - Type: 1D Integer Array
 * Let gameBoard represent the game's grid layout, storing data from each cell - Type: 2D String Array
 * Let timoWalked represent up to 10 spots timo has visited recently. Type: 2D int array 
 * Let illegalSpaces represent positions on the gameboard where pitfalls shouldn't spawn to prevent the creation of enclosed spaces
 * Let validMove represent whether a desired change in the player's position is valid or not (in bounds) - Type: Boolean
 * Let policeValidMove represent whether a cop's attempted move is valid of not - Type: Boolean
 * Let timoValidMove represent whether Timo's attempted move is valid or not - Type: Boolean
 * Let hitPitfall represent whether a player has come into contact with a pitfall tile - Type: Boolean
 * Let stopAnimation represent whether the playback animation should continue playing or stop - Type: Boolean
 * Let maxLeaderboard represent whether the current leaderboard is full (10 people) - Type: Boolean
 * Let moves represent the player's current amount of moves - Type: Integer
 * Let timoMoves represent how many times timo has moved, used to help store timo's recently walked path - Type: Integer
 * Let count represent an accumulator that helps with storing illegal tiles - Type: Integer
 * Let count2 represent an accumulator that helps with storing the game's history - Type: Integer
 * Let moves be an accumulator that increases when the player makes a move, representing how many moves the player has used - Type: Integer
 * let timoX and timoY represent randomly generated numbers that together serve as coordinates for Timo's position on the gameboard - Type: Integer
 */



import java.io.*;//allow access to coding within the IO Library
import java.time.LocalTime;//allow access to the LocalTime object
import java.util.Random;//allow access to the Java.util.Random class to generate random values
import java.util.concurrent.TimeUnit;//allow access to the TimeUnit class
public class FindingTimo {//start of FindingTimo class

	final int SIZE = 20;//declaration of class variables

	int [] timoPosition;
	int [] playerPosition;
	int [] currentPlayerPosition;
	int [] policePosition;
	int [] policePosition2;

	String gameBoard [][] = new String [SIZE][SIZE];
	int [][] timoWalked = new int [10][2];
	int [][]illegalSpaces = new int [1500][2];

	boolean validMove=false;
	boolean policeValidMove=false;
	boolean timoValidMove=false;
	boolean hitPitfall=false;
	boolean stopAnimation=false;
	boolean maxLeaderboard=false;
	int moves=0;
	int timoMoves=0;
	int count=0;
	int count2=0;

	/**main method:
	 * This procedural method is called automatically and is used to organize the calling of other methods defined in the class
	 * 
	 * List of Local Identifiers
	 * fT - an object used to get access to the non-static methods and variables defined in the class <type FindingTimoBase>
	 * myFile - an object used to read and write from a text file
	 * moveDecision - used to represent the player's choice on which direction to move/which develop option to execute <type String>
	 * name - used to represent the player's username <type String>
	 * decision - used to represent the user's choice in the opening menu <type: int>
	 * postGameDecision - used to represent the user's choice in the post game menu <type: int>
	 * highScoreDecision - used to represent the user's choice on whether to view the highscores <type:int>
	 * 
	 * playAgain - used to represent the player's choice on whether to repeat the program <type boolean>
	 * masterPlayAgain - used to represent the user's choice if another player wants to play the game <type boolean>
	 * caught - used to represent whether the player has been caught by a police <type: boolean>
	 * wall - used to represent a block of certain tiles that are impassible during gameplay <type: int [][]>
	 * wall2 - used to represent a block of certain tiles that are impassible during gameplay <type: int [][]>
	 * pitfalls: used to represent certain cells that act as pitfalls on the gameboard <type: int [][]>
	 * highScoreList - used to represent up to 10 top scores <type: String[][]>
	 * animation - used to represent all updates to the gameboard in a run. An array of 2d gameboards <type: String[][][]>
	 * startTime - used to represent an extremely precise time in which the run starts <type: double>
	 * checkpointTime - used to represent an extremely precise time in which an updated gameboard is printed <type: double>
	 * elapsedTime - used to represent difference in time between the startTime and checkpointTime to display a timer when the gameboard is displayed <type: double>
	 * seconds - used to represent the elapsedTime in unit seconds instead of nanoseconds
	 * giveUp - used to represent if the player wishes to give up <type boolean>
	 * cheat - used to represent if the player decided to automatically win <type: boolean>
	 * TIMO - used to represent timo's appearance on the gameboard <type: String (constant)>
	 * POLICE - used to represent the police's appearance on the gameboard <type: String (constant)>
	 * WALL - used to represent a wall's appearance on the gameboard <type: String (constant)>
	 * PITFALL - used to represent a pitfall's appearance on the gameboard <type: String (constant)>
	 * 
	 * @param args <type String>
	 * @throws IOException, InterruptedException
	 * @return void
	 * 
	 */


	public static void main(String[] args) throws IOException, InterruptedException {//main method header

		System.out.println("                             ___-------___\r\n"
				+ "                         _-~~             ~~-_\r\n"
				+ "                      _-~                    /~-_\r\n"
				+ "   /^\\__/^\\         /~  \\                   /    \\\r\n"
				+ " /|  O|| O|        /      \\_______________/        \\\r\n"
				+ "| |___||__|      /       /                \\          \\\r\n"
				+ "|          \\    /      /                    \\          \\\r\n"
				+ "|   (_______) /______/                        \\_________ \\\r\n"
				+ "|         / /         \\                      /            \\\r\n"
				+ " \\         \\^\\\\         \\                  /               \\     /\r\n"
				+ "   \\         ||           \\______________/      _-_       //\\__//\r\n"
				+ "     \\       ||------_-~~-_ ------------- \\ --/~   ~\\    || __/\r\n"
				+ "       ~-----||====/~     |==================|       |/~~~~~\r\n"
				+ "        (_(__/  ./     /                    \\_\\      \\.\r\n"
				+ "               (_(___/                         \\_____)_)");



		FindingTimo fT = new FindingTimo();
		File myFile = new File ("E:\\FindingTimo\\highscores.txt");//Declaration and instantiation of a new object named myFile and including a speficied file path


		String moveDecision; //declaration and initialization of various variables outlined above
		String name;
		int decision;
		int postGameDecision;
		int highScoreDecision;
		boolean playAgain=false;
		boolean masterPlayAgain=false;
		boolean caught=false;
		int [][]wall = null;
		int [][]wall2=null;
		int [][]pitfalls=new int [20][2];
		String [][]highScoreList =new String[10][2];
		String [][][]animation = new String [100][fT.SIZE][fT.SIZE];
		double startTime;
		double checkpointTime;
		double elapsedTime;
		double seconds;
		boolean giveUp=false;
		boolean cheat=false;

		final String TIMO = "OOO";
		final String POLICE = "!!!";
		final String WALL = "///";
		final String PITFALL = "XXX";

		fT.displayIntro();//displayIntro method is used to display the game's title

		do{
			name=fT.getName();

			do {//start of do while loop
				System.out.println("________________________________________________________________________________________________________________________________________");//UFP
				System.out.println("Hello "+name+", would you like to:");
				System.out.println("________________________________________________________________________________________________________________________________________");
				System.out.println("1. Read instructions/tutorial");
				System.out.println("2: See All Time Highscores");
				System.out.println("3. Play Game");


				decision=fT.getDecision(); //decision is set equal to the returned value from the getDecision method
				System.out.println("________________________________________________________________________________________________________________________________________");
				if (decision==1) {//if statement is used to test if decision is 1
					fT.tutorial();//Tutorial method is executed
				}else if (decision==2) {//else if statement is used to test if decision is 2
					highScoreList=fT.returnScores(myFile); //highScoreList is set equal to the returned value from the returnScores method
					highScoreList=fT.sortScores(highScoreList);//highScoreList is set equal to the returned value from the sortScore method
					fT.displayHighScores(highScoreList);//displayHighScores method displays the top scores, passing in highScoreList
				}//end of if/else if block

			}while (decision!=3);//end of do while loop that loops when decision is not 3



			do{
				startTime = LocalTime.now().toNanoOfDay();//set startTime equal to the precise current time
				fT.playerPosition = new int[]{0,0};
				wall=fT.setUpWall(wall);//wall is set up using the setUpWall method
				wall2=fT.setUpWall(wall);//wall2 is set up using the setUpWall method
				pitfalls=fT.generatePitfalls(pitfalls, wall, wall2);//pitfalls are set up using the generatePitfalls method passing in the pitfalls array and both recently generated walls

				fT.gameBoard = fT.boardSetup(fT.gameBoard,wall,wall2,pitfalls, WALL, PITFALL, TIMO, POLICE);//uses the boardSetup method (passing in many parameters)and assigns the returned value to gameBoard
				fT.drawBoard (fT.gameBoard,name,0);//prints the current board configuration using the drawBoard method passing in various parameters




				while(((fT.playerPosition[0]!=fT.timoPosition[0])||(fT.playerPosition[1]!=fT.timoPosition[1]))&& !caught) {//while loop loops when the player hasn't found timo and is not caught
					System.out.println("Would you like to:       Developer Options:");//UFP
					System.out.println("w: Move up               1. Win");
					System.out.println("a: Move left             2. Give Up");
					System.out.println("s: Move down");
					System.out.println("d: Move right");
					moveDecision=fT.getMoveDecision();//sets moveDecision to the returned value from the getMoveDecision method
					fT.policeMove(fT.policePosition, WALL, PITFALL, POLICE);//policePosition and other parameters are passed through the policeMove method
					fT.policeValidMove=false;//policeValidMove is set to false
					fT.policeMove(fT.policePosition2, WALL, PITFALL, POLICE);//policePosition2 and other parameters are passed through the policeMove method
					fT.policeValidMove=false;//policeValidMove is set to false
					fT.timoMove(fT.timoPosition, WALL, PITFALL, TIMO);//timoPosition and other parameters are passed through the timoMove method
					fT.timoMoves++;//timoMoves is accumulated
					fT.collectWalkedPath();//collectWalkedPath method is executed

					fT.timoValidMove=false;//timoValidMove is set to false


					switch (moveDecision) {//switch case handles the different possibilities for moveDecision
					case "w":
						fT.moveUp(fT.playerPosition, WALL, PITFALL, TIMO, POLICE); //moveUp method is executed, passing in the player's position as well as various other parameters
						if(fT.validMove){
							fT.moves++;
						}
						fT.validMove=false;
						break;
					case "a":
						fT.moveLeft(fT.playerPosition, WALL, PITFALL, TIMO, POLICE);//moveLeft method is executed, passing in the player's position as well as various other parameters
						if(fT.validMove){
							fT.moves++;
						}
						fT.validMove=false;
						break;
					case "s":
						fT.moveDown(fT.playerPosition, WALL, PITFALL, TIMO, POLICE);//moveDown method is executed, passing in the player's position as well as various other parameters
						if(fT.validMove){
							fT.moves++;
						}
						fT.validMove=false;
						break;
					case "d":
						fT.moveRight(fT.playerPosition, WALL, PITFALL, TIMO, POLICE);//moveRight method is executed, passing in the player's position as well as various other parameters
						if(fT.validMove){
							fT.moves++;
						}
						fT.validMove=false;
						break;
					case "1":
						fT.moves++;
						fT.playerPosition[0]=fT.timoPosition[0];
						fT.playerPosition[1]=fT.timoPosition[1];
						cheat=true;

						break;

					case "2":
						giveUp=true;
						break;
					}

					if (giveUp) {
						break;
					}


					if ((fT.playerPosition[0]==fT.policePosition[0])&&(fT.playerPosition[1]==fT.policePosition[1])) { //if statement checks if the player is in the same spot as the police

						caught=true;//caught is set equal to true
					}else if ((fT.playerPosition[0]==fT.policePosition2[0])&&(fT.playerPosition[1]==fT.policePosition2[1])) {//if statement checks if the player is in the same spot as the second police
						caught=true;//caught is set equal to true

					}//end of if/else if block


					checkpointTime = LocalTime.now().toNanoOfDay();//checkpointTime is set equal to the precise moment in time
					elapsedTime=checkpointTime-startTime;//elapsedTime is calculated by finding the difference between the checkpoint time and the starting time
					seconds = (int)(elapsedTime /1000000000);//seconds is calculated by converting the nanosecond value of elapsed time into seconds by doing division. It is also then casted into an integer for simplicity
					if (!cheat) {//checks if the player cheated
						fT.drawBoard(fT.gameBoard,name,seconds);//drawBoard method prints the gameboard. name and seconds is also passed into the method
						fT.addFrame(animation);//addFrame method is executed, passing in animation as a parameter

					}//end of if statement

				}//end of while loop

				if (giveUp) {//checks to see whether the player has given up
					System.out.println("You have forfeited the game");//UFP

				}else if(!caught || cheat) {//else if statement proceeds if the player is not caught or cheated
					cheat=false;//cheat is reset to false
					fT.displayEndMessage(name);//displayEndMessage is executed, passing in name
					fT.maxLeaderboard=fT.checkFile(myFile);//maxLeaderboard is set equal to the returned boolean value from the checkFile method passing in myFile. 

					if (!fT.maxLeaderboard) {//if statement proceeds if maxLeaderboard is false

						try {
							highScoreList=fT.returnScores(myFile);//highScoreList is set equal to the returned value from the returnScores method passing in myFile
							highScoreList=fT.sortScores(highScoreList);//highScoreList is set equal to the returned value from the sortScores method passing in highScoreList

							fT.getScore(highScoreList,name, Integer.toString(fT.moves));//getScore method is executed, passing in various parameters

							highScoreList=fT.sortScores(highScoreList);//highScoreList is set equal to the returned value from the sortScores method passing in highScoreList
						}catch(Exception e) {

							}
						}else {//end of if block, else statement is used to direct remaining possibilities
							highScoreList=fT.returnScores(myFile);//highScoreList is set equal to the returned value from the returnScores method passing in myFile
							highScoreList=fT.sortScores(highScoreList);//highScoreList is set equal to the returned value from the sortScores method passing in highScoreList
							fT.getScoreWhenMax(highScoreList,name, Integer.toString(fT.moves));//getScoreWhenMax method is executed, passing in various parameters

						}//end of if/else block


					}else {//else statement used to direct remaining possibilities
						System.out.println(name+ ", YOU HAVE BEEN CAUGHT BY THE POLICE!");//UFP

					}//end of if/else if/else block

					System.out.println("________________________________________________________________________________________________________________________________________");//UFP
					System.out.println(name+", would you like to:");
					System.out.println("1. View Run Animation");
					System.out.println("2. View Highscores");
					System.out.println("3. Play again as "+name);
					System.out.println("4. Exit");

					postGameDecision=fT.getDecision();//postGameDecision is set equal to the returned value from the getDecision method
					System.out.println("________________________________________________________________________________________________________________________________________");
					while (postGameDecision!=3 &&postGameDecision!=4 ){//while loop loops when postGameDecision isn't 3 or 4 
						if (postGameDecision==1){//if statement checks if postGameDecision is 1

							fT.playAnimation(animation);//playAnimation method is executed, passing in animation
						}else if (postGameDecision==2){//end of if statement, else if statement checks if postGameDecision is 2

							highScoreList=fT.returnScores(myFile); //highScoreList is set equal to the returned value from the returnScores method
							highScoreList=fT.sortScores(highScoreList);//highScoreList is set equal to the returned value from the sortScore method
							fT.displayHighScores(highScoreList);//displayHighScores method displays the top scores, passing in highScoreList
						}
						System.out.println("________________________________________________________________________________________________________________________________________");//UFP
						System.out.println(name+", would you like to:");
						System.out.println("1. View Run Animation");
						System.out.println("2. View Highscores");
						System.out.println("3. Play again as "+name);
						System.out.println("4. Exit");
						postGameDecision=fT.getDecision();//postGameDecision is set equal to the returned value from the getDecision method
						System.out.println("________________________________________________________________________________________________________________________________________");
					}//end of while loop

					if (postGameDecision==3){//if statement checks if postGameDecision is 3
						playAgain=true;//sets playAgain to true
					}//end of if statement
					else if (postGameDecision==4){//else if statement checks if postGameDecision is 1
						playAgain=false;//playAgain is set to false
					}//end of if/else if block


					fT.moves=0;//moves is reset to 0
					fT.timoWalked=fT.resetArray(fT.timoWalked);//timoWalked is "reset" or set equal to the returned value from the resetArray method after passing in timoWalked
					fT.illegalSpaces=fT.resetArray(fT.illegalSpaces);//illegalSpcaes is "reset" or set equal to the returned value from the resetArray method after passing in illegalSpaces
					animation=fT.reset3DArray(animation);//animation is "reset" or set equal to the returned value from the reset3DArray method after passing in animation
					fT.count2=0;//count 2 is reset to 0
					fT.count=0;//count is reset to 0
					caught=false;//caught is reset to false
					cheat=false;//cheat is reset to false
					giveUp=false;//giveUp is reset to false
				}while(playAgain);//end of do while loop that loops when playAgain is true
				System.out.println("Farewell, "+ name+", thanks for playing!");//UFP
				System.out.println("Would another player like to play? 1: Yes, 2: No");//UFP
				masterPlayAgain=fT.getMasterPlayagain();//masterPlayAgain is set equal to the returned value from the getMasterPlayAgain method
			}while (masterPlayAgain);//end of do while loop that loops while masterPlayAgain is true
			System.out.println("Thanks for using this program!");//UFP


		}//end of main method


		/** sortScoresMethod:
		 * 
		 * This functional method is used to bubble sort the leaderboard so that it is properly ordered
		 * 
		 * LOCAL VARIABLES
		 * tempScore - used to temporarily store a certain score value while swapping <type: integer>
		 * tempName - used to temporarily store a certain name value while swapping <type:string>
		 * 
		 * @param leaderBoard<type: String [][]>
		 * @return leaderBoard <type: String [][]>
		 */
		public String [][] sortScores (String [][]leaderBoard){//start of sortScores method
			int tempScore;//initialization of local variables described above
			String tempName;
			for (int x=0; x<leaderBoard.length-1;x++) {//double for loop used to go through the two dimensional array
				for (int y=0; y<leaderBoard.length-x-1;y++) {
					if (Integer.parseInt(leaderBoard[y][1])>Integer.parseInt(leaderBoard[y+1][1])) {//checks to see if a certain score is higher than another
						tempName = leaderBoard[y][0];//tempName is set equal to a certain corresponding name
						tempScore = Integer.parseInt(leaderBoard[y][1]);//tempScore is set equal to a certain parsed score

						leaderBoard[y][0]=leaderBoard[y+1][0];//names are swapped
						leaderBoard[y][1]=leaderBoard[y+1][1];//scores are swapped

						leaderBoard[y+1][0]=tempName;//the value of tempName is placed into the leaderBoard[y+1][0]
						leaderBoard[y+1][1]=Integer.toString(tempScore);//the value of tempScore (after being converted to an integer) is placed into leaderBoard[y+1][1]
					}//end of if statement
				}//end of for loop
			}//end of for loop
			return leaderBoard;//returns the sorted leaderboard
		}//end of sortScores method

		/**checkFile method:
		 * 
		 * This functional method is used to determine the size of the text file, and if it has reached a maximum for 10 names and 10 scores
		 * 
		 * LOCAL VARIABLES
		 * br - an object used to read from the file <type: BufferedReader>
		 * count - used as an accumulator to count the lines in the text file <type: integer>
		 * test - used simply to move in the text file from line to line and to test if a line is null <type: String>
		 * 
		 * @param file <Type: file>
		 * @return true or false - depending on the size of the text file
		 * @throws IOException
		 */

		public boolean checkFile (File file) throws IOException{//start of the checkFile method

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));//declaration and instantiation of a BufferedReader object used to read the file
				int count=1;//initiation of various local variables outlined above
				String test;
				do {//start of a do while loop
					test = br.readLine();//test is set equal to a line in the file
					count ++;//count is accumulated
				} while (test != null);//end of do while loop, loops while the reader doesn't encounter a null line

				if (count >20) {//if statement checks if count is greater than 20
					return true;//returns true
				}else {//end of if statement, else statement directs remaining possiblities
					return false;//false is returned
				}//end of if/else block
			}catch(Exception e) {
				System.out.println("Error when trying to upload score");
			}
			return false;
		}//end of checkFile method


		/** getScore method:
		 * 
		 * This procedural method is used to add a score by writing it into the text file. This method is also responsible for taking the existing highScoreList and 
		 * writing it into the text file
		 * 
		 * LOCAL VARIABLES
		 * 
		 * myWriter - an object used to write in the file <type: FileWriter>
		 * 
		 * @param highScoreList <type: String [][]>
		 * @param name <type: String>
		 * @param count <type: String>
		 * @throws IOException
		 * @return void
		 */

		public void getScore(String highScoreList[][],String name, String count) throws IOException {//start of getScore method

			FileWriter myWriter = new FileWriter("E:\\FindingTimo\\highscores.txt");//declaration and instantiation of a FileWriter object named myWriter

			try {//start of a try catch block for error handling
				for (int x=0; x<highScoreList.length; x++) {//for loop runs through the entire leaderboard
					if (highScoreList[x][0]==null) {//if statement used to check if the leaderboard contains a null element
						break;//immediately breaks out of the for loop
					}//end of if statement
					myWriter.write(highScoreList[x][0]);//writes a certain name to the text file
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line
					myWriter.write(highScoreList[x][1]);//writes a certain score to the text file
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line

				}//end of for loop

				myWriter.write(name);//writes name to the text file
				myWriter.write(System.getProperty( "line.separator" ));//writes a new line
				myWriter.write(count);//writes count to the text file
				myWriter.write(System.getProperty( "line.separator" ));//writes a new line
				myWriter.close();//closes the writer

			} catch (IOException e) {//catch statement used to direct errors
				System.out.println("An error occurred.");//UFP

			}//end of try catch block
		}//end of getScore method

		/** getScoreWhenMax method:
		 * 
		 * This procedural method is used to potentially add a score by writing it into the text file. This method is also responsible for taking the exisiting highScoreList and 
		 * writing it into the text file
		 * 
		 * LOCAL VARIABLES
		 * 
		 * myWriter - an object used to write in the file <type: FileWriter>
		 * 
		 * @param highScoreList <type: String [][]>
		 * @param name <type: String>
		 * @param count <type: String>
		 * @throws IOException
		 * @return void
		 */

		public void getScoreWhenMax(String highScoreList[][],String name, String count) throws IOException {//start of getScoreWhenMax method

			FileWriter myWriter = new FileWriter("E:\\FindingTimo\\highscores.txt");//declaration and instantiation of a FileWriter object named myWriter

			try {//start of a try catch block for error handling
				for (int x=0; x<highScoreList.length-1; x++) {//for loop runs through the entire leaderboard
					if (highScoreList[x][0]==null) {//if statement used to check if the leaderboard contains a null element
						break;//immediately breaks out of the for loop
					}//end of if statement

					myWriter.write(highScoreList[x][0]);//writes a certain name to the text file
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line
					myWriter.write(highScoreList[x][1]);//writes a certain score to the text file
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line


				}//end of for loop

				if (Integer.parseInt(count)<Integer.parseInt(highScoreList[highScoreList.length-1][1])){ //if statement used to check if count is less than the existing worst leaderboard score


					myWriter.write(name);//writes name to the text file
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line
					myWriter.write(count);//writes count to the text file
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line
					myWriter.close();//closes the writer
				}else{//end of if statement, else statement used to direct remaining possibilities

					myWriter.write(highScoreList[highScoreList.length-1][0]);//writes the existing name with the worst leaderboard score
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line
					myWriter.write(highScoreList[highScoreList.length-1][1]);//writes the existing worst leaderboard score
					myWriter.write(System.getProperty( "line.separator" ));//writes a new line
					myWriter.close();//closes the writer

				}//end of if/else block

			} catch (IOException e) {//catch statement used to direct errors
				System.out.println("An error occurred.");//UFP

			}//end of try catch block
		}//end of getScoreWhenMax method

		/**returnScores method:
		 * 
		 * This functional method reads the data inside of the file and returns a 2D array containing that information
		 * 
		 * LOCAL VARIABLES
		 * fileSize - used to represent the size of the file <type: integer>
		 * highScores - used to store the data inside of the file <type: String [][]>
		 * test - used to move the cursor of the file writer to a new line and test if a line is null <type: String>
		 * 
		 * @param file <type: File>
		 * @return leaderBoard <type: String [][]>
		 * @throws IOException
		 */

		public String [][] returnScores (File file) throws IOException{//start of returnScores method
			String test;//initialization of various variables outlined above;
			int fileSize = 0;
			String [][] leaderBoard = null;


			try {//start of try block for error handling
				BufferedReader br = new BufferedReader(new FileReader(file));//declaration and instantiation of a BufferedReader object br used to read from the file

				do {//start of do while loop
					test = br.readLine();//test is set equal to a line in the file
					fileSize ++;//fileSize is accumulated
				} while (test != null);//end of do while loop, loop loops when test is not null
				fileSize=fileSize/2;//fileSize is halved
				leaderBoard = new String [fileSize][2];//leaderBoard is defined to have size fileSize/2 and 2
				br = new BufferedReader(new FileReader(file)); //reset buffered Reader

				for (int i = 0; i < fileSize; i ++) {
					leaderBoard [i][0] = br.readLine(); //get the name
					leaderBoard [i][1] = br.readLine(); //get the corresponding score
				}
			} catch (FileNotFoundException e) {//catch statement used to direct errors
				System.out.println ("An error occured");//UFP

			}//end of try catch block

			return leaderBoard;//returns the leaderBoard array
		}//end of returnScores method

		/**method BoardSetup:
		 * 
		 * This functional method is used to set up the gameboard at the beginning of each game
		 * 
		 * LOCAL VARIABLES
		 * 
		 * rand - an object used to generate random numbers <from java.util.Random>
		 * timoX - used to represent timo's first coordinate <type: integer>
		 * timoY - used to represent timo's second coordinate <type: integer>
		 * policeX - used to represent the first cop's first coordinate <type: integer>
		 * policeY - used to represent the first cop's second coordinate <type: integer>
		 * police2X - used to represent the second cop's first coordinate <type: integer>
		 * police2Y - used to represent the second cop's second coordinate <type: integer>
		 * 
		 * @param gameBoard <type: String [][]>
		 * @param wall <type: integer [][]>
		 * @param wall2 <type: integer [][]>
		 * @param pitfalls <type: integer [][]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @param POLICE <type: String>
		 * @return gameBoard <type: String [][]>
		 */



		public String [][] boardSetup (String[][]gameBoard, int [][]wall, int [][]wall2, int [][]pitfalls, String WALL, String PITFALL, String TIMO, String POLICE){//start of boardSetup method
			Random rand = new Random();//declaration and instantiation of a new object named rand
			int timoX;//initialization of various local variables outlined above
			int timoY;
			int policeX;
			int policeY;
			int police2X;
			int police2Y;

			do {//start of do while loop
				timoX = rand.nextInt(SIZE);//timoX is randomly generated from 0 to SIZE-1
				timoY = rand.nextInt(SIZE);//timoY is randomly generated from 0 to SIZE-1
				timoPosition=new int []{timoX,timoY};//timoPosition is declared with the randomized values
			}while ((timoX==0 && timoY ==0)||checkExist(wall,timoPosition)||checkExist(wall2,timoPosition)||checkExist(pitfalls,timoPosition));//end of while loop that loops if the generated values clash with certain conditions

			do {//start of do while loop
				policeX = rand.nextInt(SIZE);//policeX is randomly generated from 0 to SIZE-1
				policeY = rand.nextInt(SIZE);//policeY is randomly generated from 0 to SIZE-1
				policePosition=new int[] {policeX, policeY};//policePosition is declared with the randomized values
			}while ((policeX==0 && policeY==0) || (policeX==timoX && policeY==timoY) ||checkExist(wall,policePosition)||checkExist(wall2,policePosition)||checkExist(pitfalls,timoPosition));//end of while loop that loops if the generated values clash with certain conditions
			do {//start of do while loop
				police2X = rand.nextInt(SIZE);//police2X is randomly generated from 0 to SIZE-1
				police2Y = rand.nextInt(SIZE);//police2Y is randomly generated from 0 to SIZE-1
				policePosition2=new int[] {police2X, police2Y};//policePosition2 is declared with the randomized values
			}while ((police2X==0 && police2Y==0) || (police2X==timoX && police2Y==timoY) || (police2X==policeX && police2Y==policeY)||checkExist(wall,policePosition2)||checkExist(wall2,policePosition2)||checkExist(pitfalls,timoPosition));
			//end of while loop that loops if the generated values clash with certain conditions
			for (int x=0; x<gameBoard.length;x++){//double for loop runs through entire gameBoard array
				for (int y=0; y<gameBoard[x].length;y++){
					int position[]={x,y};//position is set equal to {x,y}
					if (checkExist(wall,position)||checkExist(wall2,position)){//if statement and checkExist methods are used to determine where to assign walls
						gameBoard[x][y]=WALL;//a certain tile is assigned to be a wall
					}else if(checkExist(pitfalls,position)){//else if statement and checkExist method is used to determine where to plant pitfalls
						gameBoard[x][y]=PITFALL;//a certain tile is assigned to be a pitfall

					}//end of else if statement
					else{//else statement used to direct remaining possibilities
						gameBoard[x][y]="[?]";//the rest is filled with question marks
					}//end of if/else if/else block
				}//end of for loop

			}//end of for loop

			policePosition2=new int[] {police2X, police2Y};//policePosition is declared with the randomized values

			gameBoard[timoX][timoY]=TIMO;//a certian randomized tile is assigned to be TIMO
			timoWalked[0][0]=timoX;//sets up the first tile that timo walked on
			timoWalked[0][1]=timoY;//sets up the first tile that timo walked on

			gameBoard[0][0]="[X]";//sets the player's position to be visible on the top left
			gameBoard[policeX][policeY]=POLICE;//sets the police's position to be visible on the gameboard
			gameBoard[police2X][police2Y]=POLICE;//sets the second police's position to be visible on the gameboard

			return gameBoard;//returns the set up gameboard

		}//end of boardSetup method

		/**collectIllegalTiles method:
		 * 
		 * This procedural method is in charge of collecting "illegal tiles" for pitfalls to spawn, which are the 9 spaces surrounding a tile.
		 * The purpose for collecting such tiles is to prevent possible enclosed spaces. 
		 * 
		 * @param x <type: integer>
		 * @param y <type: integer>
		 */

		public void collectIllegalTiles (int x, int y){ //start of collectIllegalTiles method
			illegalSpaces[count][0]=x-1; //adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y;
			count++;//count is accumulated					
			illegalSpaces[count][0]=x+1;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y;
			count++;//count is accumulated
			illegalSpaces[count][0]=x;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y+1;
			count++;//count is accumulated
			illegalSpaces[count][0]=x;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y-1;
			count++;//count is accumulated
			illegalSpaces[count][0]=x-1;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y-1;
			count++;//count is accumulated
			illegalSpaces[count][0]=x-1;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y+1;
			count++;//count is accumulated
			illegalSpaces[count][0]=x+1;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y-1;
			count++;//count is accumulated
			illegalSpaces[count][0]=x+1;//adds one of the surrounding tiles to the illegalSpaces array
			illegalSpaces[count][1]=y+1;
			count++;//count is accumulated

		}//end of collectIllegalTiles method\


		/**generatePitfalls method:
		 * 
		 * This functional method randomly generates pitfall coordinates while taking the walls into consideration.
		 * This method uses lots of bulletproofing to minimize the chances of a pitfall spawning in a bad position
		 * 
		 * LOCAL VARIABLES
		 * rand - an object used to generate random numbers <from java.util.Random>
		 * x - used to represent the first coordinate for a pitfall <type: Integer>
		 * y - used to represent the second coordinate for a pitfall <type: Integer>
		 * @param pitfalls <type: Integer [][]>
		 * @param wall <type: Integer [][]>
		 * @param wall2 <type: Integer [][]>
		 * @return pitfalls <type: Integer [][]>
		 */

		public int [][] generatePitfalls (int [][]pitfalls, int [][]wall, int [][]wall2){//start of generatePitfalls method
			Random rand = new Random();//declaration and instantiation of a new object named rand
			int x;//initialization of various local variables outlined above
			int y;

			for (int i=0; i<pitfalls.length; i++){//for loop runs through the pitfalls array
				do{//start of do while loop
					x=rand.nextInt(SIZE);//x is randomly generated from 0 - SIZE-1
					y=rand.nextInt(SIZE);//y is randomly generated from 0 - SIZE-1
					pitfalls[i][0]=x;//x and y are stored inside of the pitfalls array
					pitfalls[i][1]=y;
				}while ((x==0 && y==0)|| x==0 || y==0|| y==SIZE-1 || x==SIZE-1 || checkExist(wall,pitfalls[i])||checkExist(wall2,pitfalls[i])||checkExist(illegalSpaces,pitfalls[i]));//end of do while loop, many conditions are used
				//to minimize the chances of a pitfall spawning in a bad position

				collectIllegalTiles(x,y);//execute collectIllegalTiles method on the generated pitfall
			}//end of for loop

			return pitfalls;//return pitfalls array
		}//end of generatePitfalls method


		/**addFrame method:
		 * 
		 * This functional method is used to add a "frame" to a 3D array consisting of all of game's previous gameboards
		 * 
		 * 
		 * @param animation <type: String [][][]>
		 * @return animation <type: String [][][]>
		 */

		public String [][][] addFrame (String [][][]animation){//start of addFrame method
			for (int x=0; x<gameBoard.length;x++){//double for loop runs through the current gameboard
				for (int y=0; y<gameBoard[x].length;y++){
					animation[count2][x][y]=gameBoard[x][y];//this line copies the current gameboard into index count2 of animation

				}//end of for loop
			}//end of for loop
			count2++;//count2 accumulates
			return animation;//animation array is returned
		}//end of addFrame method

		/**playAnimation method:
		 * 
		 * This procedural method takes a 3D array full of gameboards from the game's history and prints them
		 * at intervals to create something that looks like a moving animation
		 * 
		 * @param animation <type: String [][][]>
		 * @throws InterruptedException
		 */

		public void playAnimation (String [][][]animation)throws InterruptedException{ //start of playAnimation method
			for (int y=0; y<50; y++){//for loop loops 30 times
				System.out.println("");//prints a new line to create space between the frames
			}//end of for loop
			for (int x=0; x<animation.length; x++){//for loop goes through the 3D array frame by frame


				printArray(animation[x]);//printArray method is used to print the 2D gameboard in animation[X]
				testArray(animation[x+1]);//testArray is used to test the upcoming frame
				if (!stopAnimation){//if statement tests if stopAnimation is false

					for (int y=0; y<50; y++){//for loop loops 30 times
						System.out.println("");//prints a new line to create space between the frames
					}//end of for loop
				}else{//end of if statement, else statement is used to direct remaining possibilities
					System.out.println("END OF ANIMATION");//UFP
					stopAnimation=false;//stopAnimation is reset to false
					return;//breaks out of method
				}//end of if/else block
			}//end of for loop
		}//end of playAnimation method

		/**policeMove method:
		 * 
		 * This procedural method is used to randomly move a police in a direction
		 * 
		 * LOCAL VARIABLES
		 * 
		 * rand - an object used to generate random numbers <from java.util.Random>
		 * x - a randomly generated value that determines what direction the police moves <type: integer>
		 * 
		 * @param policePosition <type: int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void policeMove (int []policePosition, String WALL, String PITFALL, String POLICE){//start of policeMove method
			Random rand = new Random();//declaration and instantiation of a new object named rand

			while (!policeValidMove) {//while loop loops when policeValidMove is false
				int x = rand.nextInt(4);//x is assigned to a random value from 0-3
				switch (x) {//switch case is used to manage the different possibilities of x
				case 0://checks if x is 0
					moveRightPolice(policePosition, WALL, PITFALL, POLICE);//moveRightPolice is executed, with four parameters being passed through
					break;//breaks out of switch case
				case 1://checks if x is 1
					moveLeftPolice(policePosition, WALL, PITFALL, POLICE);//moveLeftPolice is executed, with four parameters being passed through
					break;//breaks out of switch case
				case 2://checks if x is 2
					moveUpPolice(policePosition, WALL, PITFALL, POLICE);//moveUpPolice is executed, with four parameters being passed through
					break;//breaks out of switch case
				case 3://checks if x is 3
					moveDownPolice(policePosition, WALL, PITFALL, POLICE);//moveDownPolice is executed, with four parameters being passed through
					break;//breaks out of switch case
				}//end of switch case

			}//end of while loop
		}//end of policeMove method

		/**timoMove method:
		 * 
		 * This procedural method is used to move Timo in a random direction
		 * 
		 * LOCAL VARIABLES
		 * rand - an object used to generate random numbers <from java.util.Random>
		 * x - a randomly generated value that determines what direction Timo moves <type: integer>
		 * count - used as an accumulator and exit condition if Timo is stuck, to avoid an infinite loop
		 * 
		 * 
		 * @param timoPosition <type: int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @return void
		 */

		public void timoMove (int []timoPosition, String WALL, String PITFALL, String TIMO){ //start of timoMove method
			Random rand = new Random();//initialization and instantiation of a new object named rand
			int count=0;//initializing a variable named count to 1
			while (!timoValidMove&&count<20) {//start of while loop that loops when timoValidMove is false and count is less than 20
				int x = rand.nextInt(4);//x is assigned a random value from 0-3
				switch (x) {//switch case is used to manage the possibilities for x
				case 0://check if x is 0
					timoMoveRight(timoPosition, WALL, PITFALL,TIMO);//timoMoveRight method is executed, passing in 4 parameters
					if (!checkExist(timoWalked,timoPosition)){//if statement and checkExist method checks if timo's position exists in the array of timoWalked
						break;//breaks out switch case
					}else{//end if if statement, else statement used to direct remaining possibilities
						if (timoValidMove){// if statement used to check if timoValidMove is true

							timoMoveLeft(timoPosition, WALL, PITFALL,TIMO);//timoMoveLeft is executed, passing in 4 values such that Timo ends not not moving at all
						}//end of if statement
						timoValidMove=false;//timoValidMove is reset to false
					}//end of if/else block

					break;//breaks out of the switch case
				case 1://check if x is 1
					timoMoveLeft(timoPosition, WALL, PITFALL,TIMO);//timoMoveLeft method is executed, passing in 4 parameters
					if (!checkExist(timoWalked,timoPosition)){//if statement and checkExist method checks if timo's position exists in the array of timoWalked
						break;//breaks out switch case
					}else{//end if if statement, else statement used to direct remaining possibilities
						if (timoValidMove){// if statement used to check if timoValidMove is true

							timoMoveRight(timoPosition, WALL, PITFALL,TIMO);//timoMoveRight is executed, passing in 4 values such that Timo ends not not moving at all
						}//end of if statement
						timoValidMove=false;//timoValidMove is reset to false
					}//end of if/else block

					break;//breaks out of the switch case
				case 2://check if x is 2
					timoMoveUp(timoPosition, WALL, PITFALL,TIMO);//timoMoveUp method is executed, passing in 4 parameters
					if (!checkExist(timoWalked,timoPosition)){//if statement and checkExist method checks if timo's position exists in the array of timoWalked
						break;//breaks out switch case
					}else{//end if if statement, else statement used to direct remaining possibilities
						if (timoValidMove){// if statement used to check if timoValidMove is true

							timoMoveDown(timoPosition, WALL, PITFALL,TIMO);//timoMoveDown is executed, passing in 4 values such that Timo ends not not moving at all
						}//end of if statement
						timoValidMove=false;//timoValidMove is reset to false
					}//end of if/else block

					break;//breaks out of the switch case
				case 3://check if x is 3
					timoMoveDown(timoPosition, WALL, PITFALL,TIMO);//timoMoveDown method is executed, passing in 4 parameters
					if (!checkExist(timoWalked,timoPosition)){//if statement and checkExist method checks if timo's position exists in the array of timoWalked
						break;//breaks out switch case
					}else{//end if if statement, else statement used to direct remaining possibilities
						if (timoValidMove){// if statement used to check if timoValidMove is true

							timoMoveUp(timoPosition, WALL, PITFALL,TIMO);//timoMoveUp is executed, passing in 4 values such that Timo ends not not moving at all
						}//end of if statement
						timoValidMove=false;//timoValidMove is reset to false
					}//end of if/else block

					break;//breaks out of the switch case
				}//end of switch case
				count++;//count is accumulated by 1
			}//end of while loop
		}//end of timoMove method

		/**collectWalkedPath method:
		 * 
		 * This procedural method collects timo's walked path, holding up to 10 coordinates.
		 * The method will also update new walked paths and discard old walked paths
		 * 
		 * @return void
		 * 
		 */

		public void collectWalkedPath(){//start of collectWalkedPath method
			if (timoMoves>9) {//if statement checks if timoMoves is greater than 9
				for (int x=0; x<timoWalked.length-1;x++) {//for loop runs through the timoWalked array
					timoWalked[x][0]=timoWalked[x+1][0];//coordinates are shifted up
					timoWalked[x][1]=timoWalked[x+1][1];
				}//end of for loop
				timoWalked[9][0]=timoPosition[0]; //timo's current position becomes newly added to the timoWalked array
				timoWalked[9][1]=timoPosition[1];
			}else {//end of if statement, else statement is used to direct remaining possibilities
				timoWalked[timoMoves][0]=timoPosition[0];//sets timoWalked at a certain index equal to timo's current position
				timoWalked[timoMoves][1]=timoPosition[1];
			}//end of if/else block
		}//end of collectWalkedPath method


		/**moveRight method:
		 * 
		 * 
		 * This procedural method is used to move the player's position to the right, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to test if the player will hit a wall or pitfall <type: int[]>
		 * 
		 * 
		 * @param playerPosition <type: int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveRight (int [] playerPosition, String WALL, String PITFALL, String TIMO, String POLICE){//start of method moveRight

			if(playerPosition[1]!=SIZE-1) {//checks if the player is positioned at an certain edge that would make the move illegal
				int [] check = new int[] {playerPosition[0],playerPosition[1]}; //initiate and declare a new variable that is equal to the player's position
				check[1]=(check[1]+1);//set check's coordinates equal to what the player's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL) {//checks to see if check hit a wall
					System.out.println("You cannot move through a wall");//UFP;
					return;//breaks out of method
				}else if(gameBoard[check[0]][check[1]]==PITFALL) {//end of if statement, else if statement checks if check will hit a pitfall
					if (playerPosition[0]==timoPosition[0] &&playerPosition[1]==timoPosition[1]) {//if statement used in a case where timo moves towards the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=TIMO;//where the player used to be will now display timo
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else if ((playerPosition[0]==policePosition[0] &&playerPosition[1]==policePosition[1])||(playerPosition[0]==policePosition2[0] &&playerPosition[1]==policePosition2[1])) {//end of if statement,
						//else if statement used in a case where the police catches the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=POLICE;//where the player used to be will now display a police
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else {//end of else if statement, else statement used to direct remaining possibilities
						gameBoard[playerPosition[0]][playerPosition[1]]="[ ]";//where the player used to be will now display an empty tile
						gameBoard[0][0]="[X]";//player will now displayed back where it started
					}//end of if/else if/else block
					System.out.println("YOU HIT A PITFALL, YOUR POSITION WILL NOW BE RESET");//UFP
					playerPosition [0]=0;//resets the player's position
					playerPosition [1]=0;
					hitPitfall=true;//hitPitfall is set to true
					return;//breaks out of method
				}//end of if/else if block
				if (gameBoard[playerPosition[0]][playerPosition[1]]==TIMO) {//if statement used for a scenario where timo moves where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=TIMO;//timo will be displayed where the player is before the player moves
					playerPosition[1]=(playerPosition[1]+1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//player's new position will be marked by a [X]
					validMove=true;//validMove is set to true

				}else if (gameBoard[playerPosition[0]][playerPosition[1]]==POLICE) {//end of if statement, else if statement is sued for a scenario where the 
					//police moves to where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=POLICE;//the player's current position will display a police
					playerPosition[1]=(playerPosition[1]+1);//the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}else {//end of if else statement, else statement is used to direct remaining possibilities

					gameBoard [playerPosition[0]][playerPosition[1]]="[ ]";//the player's current tile will display an empty tile
					playerPosition[1]=(playerPosition[1]+1);//the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}//end of if statement

			}else {//else statement for remaining possibilities
				System.out.println("Invalid move, there is no space to navigate");//UFP
			}//end of if/else block
		}//end of moveRight method;


		/**moveLeft method:
		 * 
		 * 
		 * This procedural method is used to move the player's position to the left, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to test if the player will hit a wall or pitfall <type: int[]>
		 * 
		 * 
		 * @param playerPosition <type: int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveLeft (int [] playerPosition, String WALL, String PITFALL, String TIMO, String POLICE){//start of method moveLeft

			if(playerPosition[1]!=0) {//checks if the player is positioned at an certain edge that would make the move illegal
				int [] check = new int[] {playerPosition[0],playerPosition[1]}; //initiate and declare a new variable that is equal to the player's position
				check[1]=(check[1]-1);//set check's coordinates equal to what the player's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL) {//checks to see if check hit a wall
					System.out.println("You cannot move through a wall");//UFP;
					return;//breaks out of method
				}else if(gameBoard[check[0]][check[1]]==PITFALL) {//end of if statement, else if statement checks if check will hit a pitfall
					if (playerPosition[0]==timoPosition[0] &&playerPosition[1]==timoPosition[1]) {//if statement used in a case where timo moves towards the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=TIMO;//where the player used to be will now display timo
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else if ((playerPosition[0]==policePosition[0] &&playerPosition[1]==policePosition[1])||(playerPosition[0]==policePosition2[0] &&playerPosition[1]==policePosition2[1])) {//end of if statement,
						//else if statement used in a case where the police catches the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=POLICE;//where the player used to be will now display a police
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else {//end of else if statement, else statement used to direct remaining possibilities
						gameBoard[playerPosition[0]][playerPosition[1]]="[ ]";//where the player used to be will now display an empty tile
						gameBoard[0][0]="[X]";//player will now displayed back where it started
					}//end of if/else if/else block
					System.out.println("YOU HIT A PITFALL, YOUR POSITION WILL NOW BE RESET");//UFP
					playerPosition [0]=0;//resets the player's position
					playerPosition [1]=0;
					hitPitfall=true;//hitPitfall is set to true
					return;//breaks out of method
				}//end of if/else if block
				if (gameBoard[playerPosition[0]][playerPosition[1]]==TIMO) {//if statement used for a scenario where timo moves where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=TIMO;//timo will be displayed where the player is before the player moves
					playerPosition[1]=(playerPosition[1]-1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//player's new position will be marked by a [X]
					validMove=true;//validMove is set to true

				}else if (gameBoard[playerPosition[0]][playerPosition[1]]==POLICE) {//end of if statement, else if statement is sued for a scenario where the 
					//police moves to where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=POLICE;//the player's current position will display a police
					playerPosition[1]=(playerPosition[1]-1);//the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}else {//end of if else statement, else statement is used to direct remaining possibilities

					gameBoard [playerPosition[0]][playerPosition[1]]="[ ]";//the player's current tile will display an empty tile
					playerPosition[1]=(playerPosition[1]-1);//the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}//end of if statement

			}else {//else statement for remaining possibilities
				System.out.println("Invalid move, there is no space to navigate");//UFP
			}//end of if/else block
		}//end of moveLeft method;

		/**moveDown method:
		 * 
		 * 
		 * This procedural method is used to move the player's position down, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to test if the player will hit a wall or pitfall <type: int[]>
		 * 
		 * 
		 * @param playerPosition <type: int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveDown (int [] playerPosition, String WALL, String PITFALL, String TIMO, String POLICE){//start of method moveDown

			if(playerPosition[0]!=SIZE-1) {//checks if the player is positioned at an certain edge that would make the move illegal
				int [] check = new int[] {playerPosition[0],playerPosition[1]}; //initiate and declare a new variable that is equal to the player's position
				check[0]=(check[0]+1);//set check's coordinates equal to what the player's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL) {//checks to see if check hit a wall
					System.out.println("You cannot move through a wall");//UFP;
					return;//breaks out of method
				}else if(gameBoard[check[0]][check[1]]==PITFALL) {//end of if statement, else if statement checks if check will hit a pitfall
					if (playerPosition[0]==timoPosition[0] &&playerPosition[1]==timoPosition[1]) {//if statement used in a case where timo moves towards the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=TIMO;//where the player used to be will now display timo
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else if ((playerPosition[0]==policePosition[0] &&playerPosition[1]==policePosition[1])||(playerPosition[0]==policePosition2[0] &&playerPosition[1]==policePosition2[1])) {//end of if statement,
						//else if statement used in a case where the police catches the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=POLICE;//where the player used to be will now display a police
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else {//end of else if statement, else statement used to direct remaining possibilities
						gameBoard[playerPosition[0]][playerPosition[1]]="[ ]";//where the player used to be will now display an empty tile
						gameBoard[0][0]="[X]";//player will now displayed back where it started
					}//end of if/else if/else block
					System.out.println("YOU HIT A PITFALL, YOUR POSITION WILL NOW BE RESET");//UFP
					playerPosition [0]=0;//resets the player's position
					playerPosition [1]=0;
					hitPitfall=true;//hitPitfall is set to true
					return;//breaks out of method
				}//end of if/else if block
				if (gameBoard[playerPosition[0]][playerPosition[1]]==TIMO) {//if statement used for a scenario where timo moves where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=TIMO;//timo will be displayed where the player is before the player moves
					playerPosition[0]=(playerPosition[0]+1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//player's new position will be marked by a [X]
					validMove=true;//validMove is set to true

				}else if (gameBoard[playerPosition[0]][playerPosition[1]]==POLICE) {//end of if statement, else if statement is sued for a scenario where the 
					//police moves to where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=POLICE;//the player's current position will display a police
					playerPosition[0]=(playerPosition[0]+1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}else {//end of if else statement, else statement is used to direct remaining possibilities

					gameBoard [playerPosition[0]][playerPosition[1]]="[ ]";//the player's current tile will display an empty tile
					playerPosition[0]=(playerPosition[0]+1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}//end of if statement

			}else {//else statement for remaining possibilities
				System.out.println("Invalid move, there is no space to navigate");//UFP
			}//end of if/else block
		}//end of moveDown method;

		/**moveUp method:
		 * 
		 * 
		 * This procedural method is used to move the player's position up, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to test if the player will hit a wall or pitfall <type: int[]>
		 * 
		 * 
		 * @param playerPosition <type: int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveUp (int [] playerPosition, String WALL, String PITFALL, String TIMO, String POLICE){//start of method moveUp

			if(playerPosition[0]!=0) {//checks if the player is positioned at an certain edge that would make the move illegal
				int [] check = new int[] {playerPosition[0],playerPosition[1]}; //initiate and declare a new variable that is equal to the player's position
				check[0]=(check[0]-1);//set check's coordinates equal to what the player's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL) {//checks to see if check hit a wall
					System.out.println("You cannot move through a wall");//UFP;
					return;//breaks out of method
				}else if(gameBoard[check[0]][check[1]]==PITFALL) {//end of if statement, else if statement checks if check will hit a pitfall
					if (playerPosition[0]==timoPosition[0] &&playerPosition[1]==timoPosition[1]) {//if statement used in a case where timo moves towards the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=TIMO;//where the player used to be will now display timo
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else if ((playerPosition[0]==policePosition[0] &&playerPosition[1]==policePosition[1])||(playerPosition[0]==policePosition2[0] &&playerPosition[1]==policePosition2[1])) {//end of if statement,
						//else if statement used in a case where the police catches the player but the player hits a pitfall
						gameBoard[playerPosition[0]][playerPosition[1]]=POLICE;//where the player used to be will now display a police
						gameBoard[0][0]="[X]";//player will now displayed back where it started

					}else {//end of else if statement, else statement used to direct remaining possibilities
						gameBoard[playerPosition[0]][playerPosition[1]]="[ ]";//where the player used to be will now display an empty tile
						gameBoard[0][0]="[X]";//player will now displayed back where it started
					}//end of if/else if/else block
					System.out.println("YOU HIT A PITFALL, YOUR POSITION WILL NOW BE RESET");//UFP
					playerPosition [0]=0;//resets the player's position
					playerPosition [1]=0;
					hitPitfall=true;//hitPitfall is set to true
					return;//breaks out of method
				}//end of if/else if block
				if (gameBoard[playerPosition[0]][playerPosition[1]]==TIMO) {//if statement used for a scenario where timo moves where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=TIMO;//timo will be displayed where the player is before the player moves
					playerPosition[0]=(playerPosition[0]-1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//player's new position will be marked by a [X]
					validMove=true;//validMove is set to true

				}else if (gameBoard[playerPosition[0]][playerPosition[1]]==POLICE) {//end of if statement, else if statement is sued for a scenario where the 
					//police moves to where the player is before the player moves
					gameBoard [playerPosition[0]][playerPosition[1]]=POLICE;//the player's current position will display a police
					playerPosition[0]=(playerPosition[0]-1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}else {//end of if else statement, else statement is used to direct remaining possibilities

					gameBoard [playerPosition[0]][playerPosition[1]]="[ ]";//the player's current tile will display an empty tile
					playerPosition[0]=(playerPosition[0]-1);//player moves
					gameBoard [playerPosition[0]][playerPosition[1]]="[X]";//the player's new position will be marked by a [X]
					validMove=true;//validMove is set to true
				}//end of if statement

			}else {//else statement for remaining possibilities
				System.out.println("Invalid move, there is no space to navigate");//UFP
			}//end of if/else block
		}//end of moveUp method;


		/**moveRightPolice method:
		 * 
		 * This procedural method is used to move the position of a police to the right, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if the police will hit a wall or pitfall <type: int[]>
		 * 
		 * @param policePosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */


		public void moveRightPolice (int [] policePosition, String WALL, String PITFALL, String POLICE){//start of moveRightPolice method

			if(policePosition[1]!=SIZE-1) {//checks if the police is currently along a certain edge where it can't move towards
				int [] check = new int[] {policePosition[0],policePosition[1]};//Initialize check and declare it equal to the police's position
				check[1]=(check[1]+1);//set check equal to the what the police's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement
				gameBoard [policePosition[0]][policePosition[1]]="[?]";//the police's current position will be marked with a [?]
				policePosition[1]=(policePosition[1]+1);//the police moves
				gameBoard [policePosition[0]][policePosition[1]]=POLICE;//the police's current position will be marked with POLICE
				policeValidMove=true;//policeValidMove is set equal to true

			}else {//end of if statement, else statement used to direct remaining possibilities
				policeValidMove=false;//policeValidMove is set to false
			}//end of if/else block
		}//end of moveRightPolice method

		/**moveLeftPolice method:
		 * 
		 * This procedural method is used to move the position of a police to the left, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if the police will hit a wall or pitfall <type: int[]>
		 * 
		 * @param policePosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveLeftPolice (int [] policePosition, String WALL, String PITFALL, String POLICE){//start of moveLeftPolice method

			if(policePosition[1]!=0) {//checks if the police is currently along a certain edge where it can't move towards
				int [] check = new int[] {policePosition[0],policePosition[1]};//Initialize check and declare it equal to the police's position
				check[1]=(check[1]-1);//set check equal to the what the police's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement
				gameBoard [policePosition[0]][policePosition[1]]="[?]";//the police's current position will be marked with a [?]
				policePosition[1]=(policePosition[1]-1);//the police moves
				gameBoard [policePosition[0]][policePosition[1]]=POLICE;//the police's current position will be marked with POLICE
				policeValidMove=true;//policeValidMove is set equal to true

			}else {//end of if statement, else statement used to direct remaining possibilities
				policeValidMove=false;//policeValidMove is set to false
			}//end of if/else block
		}//end of moveLeftPolice method

		/**moveDownPolice method:
		 * 
		 * This procedural method is used to move the position of a police down, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if the police will hit a wall or pitfall <type: int[]>
		 * 
		 * @param policePosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveDownPolice (int [] policePosition, String WALL, String PITFALL, String POLICE){//start of moveDownPolice method

			if(policePosition[0]!=SIZE-1) {//checks if the police is currently along a certain edge where it can't move towards
				int [] check = new int[] {policePosition[0],policePosition[1]};//Initialize check and declare it equal to the police's position
				check[0]=(check[0]+1);//set check equal to the what the police's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement
				gameBoard [policePosition[0]][policePosition[1]]="[?]";//the police's current position will be marked with a [?]
				policePosition[0]=(policePosition[0]+1);//the police moves
				gameBoard [policePosition[0]][policePosition[1]]=POLICE;//the police's current position will be marked with POLICE
				policeValidMove=true;//policeValidMove is set equal to true

			}else {//end of if statement, else statement used to direct remaining possibilities
				policeValidMove=false;//policeValidMove is set to false
			}//end of if/else block
		}//end of moveDownPolice method

		/**moveUpPolice method:
		 * 
		 * This procedural method is used to move the position of a police up, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if the police will hit a wall or pitfall <type: int[]>
		 * 
		 * @param policePosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param POLICE <type: String>
		 * @return void
		 */

		public void moveUpPolice (int [] policePosition, String WALL, String PITFALL, String POLICE){//start of moveUpPolice method

			if(policePosition[0]!=0) {//checks if the police is currently along a certain edge where it can't move towards
				int [] check = new int[] {policePosition[0],policePosition[1]};//Initialize check and declare it equal to the police's position
				check[0]=(check[0]-1);//set check equal to the what the police's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement
				gameBoard [policePosition[0]][policePosition[1]]="[?]";//the police's current position will be marked with a [?]
				policePosition[0]=(policePosition[0]-1);//the police moves
				gameBoard [policePosition[0]][policePosition[1]]=POLICE;//the police's current position will be marked with POLICE
				policeValidMove=true;//policeValidMove is set equal to true

			}else {//end of if statement, else statement used to direct remaining possibilities
				policeValidMove=false;//policeValidMove is set to false
			}//end of if/else block
		}//end of moveUpPolice method


		/**timoMoveRight method:
		 * 
		 * This procedural method is used to move Timo's position to the right, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if Timo will hit a wall or pitfall <type: int[]>
		 * 
		 * @param timoPosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @return void
		 */


		public void timoMoveRight (int [] timoPosition, String WALL, String PITFALL, String TIMO){//start of timoMoveRight method
			if(timoPosition[1]!=SIZE-1) {//checks if timo is currently along a certain edge where it can't move towards
				int [] check = new int[] {timoPosition[0],timoPosition[1]};//Initialize check and declare it equal to timo's position
				check[1]=(check[1]+1);//set check equal to the what timo's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement

				gameBoard [timoPosition[0]][timoPosition[1]]="[?]";//timo's current position will be marked with a [?]
				timoPosition[1]=(timoPosition[1]+1);//timo moves
				gameBoard [timoPosition[0]][timoPosition[1]]=TIMO;//timo's current position will display TIMO
				timoValidMove=true;//timoValidMove is set to true


			}else {//end of if statement, else statement used to direct remaining possibilities
				timoValidMove=false;//timoValidMove is set to false
			}//end of if/else block
		}//end of timoMoveRight method

		/**timoMoveLeft method:
		 * 
		 * This procedural method is used to move Timo's position to the left, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if Timo will hit a wall or pitfall <type: int[]>
		 * 
		 * @param timoPosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @return void
		 */


		public void timoMoveLeft (int [] timoPosition, String WALL, String PITFALL, String TIMO){//start of timoMoveLeft method
			if(timoPosition[1]!=0) {//checks if timo is currently along a certain edge where it can't move towards
				int [] check = new int[] {timoPosition[0],timoPosition[1]};//Initialize check and declare it equal to timo's position
				check[1]=(check[1]-1);//set check equal to the what timo's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement

				gameBoard [timoPosition[0]][timoPosition[1]]="[?]";//timo's current position will be marked with a [?]
				timoPosition[1]=(timoPosition[1]-1);//timo moves
				gameBoard [timoPosition[0]][timoPosition[1]]=TIMO;//timo's current position will display TIMO
				timoValidMove=true;//timoValidMove is set to true


			}else {//end of if statement, else statement used to direct remaining possibilities
				timoValidMove=false;//timoValidMove is set to false
			}//end of if/else block
		}//end of timoMoveLeft method

		/**timoMoveDown method:
		 * 
		 * This procedural method is used to move Timo's position down, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if Timo will hit a wall or pitfall <type: int[]>
		 * 
		 * @param timoPosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @return void
		 */

		public void timoMoveDown (int [] timoPosition, String WALL, String PITFALL, String TIMO){//start of timoMoveDown method
			if(timoPosition[0]!=SIZE-1) {//checks if timo is currently along a certain edge where it can't move towards
				int [] check = new int[] {timoPosition[0],timoPosition[1]};//Initialize check and declare it equal to timo's position
				check[0]=(check[0]+1);//set check equal to the what timo's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement

				gameBoard [timoPosition[0]][timoPosition[1]]="[?]";//timo's current position will be marked with a [?]
				timoPosition[0]=(timoPosition[0]+1);//timo moves
				gameBoard [timoPosition[0]][timoPosition[1]]=TIMO;//timo's current position will display TIMO
				timoValidMove=true;//timoValidMove is set to true


			}else {//end of if statement, else statement used to direct remaining possibilities
				timoValidMove=false;//timoValidMove is set to false
			}//end of if/else block
		}//end of timoMoveDown method

		/**timoMoveUp method:
		 * 
		 * This procedural method is used to move Timo's position up, if possible
		 * 
		 * LOCAL VARIABLES
		 * check - used to check if Timo will hit a wall or pitfall <type: int[]>
		 * 
		 * @param timoPosition <type int[]>
		 * @param WALL <type: String>
		 * @param PITFALL <type: String>
		 * @param TIMO <type: String>
		 * @return void
		 */

		public void timoMoveUp (int [] timoPosition, String WALL, String PITFALL, String TIMO){//start of timoMoveUp method
			if(timoPosition[0]!=0) {//checks if timo is currently along a certain edge where it can't move towards
				int [] check = new int[] {timoPosition[0],timoPosition[1]};//Initialize check and declare it equal to timo's position
				check[0]=(check[0]-1);//set check equal to the what timo's coordinates would be when moving
				if (gameBoard[check[0]][check[1]]==WALL || gameBoard[check[0]][check[1]]==PITFALL) {//if statement checks if check hit a wall or pitfall

					return;//breaks out of method
				}//end of if statement

				gameBoard [timoPosition[0]][timoPosition[1]]="[?]";//timo's current position will be marked with a [?]
				timoPosition[0]=(timoPosition[0]-1);//timo moves
				gameBoard [timoPosition[0]][timoPosition[1]]=TIMO;//timo's current position will display TIMO
				timoValidMove=true;//timoValidMove is set to true


			}else {//end of if statement, else statement used to direct remaining possibilities
				timoValidMove=false;//timoValidMove is set to false
			}//end of if/else block
		}//end of timoMoveUp method


		/**resetArrayMethod:
		 * 
		 * This functional method "resets" a 2D array
		 * by resetting all of its values to 0
		 * 
		 * @param nums <type: int[][]>
		 * @return nums <type: int[][]>
		 */


		public int [][]resetArray (int [][]nums){//start of resetArray method
			for (int i=0; i<nums.length; i++){//double for loop runs through the entire array
				for (int y=0; y<nums[i].length;y++){
					nums[i][y]=0;//set each element to 0
				}//end of for loop

			}//end of for loop
			return nums;//returns the resetted array
		}//end of method resetArray

		/**reset3DArray Method:
		 * 
		 * This functional method resets a 3D array by re-initializing it 
		 * 
		 * @param animation <type: String[][][]>
		 * @return animation <type: String[][][]>
		 */

		public String [][][] reset3DArray (String [][][]animation){//start of reset3DArray method
			animation = new String [100][SIZE][SIZE];//re-initializes the array
			return animation;//returns animation
		}//end of method reset3DArray


		/** setUpWall method:
		 * 
		 * This functional method randomly generates a wall to be used in game
		 * 
		 * LOCAL VARIABLES
		 * rand - an object used to generate random numbers <from java.util.Random>
		 * wallLength - a randomly generated value for the length of the wall <type: int>
		 * wallWidth - a randomly generated value for the width of the wall <type: int>
		 * parameter1 - a randomly generated parameter that adjusts the wall's position on the board <type: int>
		 * paramater2 - a randomly generated parameter that adjusts the wall's position on the board <type: int>
		 * count and count2 - accumulators that assist with making the walls <type: int>
		 * 
		 * @param wall <type: int[][]>
		 * @return wall <type: int[][]>
		 */
		public int[][] setUpWall(int wall[][]){//start of setUpWall method

			Random rand = new Random();//declaration and instantiation of an object named rand
			int wallLength = rand.nextInt(SIZE-5)+1;//generating random values for various variables outlined above, based on experimented ranges
			int wallWidth = rand.nextInt(5)+1;
			int parameter1 = rand.nextInt(3)+2;
			int parameter2= rand.nextInt(SIZE)-1;

			wall = new int [wallLength*wallWidth][2];//declare wall as an array with a certain size 

			int count;int count2=0;//initializing various variables outlined above

			for (int y = wallWidth; y>0; y--) {//double for loop runs through the wall's length and width
				count=0;//count is set to 0

				for (int x=wallLength; x>0; x--){
					wall[count2][0]=SIZE-parameter1-count;//store wall coordinates inside of wall array
					wall[count2][1]=wallWidth+parameter2;
					count++;//count and count2 are accumulated
					count2++;
				}//end of for loop
				wallWidth--;//wallWidth is decremented

			}//end of for loop

			for (int i=0; i<wall.length;i++) {//for loop runs through the length of the wall
				collectIllegalTiles(wall[i][0],wall[i][1]);//Illegal tiles are collected
			}//end of for loop
			return wall;//set up wall is returned
		}//end of method setUpWall

		/**checkExist Method:
		 * 
		 * This functional method checks whether an array (position) exists within a 2D array (cells)
		 * 
		 * LOCAL VARIABLES
		 * exists - represents if the array exists or not <type: boolean>
		 * 
		 * @param cells <type: int [][]>
		 * @param position <type: int[]>
		 * @return exists <type: boolean>
		 */

		public boolean checkExist (int[][]cells, int [] position){//start of method checkExist
			boolean exists=false;//exists is set to false
			for (int i=0; i<cells.length; i++){//double for loop runs through cells with some logic involved
				for (int y=0; y<cells[i].length;y=y+2){
					if (cells[i][y]==position[0]&&cells[i][y+1]==position[1]){//if statement checks if position matches with an array in cells
						exists=true;//exists is set to true
						return exists;//exists is returned
					}//end of if statement
				}//end of for loop

			}//end of for loop
			return exists;//exists is returned
		}//end of checkExist method

		/**printArray Method
		 * 
		 * This procedural method is used to print a 2D array (for the animation) with a sleep timer
		 * 
		 * @param array <type: String [][]>
		 * @throws InterruptedException
		 * @return void
		 */

		public void printArray(String array[][]) throws InterruptedException{//start of printArray method

			for (int x=0; x<array.length; x++){//double for loop runs through the array
				for (int y=0; y<array[x].length;y++){
					if (array[x][y]==null){//if statement checks if the loop has reached a null value
						stopAnimation=true;//stopAnimation is set to true
						return;//breaks out of method
					}else{//end of if statement, else statement used to direct remaining outcomes
						System.out.print(array[x][y]);//prints out indicated elements
					}//end of if/else block

				}//end of for loop
				System.out.println("");//prints a new line
			}//end of for loop
			TimeUnit.MILLISECONDS.sleep(350);//puts the program to sleep for 350 milliseconds

		}//end of method printArray

		/**testArray Method:
		 * This procedural method tests if a certain 2D array contains null elements. This is used to stop the animation so it doesn't print empty/null arrays
		 * This method is used to test a future frame while the animation is running
		 * 
		 * @param array <type: String [][]>
		 * @throws InterruptedException
		 * @return null
		 */

		public void testArray(String array[][]) throws InterruptedException{ //start of testArray method

			for (int x=0; x<array.length; x++){//double for loop runs through the array
				for (int y=0; y<array[x].length;y++){
					if (array[x][y]==null){//if statement checks if the loop has reached a null value
						stopAnimation=true;//stopAnimation is equal to true
						return;//break out of the method
					}//end of if statement
				}//end of for loop
			}//end of for loop
		}//end of testArray method

		/**drawBoard method:
		 * 
		 * This method is used to display the gameboard and the legend on the screen for the player to see
		 * 
		 * LOCAL VARIABLES
		 * alphabets - used to represent the alphabets for the coordinate labeling mechanic
		 * 
		 * @param gameBoard <type String [][]>
		 * @param name <type String>
		 * @return void
		 * @param elapsedTime <type: double>
		 */

		public void drawBoard (String[][]gameBoard, String name, double elapsedTime){
			String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			//declaring a new string array full of alphabets to use in the coordinate system
			System.out.print("   ");//prints out some spaces for formatting purposes

			for (int i=1; i<=SIZE; i++){//for loop prints out the numbers from 1-SIZE
				if (i>8){//if statement used to test if i is greater than 8
					System.out.print(i+" ");//prints out some spaces for formatting purposes
				}else{//end of if statement, else statement is used
					System.out.print(i+"  ");//prints out some spaces for formatting purposes
				}//end of if/else block

			}//end of for loop
			System.out.println();//moves cursor to the next line
			for (int x=0; x<gameBoard.length;x++){//double for loop is used to run through the 2d array
				System.out.print(alphabets[x]+" ");//prints out the corresponding alphabet followed by a space
				for (int y=0; y<gameBoard[x].length;y++){
					System.out.print(gameBoard[x][y]);//prints out the elements in the array
				}//end of for loop
				System.out.println("");//moves cursor to the next line

			}//end of for loop
			System.out.println(name+" IS HERE: [X]"+"  POLICE IS HERE: !!!   SEARCHED AREAS: [ ] ");//UFP
			System.out.println("TIMO IS  HERE: OOO   WALLS ARE HERE: ///    PITFALLS ARE HERE: XXX   CURRENT MOVES: "+moves);
			System.out.println("RUNTIME: "+elapsedTime+" seconds");
			System.out.println("________________________________________________________________________________________________________________________________________");

		}//end of drawBoard method


		/**getMoveDecision method:
		 * 
		 * This functional method reads the user's input, tests it for validity, and returns their input as desired
		 * 
		 * LIST OF LOCAL VARIABLES
		 * br - a BufferedReader object used to read the user's input <type: BufferedReader>
		 * x - used to hold the user's string input <type: String>
		 * 
		 * @return x - the user's input <type: String>
		 * @throws IOException
		 * @param none
		 */

		public String getMoveDecision() throws IOException{//start of getMoveDecision method
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in));//declaration and instantiation of a BufferedReader object br
			String x = br.readLine();//declaring a string variable named x and assigning it to the user's input
			while (true){//while loop will loop infinitely until method returns something
				if (x.equals("w") || x.equals("a") || x.equals("s") || x.equals("d")|| x.equals("1")|| x.equals("2")){ //if statement used to determine if the user inputed w,a,s,d,1,2 or 3
					return x;//method returns x
				}else{//end of if statement, else statement is used
					System.out.println("Invalid, please reenter:");//UFP
					x= br.readLine();//assigning x to the user's input
				}//end of if/else block

			}//end of while loop

		}//end of getMoveDecision method


		/**getDecision method:
		 * 
		 * This functional method returns the user's menu decision 
		 * 
		 * LIST OF LOCAL VARIABLS
		 * br - a BufferedReader object used to read the user's input <type: BufferedReader>
		 * x - used to hold the user's input <type: Int>
		 * 
		 * @return x - the player's decision
		 * @throws IOException
		 * @param none
		 */

		public int getDecision() throws IOException{//start of getDecision method
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in));//declaration and instantiation of a BufferedReader object br
			int x;//declaring a variable x
			while (true) {//setting up an infinite while loop
				try {//try catch block used for error handling
					x=Integer.parseInt(br.readLine());//x is set equal to the user's input parsed into an integer type
					if (x==1 || x==2 || x==3 || x==4) {//if statement checks if the user's input is 1, 2, 3, or 4
						return x;//x is returned
					}else {//end of if statement, else statement is used for remaining outcomes
						System.out.println("Invalid Input, please try again");//UFP
					}//end of if/else block

				}catch (Exception e) {//catch statement allows for custom error handling
					System.out.println("Invalid, please try again");//UFP
				}//end of try catch block
			}//end of while loop

		}//end of getDecision method

		/**getMasterPlayAgain method:
		 * 
		 * This functional method will read the user's input on whether they want a new player to play, test its validity, and return their input as desired
		 * 
		 * LIST OF LOCAL VARIABLS
		 * br - a BufferedReader object used to read the user's input <type: BufferedReader>
		 * x - used to hold the user's input <type: Int>
		 * check - used to determine if the user's input is valid <type: boolean>
		 * 
		 * @param none
		 * @return x - the user's input <type: Integer>
		 * @throws IOException
		 */


		public boolean getMasterPlayagain () throws IOException{
			int x = 0;//initializing and declaring a new integer variable x
			boolean check=true;//initializing and declaring a new boolean variable check
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in));//instantiation of a BufferedReader object br

			while (check){//while loop loops infinitely until method returns something
				try{//try catch block used for error handling
					x=Integer.parseInt(br.readLine());//sets x equal to the user's input and parses it to an integer type
					if (x!=1 && x!=2){//checks if the user inputed something other than 1 or 2
						System.out.println("Invalid input, please try again");//UFP
					}else{//end of if statement, else statement is set up
						check=false;//sets check equal to false
					}//end of if/else block

				}catch (Exception e){ //catch block tells Java what to do when an exception is encountered
					System.out.println("Invalid input, please try again");//UFP
				}//end of try catch block
			}//end of while loop
			if (x==1){//if x is equal to 1
				return true;//returns true
			}else{//end of if statement, else statement is set up
				return false;//returns false
			}//end of if/else block

		}//end of getMasterPlayAgain method 

		/**displayHighScores Method:
		 * 
		 * This procedural method is used to display the highscore list in a neat fashion
		 * 
		 * LOCAL VARIABLES
		 * greatest - represents the greatest name length on the leaderboard to calculate spacing <type: int[]>
		 * spaces - represents the number of spaces needed to be added to each name for the scores to line up <type: int[]>
		 * 
		 * @return void
		 * @param highScores <type String[][]>
		 */

		public void displayHighScores(String[][]highScores) {//start of displayHighScores method
			int greatest=0;//initializing various variables outlined above
			int spaces;
			for (int x=0; x<highScores.length; x++){//for loop runs through the highScores list

				if((highScores[x][0]).length()>greatest) {//if statement checks if the length of a name on the leaderboard is greater than greatest
					greatest=highScores[x][0].length();//if so, greatest is set equal to the length of that name


				}//end of if statement
			}//end of for loop

			System.out.print("Name");//displays the name
			for (int k=greatest-4+5; k>0; k--) {//for loop repeats a certain amount of times so that everything lines up
				System.out.print(" ");//prints a single space each time
			}//end of for loop
			System.out.println("Score");//displays the score
			for (int x=0; x<highScores.length; x++){//double for loop runs through the leaderboard
				for (int y=0; y<highScores[x].length;y++){
					if (highScores[x][y]==null) {//if statement checks if a certain element is null
						return;//breaks out of the method
					}else {//end of if statement, else statement is used to direct remaining outcomes
						spaces=greatest-(highScores[x][0].length())+5;//spaces is set equal to greatest-the current name length+5
						System.out.print(highScores[x][y]);//prints out leaderboard data
						for (int k=spaces; k>0; k--) {//for loop loops for spaces amount of times
							System.out.print(" ");//prints out a single space
						}//end of for loop

					}//end of if/else block
				}//end of for loop
				System.out.println("");//starts a new line


			}//end of for loop



		}//end of method displayHighScores

		/** tutorial Method:
		 * 
		 * This procedural method is used to teach the player basic rules/how to play the game
		 * 
		 * LOCAL VARIABLES
		 * br - a BufferedReader object used to print text and commuinicate with the user <type: BufferedReader>
		 * 
		 * @throws IOException
		 * @param none
		 * @return void
		 */

		public void tutorial() throws IOException {//start of tutorial method
			BufferedReader br = new BufferedReader (new InputStreamReader (System.in));//declaration and instantiation of a BufferedReader object br


			System.out.println("           /^\\\r\n" //UFP
					+ "          |   |\r\n"
					+ "    /\\     |_|     /\\\r\n"
					+ "    | \\___/' `\\___/ |\r\n"
					+ "     \\_/  \\___/  \\_/\r\n"
					+ "      |\\__/   \\__/|\r\n"
					+ "      |/  \\___/  \\|\r\n"
					+ "     ./\\__/   \\__/\\,\r\n"
					+ "     | /  \\___/  \\ |\r\n"
					+ "     \\/     V     \\/");
			System.out.println("Timo will try to move one space each turn. Timo cannot revisit a tile he's been to before in the last 10 moves. Timo will be visible on the board");
			System.out.println("TIMO is represented by: OOO");
			System.out.println("__________________________________________________________________________________________________________________________________");
			System.out.println("ENTER ANYTHING TO CONTINUE");
			br.readLine(); //reads a single line of input
			System.out.println("              ,\r\n" //UFP
					+ "     __  _.-\"` `'-.\r\n"
					+ "    /||\\'._ __{}_(\r\n"
					+ "    ||||  |'--.__\\\r\n"
					+ "    |  L.(   ^_\\^\r\n"
					+ "    \\ .-' |   _ |\r\n"
					+ "    | |   )\\___/\r\n"
					+ "    |  \\-'`:._]\r\n"
					+ "    \\__/;      '-.");
			System.out.println("Police will randomly move one space each turn. Don't get caught, or you lose!");
			System.out.println("POLICE is represented by: !!!");
			System.out.println("__________________________________________________________________________________________________________________________________");
			System.out.println("ENTER ANYTHING TO CONTINUE");
			br.readLine(); //reads a single line of input
			System.out.println("Methods to catch TIMO: (YOU are represented by X)");  //UFP
			System.out.println("__________________________________________________________________________________________________________________________________");
			System.out.println("1: Diagonal Catch                          2:Linear Catch                                       3.Regular Catch (If TIMO is Stuck)");
			System.out.println("OOO[?] <-- catch here                      OOO[?][X]                                            OOO[X]");
			System.out.println("[?][X]                                        ^^^ catch here                                    ^^^catch here "  );
			System.out.println("__________________________________________________________________________________________________________________________________");

			System.out.println("Player moves up, TIMO moves right          Player moves left, TIMO moves right                  Simply move left towards TIMO when stuck");
			System.out.println("__________________________________________________________________________________________________________________________________");
			System.out.println("ADDITIONAL RULES");
			System.out.println("1. There are randomy generated pitfalls that will reset your position");
			System.out.println("2. There are randomly generated walls that nothing can pass through");
			System.out.println("3. Ranking is based on least number of moves");

			System.out.println("ENTER ANYTHING TO END TUTORIAL"); 
			br.readLine();//reads a single line of input

		}//end of tutorial method

		/**displayEndMessage method
		 * 
		 * This procedural method simply displays the winning message to the player
		 * 
		 * @param name <type: String>
		 * @return void
		 */


		public void displayEndMessage (String name){//start of displayEndMessage method
			System.out.println("Congratulations "+name+"! You found TIMO in "+moves+" moves!");//UFP
		}//end of displayEndMessage method


		/** displayIntro Method:
		 * This procedural method simply outputs the title of the game
		 * 
		 * @param none
		 * @return void
		 * 
		 */

		public void displayIntro() {//start of displayIntro method
			System.out.println("  ______ _           _ _               _______ _                 \r\n"   //UFP
					+ " |  ____(_)         | (_)             |__   __(_)                \r\n"
					+ " | |__   _ _ __   __| |_ _ __   __ _     | |   _ _ __ ___   ___  \r\n"
					+ " |  __| | | '_ \\ / _` | | '_ \\ / _` |    | |  | | '_ ` _ \\ / _ \\ \r\n"
					+ " | |    | | | | | (_| | | | | | (_| |    | |  | | | | | | | (_) |\r\n"
					+ " |_|    |_|_| |_|\\__,_|_|_| |_|\\__, |    |_|  |_|_| |_| |_|\\___/ \r\n"
					+ "                                __/ |                            \r\n"
					+ "                               |___/                             ");


			System.out.println("\nWelcome to Finding Timo! Timo is a lost turtle and the objective of this game is to Find Timo in the least number of moves");
			System.out.println("________________________________________________________________________________________________________________________________________");
		}//end of displayIntro method

		/**getName method:
		 * 
		 * This functional method will read the user's input and return the data
		 * 
		 * LIST OF LOCAL VARIABLES
		 * br - a BufferedReader object used to read the user's input <type: BufferedReader>
		 * @return br.readLine() <type:String>
		 * @throws IOException
		 * @param none
		 */

		public String getName() throws IOException{//start of getName method
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in));//instantiation and declaration of a BufferedReader object br
			System.out.println("What is your name?");//UFP
			return br.readLine();//returns the user's input
		}//end of getName method

	}//end of FindingTimo class