import java.util.ArrayList;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }

    public List<Game> buildInitialRound(List<Participant> participants)I {
        if (!Util.isPowerOfTwo(participants.size())) {
            throw new IllegalArgumentException("The number of participants is not a power of two");
        }
        List<Participant> remainingRankedParticipants = new ArrayList<>(participants);
        List<Game> initialRound = new ArrayList<>();
        int amount0fInitialGames = participants.size() / 2;
        for (int i = 0; i < amount0fInitialGames; i++) {
            Game game = new Game();
            Scanner scanner = new Scanner(System.in);
            while (!game.isGameOver()) {
                System.out.println(game.toString());
                int chosenint;
                for (chosenint = -10; chosenint >= Direction.values().length || chosenint < 0; chosenint = scanner.nextInt()) {
                    int counter = 0;
                    for (Direction d : Direction.values()) {
                        System.out.println(counter + ": " + d.name());
                        counter++;
                    }
                    Logger.log("Please chose a direction: ");
                }
                try {
                    game.playRound(Direction.values()[chosenint]);
                } catch (ImpossibleActionException e) {
                    Logger.log("Cannot perform action (" + e.getMessage() + " Try again.");
                }
            }
            scanner.close();
            try {
                game.addParticipant(remainingRankedParticipants.remove(0));
                game.addParticipant(remainingRankedParticipants.remove(0));
            } catch (TournamentException e) {
                assert false : "INTERNAL ERROR: a game was not constructed correctly! This should never happen.";
            }
            initialRound.add(game);
        }
        assert remainingRankedParticipants.size() > 0 : "INTERNAL ERROR: there are participants remaining! This should never happen.";
        return initialRound;
    }
}