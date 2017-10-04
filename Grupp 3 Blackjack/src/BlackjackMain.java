import java.util.Random;
import java.util.Scanner;

public class BlackjackMain {

	public static void main(String[] args) {

		// Must have imports
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		BlackjackMain blackjack = new BlackjackMain();

		// Define the variables
		// Integers
		int dealerTotal;
		int delalerCounter;
		int nrOfPlayers;
		int tempValue1;
		int tempValue2;

		// Player Arrays
		String[] playerNames;
		int[] playerChips;
		int[] firstCard;
		int[] secondCard;
		int[] drawCard;
		int[] playerTotal;
		int[] dealerCards;
		int[] bets;

		// Strings
		String hitOrNot;

		// Booleans
		boolean gameOngoing = true;
		boolean stopHitting;

		// Starting printouts for the users
		System.out.println("\tWelcome to Jonathan´s blackjack program!");
		System.out.println("\t      Get ready for some fun!\n\n");

		// Defining the numbers of players and assigning names to them
		System.out.print("How many players? ");
		nrOfPlayers = scan.nextInt();
		playerNames = new String[nrOfPlayers];
		playerChips = new int[nrOfPlayers];
		firstCard = new int[nrOfPlayers];
		secondCard = new int[nrOfPlayers];
		drawCard = new int[nrOfPlayers];
		playerTotal = new int[nrOfPlayers];
		bets = new int[nrOfPlayers];
		dealerCards = new int[5];

		// Assigning the values of the arrays
		for (int i = 0; i < nrOfPlayers; i++) {
			System.out.print("\nName of player " + (i + 1) + "? ");
			playerNames[i] = scan.next();
			System.out.print(playerNames[i] + ", how many chips do you have? ");
			playerChips[i] = scan.nextInt();
		}

		// ASSING NAMES AND AMOUNTS IS DONE AND READY!

		// Main game loop. Do while for determining if all players are done.
		System.out.println("\nThe game starts now\n");

		do {
			delalerCounter = 1;

			// Get your bets in
			System.out.print("\n\tGet you bets in...");
			for (int i = 0; i < nrOfPlayers; i++) {
				System.out.print("\n" + playerNames[i] + ", place your bet: ");
				bets[i] = scan.nextInt();
			}

			// Game starts by dealing two cards to every player
			firstCard = blackjack.addingValues(firstCard);
			secondCard = blackjack.addingValues(secondCard);

			// Dealer gets all cards
			dealerCards = blackjack.addingValues(dealerCards);

			// Show cards for players
			for (int i = 0; i < nrOfPlayers; i++) {
				tempValue1 = firstCard[i];
				tempValue2 = secondCard[i];
				playerTotal[i] = (blackjack.correctTotal(tempValue1)) + (blackjack.correctTotal(tempValue2));
				System.out.println("\n" 
						+ playerNames[i] 
						+ ", your cards are " 
						+ blackjack.cardFaceValue(tempValue1) 
						+ " + " + blackjack.cardFaceValue(tempValue2) 
						+ " = "
						+ playerTotal[i]);
			}

			// Show one of the dealers cards
			tempValue1 = dealerCards[0];
			tempValue2 = dealerCards[1];
			System.out.println("\nDealers has " + blackjack.cardFaceValue(tempValue1) + " and one hidden...");
			dealerTotal = (blackjack.correctTotal(tempValue1)) + (blackjack.correctTotal(tempValue2));

			// All players get to hit or not
			for (int i = 0; i < nrOfPlayers; i++) {
				do {
					System.out.print("\n" + playerNames[i] + ", \"Hit\" or \"Not\"? ");
					stopHitting = true;
					hitOrNot = scan.next();
					if (hitOrNot.toLowerCase().equals("hit")) {
						drawCard[i] = rand.nextInt(14) + 2;
						tempValue1 = drawCard[i];
						playerTotal[i] += blackjack.correctTotal(tempValue1);
						if (playerTotal[i] > 21) {
							System.out.println("BUST!");
							break;
						} else if (playerTotal[i] == 21) {
							System.out.println("BLACKJACK!!");
						}
					} else if (hitOrNot.toLowerCase().equals("not")) {
						stopHitting = false;
					} else {
						System.out.println("Try again...");
					}

					System.out.println(playerNames[i] + ", new total is " + playerTotal[i]);
				} while (stopHitting);
			}

			// last part is for the dealer to play the game to a stop!
			try {
				Thread.sleep(1000); // Sleep for one second
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempValue1 = dealerCards[0];
			tempValue2 = dealerCards[1];
			System.out.println(
					"\nDealer cards is " + blackjack.cardFaceValue(tempValue1) + " and " + blackjack.cardFaceValue(tempValue2)+ " total is: " + dealerTotal);
			
			while (dealerTotal <= 16) {
				delalerCounter++;
				tempValue1 = dealerCards[delalerCounter];
				System.out.println("\nThe dealer takes another card, " + blackjack.cardFaceValue(tempValue1));
				dealerTotal += blackjack.correctTotal(tempValue1);
				System.out.println("New total is: " + dealerTotal);
				if (dealerTotal > 21) {
					System.out.println("Dealer BUST!");
				} else if (dealerTotal == 21) {
					System.out.println("Dealer got 21 and won the game!");
				}
				try {
					Thread.sleep(1000); // Sleep for one second
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Evaluate who as beaten the dealer and who has not and double there bet..
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < nrOfPlayers; i++) {
				if (playerTotal[i] <= 21 && dealerTotal < playerTotal[i]) {
					playerChips[i] += bets[i];
					System.out.println("\n" + playerNames[i] + ", You beat the dealer and double you bet!"
							+ "\nYou win " + (bets[i] * 2) + ". New chip total is: " + playerChips[i]);
				} else {
					// deal with the fact that they lost the game
					playerChips[i] -= bets[i];
					System.out.println("\n" + playerNames[i] + ", You lost to the dealer" + "\nNew chip total is "
							+ playerChips[i]);
				}
			}
			// if the arrays are empty stop game..... 
			gameOngoing = blackjack.checkArray(playerChips);
		} while (gameOngoing);

		scan.close();
	}

	// Extra methods

	// Assigning card values
	int[] addingValues(int[] array) {
		Random rand = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt(13) + 2;
		}
		return array;
	}

	// Checking for a empty array
	boolean checkArray(int[] array) {
		boolean trueOrNot = true;
		int total = 0;
		for (int i : array) {
			total += i;
		}
		if (total <= 0)
			trueOrNot = false;
		return trueOrNot;
	}
	
	String cardFaceValue(int number) {
		
		String[] color = {" of Hearts"," of Cloves"," of Diamonds", " of Spades"};
		Random rand = new Random();
		int choseColor = rand.nextInt(3);
		String line;
		
		switch (number) {
		case 2:
			line = "2" + color[choseColor];
			break;
		case 3:
			line = "3" + color[choseColor];
			break;
		case 4:
			line = "4" + color[choseColor];
			break;
		case 5:
			line = "5" + color[choseColor];
			break;
		case 6:
			line = "6" + color[choseColor];
			break;
		case 7:
			line = "7" + color[choseColor];
			break;
		case 8:
			line = "8" + color[choseColor];
			break;
		case 9:
			line = "9" + color[choseColor];
			break;
		case 10:
			line = "10" + color[choseColor];
			break;
		case 11:
			line = "Jacks" + color[choseColor];
			break;
		case 12:
			line = "Queen" + color[choseColor];
			break;
		case 13:
			line = "King" + color[choseColor];
			break;
		case 14:
			line = "Ace" + color[choseColor];
			break;

		default:
			line = "error" + number;
			break;
		}
		
		return line;
	}
	
	int correctTotal(int num) {
		if (num > 10) {
			num = 10;
		}
		return num;

	}

}
