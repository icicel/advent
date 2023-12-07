import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.Input;

// Camel Cards
// 251029473
// 251003917
public class Day7 {
    public static void main(String[] args) {
        Day7 main = new Day7();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final List<Hand> hands;

    public Day7() {
        hands = new ArrayList<>();
        for (String line : Input.lines(7)) {
            hands.add(new Hand(line));
        }
    }

    public int solve1() {
        hands.sort(Hand::compareTo);
        int sum = 0;
        int rank = 1;
        for (int i = hands.size() - 1; i >= 0; i--) {
            sum += rank++ * hands.get(i).bid;
        }
        return sum;
    }

    public int solve2() {
        hands.sort(Hand::compareToWithJoker);
        int sum = 0;
        int rank = 1;
        for (int i = hands.size() - 1; i >= 0; i--) {
            sum += rank++ * hands.get(i).bid;
        }
        return sum;
    }
    


    // Dual-purpose, both with and without jokers
    public class Hand {
        public final Card[] cards;
        public final Card[] cardsWithJoker;
        public final HandType type;
        public final HandType typeWithJoker;
        public final int bid;

        public Hand(String hand) {
            this.cards = new Card[5];
            this.cardsWithJoker = new Card[5];
            String[] splitHand = hand.split(" ");

            for (int i = 0; i < 5; i++) {
                char cardChar = splitHand[0].charAt(i);
                this.cards[i] = Card.parse(cardChar, false);
                this.cardsWithJoker[i] = Card.parse(cardChar, true);
            }

            this.bid = Integer.parseInt(splitHand[1]);
            this.type = HandType.fromHand(this, false);
            this.typeWithJoker = HandType.fromHand(this, true);
        }

        public int compareTo(Hand other) {
            int diff = this.type.compareTo(other.type);
            if (diff != 0) {
                return diff;
            }
            for (int i = 0; i < cards.length; i++) {
                int diff2 = this.cards[i].compareTo(other.cards[i]);
                if (diff2 != 0) {
                    return diff2;
                }
            }
            return 0;
        }

        public int compareToWithJoker(Hand other) {
            int diff = this.typeWithJoker.compareTo(other.typeWithJoker);
            if (diff != 0) {
                return diff;
            }
            for (int i = 0; i < cardsWithJoker.length; i++) {
                int diff2 = this.cardsWithJoker[i].compareTo(other.cardsWithJoker[i]);
                if (diff2 != 0) {
                    return diff2;
                }
            }
            return 0;
        }
    }



    public enum HandType {
        FIVE_KIND, FOUR_KIND, FULL_HOUSE, THREE_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD;

        public static HandType fromHand(Hand hand, boolean withJokers) {
            // copy
            Card[] cards;
            if (withJokers) {
                cards = Arrays.copyOf(hand.cardsWithJoker, 5);
            } else {
                cards = Arrays.copyOf(hand.cards, 5);
            }
            Arrays.sort(cards);

            // collect amounts of cards in the hand
            int[] amounts = new int[5];
            Card current = null;
            int i = -1;
            int jokers = 0;
            for (Card card : cards) {
                if (card == Card.JOKER) {
                    jokers++;
                    continue;
                }
                if (card != current) {
                    i++;
                    current = card;
                }
                amounts[i] = amounts[i] + 1;
            }
            Arrays.sort(amounts);

            // get type
            if (amounts[4] + jokers == 5) {
                return FIVE_KIND;
            } else if (amounts[4] + jokers == 4) {
                return FOUR_KIND;
            } else if (amounts[4] + jokers == 3 && amounts[3] == 2) {
                return FULL_HOUSE;
            } else if (amounts[4] + jokers == 3) {
                return THREE_KIND;
            } else if (amounts[4] + jokers == 2 && amounts[3] == 2) {
                return TWO_PAIR;
            } else if (amounts[4] + jokers == 2) {
                return ONE_PAIR;
            }
            return HIGH_CARD;
        }
    }



    public enum Card {
        ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO, JOKER;

        public static Card parse(char c, boolean withJokers) {
            switch (c) {
                case 'A':
                    return Card.ACE;
                case 'K':
                    return Card.KING;
                case 'Q':
                    return Card.QUEEN;
                case 'J':
                    if (withJokers) {
                        return Card.JOKER;
                    }
                    return Card.JACK;
                case 'T':
                    return Card.TEN;
                case '9':
                    return Card.NINE;
                case '8':
                    return Card.EIGHT;
                case '7':
                    return Card.SEVEN;
                case '6':
                    return Card.SIX;
                case '5':
                    return Card.FIVE;
                case '4':
                    return Card.FOUR;
                case '3':
                    return Card.THREE;
                case '2':
                    return Card.TWO;
                default:
                    return null;
            }
        }
    }
}
