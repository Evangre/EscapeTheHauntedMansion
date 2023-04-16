import game.Room;
import game.Player;
import game.Key;
import game.Exit;
import game.Ghost;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static boolean hasKey = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        Room[][] grid = new Room[4][4];
        Random rand = new Random();

        // Initialize all elements with an empty room
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Room(i, j);
            }
        }

        // Randomly place the player, key, exit, and ghost
        Player player = new Player(rand.nextInt(4), rand.nextInt(4));
        Key key;
        do {
            key = new Key(rand.nextInt(4), rand.nextInt(4));
        } while (key.getX() == player.getX() && key.getY() == player.getY());

        Exit exit;
        do {
            exit = new Exit(rand.nextInt(4), rand.nextInt(4));
        } while ((exit.getX() == player.getX() && exit.getY() == player.getY())
                || (exit.getX() == key.getX() && exit.getY() == key.getY()));

        Ghost ghost;
        do {
            ghost = new Ghost(rand.nextInt(4), rand.nextInt(4));
        } while ((ghost.getX() == player.getX() && ghost.getY() == player.getY())
                || (ghost.getX() == key.getX() && ghost.getY() == key.getY())
                || (ghost.getX() == exit.getX() && ghost.getY() == exit.getY()));

        System.out.println("Welcome to the Haunted Mansion! You have 1 minute to escape.");
        System.out.println("Find the key to escape and watch out for ghosts!");
        printHauntedMansionArt();

        // Start the 1-minute timer
        startTimer();

        while (!quit) {
            System.out.println("\nCurrent Mansion Layout:");
            displayGrid(grid, player, key, exit, ghost);

            System.out.print("Enter a command (up, down, left, right, unlock, quit): ");
            String command = scanner.nextLine();

            switch (command) {
                case "up" -> {
                    if (player.getY() > 0) {
                        player = new Player(player.getX(), player.getY() - 1);
                    }
                }
                case "down" -> {
                    if (player.getY() < grid.length - 1) {
                        player = new Player(player.getX(), player.getY() + 1);
                    }
                }
                case "right" -> {
                    if (player.getX() < grid[0].length - 1) {
                        player = new Player(player.getX() + 1, player.getY());
                    }
                }
                case "left" -> {
                    if (player.getX() > 0) {
                        player = new Player(player.getX() - 1, player.getY());
                    }
                }
                case "unlock" -> {
                    if (player.getX() == exit.getX() && player.getY() == exit.getY() && hasKey) {
                        System.out.println("Congratulations! You have escaped the Haunted Mansion!");
                        quit = true;
                        System.exit(0);
                    } else {
                        System.out.println("You can't unlock the exit yet.");
                    }
                }
                case "quit" -> {
                    quit = true;
                    System.exit(0);
                }
                default -> System.out.println("Invalid command. Please try again.");
            }

            // Check if the player found the key
            if (player.getX() == key.getX() && player.getY() == key.getY()) {
                System.out.println("You found the key!");
                hasKey = true;
                key = new Key(-1, -1); // Move the key off the grid
            }

            // Check if the player landed on the ghost's square
            if (player.getX() == ghost.getX() && player.getY() == ghost.getY()) {
                System.out.println("The ghost haunted you!");
                System.exit(0);
                quit = true;
            }
        }

        scanner.close();
    }

    private static void displayGrid(Room[][] grid, Player player, Key key, Exit exit, Ghost ghost) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                String cellContent;
                if (x == player.getX() && y == player.getY()) {
                    cellContent = "[ P ]";
                } else if (x == key.getX() && y == key.getY()) {
                    cellContent = "[ K ]";
                } else if (x == exit.getX() && y == exit.getY()) {
                    cellContent = "[ E ]";
                } else if (x == ghost.getX() && y == ghost.getY()) {
                    cellContent = "[ G ]";
                } else {
                    cellContent = "[   ]";
                }
                System.out.print(cellContent);
            }
            System.out.println();
        }
    }

    private static void printHauntedMansionArt() {
        System.out.println("""
                                                              ,           ^'^  _
                                                              )               (_) ^'^
                         _/\\_                    .---------. ((        ^'^
                         (('>                    )`'`'`'`'`( ||                 ^'^
                    _    /^|                    /`'`'`'`'`'`\\||           ^'^
                    =>--/__|m---               /`'`'`'`'`'`'`\\|
                         ^^           ,,,,,,, /`'`'`'`'`'`'`'`\\      ,
                                     .-------.`|`````````````|`  .   )
                                    / .^. .^. \\|  ,^^, ,^^,  |  / \\ ((
                                   /  |_| |_|  \\  |__| |__|  | /,-,\\||
                        _         /_____________\\ |")| |  |  |/ |_| \\|
                       (")         |  __   __  |  '==' '=='  /_______\\     _
                      (' ')        | /  \\ /  \\ |   _______   |,^, ,^,|    (")
                       \\  \\        | |--| |--| |  ((--.--))  ||_| |_||   (' ')
                     _  ^^^ _      | |__| |("| |  ||  |  ||  |,-, ,-,|   /  /
                   ,' ',  ,' ',    |           |  ||  |  ||  ||_| |_||   ^^^
                .,,|RIP|,.|RIP|,.,,'==========='==''=='==''=='=======',,....,,,,.,ldb""");
    }

    private static void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! You failed to escape the Haunted Mansion.");
                System.exit(0);
            }
        };
        timer.schedule(task, 60000); // Schedule the task to run after 60,000 milliseconds (1 minute)
    }
}
