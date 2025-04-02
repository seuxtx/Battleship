import java.util.Scanner;

public class Battleships {
    Scanner scanner = new Scanner(System.in);
    public static int numRows = 12;
    public static int numCols = 12;
    public static int playerShips;
    public static int compShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    public static void main(String[] args) {
        System.out.println("***** Welcome to Battleship ******");
        System.out.println("The sea is empty of vessels, lets set sail");

        createOcean();
        deployPlayerShips();
        deployCompShips();

        do {
            Battle();
        } while (playerShips != 0 && compShips != 0);

        gameOver();
    }

    public static void createOcean() {
        System.out.print("  ");
        for (int i = 0; i < numCols; i++) {
            System.out.print(i);
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0) {
                    System.out.print(i + "|" + grid[i][j]);
                } else if (j == grid[i].length - 1) {
                    System.out.print(grid[i][j] + "|" + i);
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < numCols; i++) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void deployPlayerShips() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nDeploy your ships: ");
        playerShips = 5;

        for (int i = 1; i <= playerShips; ) {
            System.out.print("Enter X Coordinate for your " + i + " ship: ");
            int x = input.nextInt();
            System.out.print("Enter Y Coordinate for your " + i + " ship: ");
            int y = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y].equals(" ")) {
                grid[x][y] = "@";
                i++;
            } else if (grid[x][y].equals("@")) {
                System.out.println("You already have a ship on this grid spot, only 1 ship per location.");
            } else {
                System.out.println("Invalid placement, keep ships within the grid.");
            }
        }
        printOceanMap();
    }

    public static void deployCompShips() {
        System.out.println("\nComputer is deploying their ships");
        compShips = 5;

        for (int i = 1; i <= compShips; ) {
            int x = (int) (Math.random() * 12);
            int y = (int) (Math.random() * 12);

            if (grid[x][y].equals(" ")) {
                grid[x][y] = "x";
                System.out.println(i + ". Ship DEPLOYED");
                i++;
            }
        }
        printOceanMap();
    }

    public static void Battle() {
        playerTurn();
        computerTurn();
        printOceanMap();

        System.out.println("Your ships: " + playerShips + " | Computer ships: " + compShips);
    }

    public static void playerTurn() {
        System.out.println("\nYour Turn");
        Scanner input = new Scanner(System.in);
        int x, y;

        do {
            System.out.print("Enter X Coordinate: ");
            x = input.nextInt();
            System.out.print("Enter Y Coordinate: ");
            y = input.nextInt();

            if (x >= 0 && x < numRows && y >= 0 && y < numCols) {
                if (grid[x][y].equals("x")) {
                    System.out.println("Enemy Ship has been foundered.");
                    grid[x][y] = "!";
                    compShips--;
                } else if (grid[x][y].equals("@")) {
                    System.out.println("You hit your own ship!");
                    grid[x][y] = "x";
                    playerShips--;
                    compShips++;
                } else {
                    System.out.println("Missed shot.");
                    grid[x][y] = "-";
                }
            } else {
                System.out.println("Invalid coordinates. Try again.");
            }
        } while (x < 0 || x >= numRows || y < 0 || y >= numCols);
    }

    public static void computerTurn() {
        System.out.println("\nComputer's Turn");
        int x, y;

        do {
            x = (int) (Math.random() * 12);
            y = (int) (Math.random() * 12);

            if (x >= 0 && x < numRows && y >= 0 && y < numCols) {
                if (grid[x][y].equals("@")) {
                    System.out.println("The enemy sank one of your ships.");
                    grid[x][y] = "x";
                    playerShips--;
                    compShips++;
                } else if (grid[x][y].equals("x")) {
                    System.out.println("The enemy hit their own ship!");
                    grid[x][y] = "!";
                } else {
                    System.out.println("Computer missed.");
                    missedGuesses[x][y] = 1;
                }
            }
        } while (x < 0 || x >= numRows || y < 0 || y >= numCols);
    }

    public static void gameOver() {
        System.out.println("Your ships: " + playerShips + " | Computer Ships: " + compShips);
        if (playerShips > 0 && compShips <= 0) {
            System.out.println("Hooray! You won the battle.");
        } else {
            System.out.println("Sorry, you lost the battle.");
        }
    }

    public static void printOceanMap() {
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < numCols; i++) System.out.print(i);
        System.out.println();

        for (int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");
            for (int y = 0; y < grid[x].length; y++) {
                System.out.print(grid[x][y]);
            }
            System.out.println("|" + x);
        }

        System.out.print("  ");
        for (int i = 0; i < numCols; i++) System.out.print(i);
        System.out.println();
    }
}
