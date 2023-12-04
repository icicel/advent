import java.util.LinkedList;
import java.util.List;

import base.Input;

// Scratchcards
//
//
public class Day4 {
    public static void main(String[] args) {
        Day4 main = new Day4();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final List<Card> cards;
    public final List<Integer> cardCopies; // corresponding to cards

    public Day4() {
        cards = new LinkedList<>();
        cardCopies = new LinkedList<>();
        for (String line : Input.lines(4)) {
            cards.add(new Card(line, cards));
            cardCopies.add(1);
        }
    }

    public int solve1() {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getPoints();
        }
        return sum;
    }

    public int solve2() {
        int sum = 0;
        for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
            // How many copies of this card exists?
            int copies = cardCopies.get(cardIndex);
            sum += copies;
            // The amount of matching numbers determines how many cards to copy
            for (int i = 1; i <= cards.get(cardIndex).getMatchingNumbers(); i++) {
                int currentCopies = cardCopies.get(cardIndex + i);
                currentCopies += copies;
                cardCopies.set(cardIndex + i, currentCopies);
            }
        }
        return sum;
    }
    


    // A number n is winning if winning[n] is true;
    // It can also communicate with the array containing it
    public class Card {
        public final boolean[] winningNumbers;
        public final List<Integer> cardNumbers;
        public final List<Card> pile;

        public Card(String cardString, List<Card> cards) {
            this.winningNumbers = new boolean[100];
            this.cardNumbers = new LinkedList<>();
            this.pile = cards;

            // parse string representation
            String[] card = cardString.split(": +");
            String[] numbers = card[1].split(" \\| +");
            String[] winningNumbers = numbers[0].split(" +");
            String[] cardNumbers = numbers[1].split(" +");
            for (String number : winningNumbers) {
                this.winningNumbers[Integer.valueOf(number)] = true;
            }
            for (String number : cardNumbers) {
                this.cardNumbers.add(Integer.valueOf(number));
            }
        }

        // Calculate the amount of numbers that are winning numbers
        private Integer matchingNumbers;
        public int getMatchingNumbers() {
            if (this.matchingNumbers != null) {
                return this.matchingNumbers;
            }

            int matchingNumbers = 0;
            for (int number : cardNumbers) {
                if (winningNumbers[number]) {
                    matchingNumbers++;
                }
            }
            this.matchingNumbers = matchingNumbers;
            return matchingNumbers;
        }

        // Using the first scoring system
        public int getPoints() {
            int matchingNumbers = getMatchingNumbers();
            if (matchingNumbers != 0) {
                return 1 << (matchingNumbers - 1);
            }
            return 0;
        }
    }
}
