
public class Deck {

	// Instance variables
	int nrOfCards = 52;
	
	// Constructors
	
	
	
	// Class methods
	String[] createDeck(String[] array){
		
		String[] color = {" of Hearts"," of Diamonds"," of Cloves"," of Spades"};
		
		for (int i = 0; i < color.length; i++) {
			for (int j = 0; j < 13; j++) {
				if (j < 9) {
					array[j+(i*13)] = (j+2) + color[i];
				} else {
					switch (j) {
					case 9: array[j+(i*13)] = "Jack" + color[i];
						
						break;
					case 10: array[j+(i*13)] = "Queen" + color[i];
						
						break;
					case 11: array[j+(i*13)] = "King" + color[i];
						
						break;
					case 12: array[j+(i*13)] = "Ace" + color[i];
						
						break;

					default:
						break;
					}
				}
			}
		}
		return array;
	}
	
	int drawCard(){
		
		int cardValue = 0;
		
		
		return cardValue;
	}
	
	
	// Getters and setters
	
	
}
