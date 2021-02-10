package besttod.com;

public class SeatLayout_Day11 {
    private final static int[][] OFFSETS = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private final char[][] layout;
    private int occupiedSeats;

    public SeatLayout_Day11(String[] input) {
        occupiedSeats = 0;
        layout = new char[input.length][input[0].length()];
        setLayout(input);
    }

    public void setLayout(String[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length(); j++) {
                layout[i][j] = input[i].charAt(j);
            }
        }
    }

    public char[][] getLayout() {
        return layout;
    }

    public void showLayout() {
        for (char[] chars : layout) {
            System.out.println(chars);
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public void seatOccupied() {
        occupiedSeats++;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public int countAdjacent(int x, int y) {
        int count = 0;
        for (int[] offset : OFFSETS) {
            int row = x + offset[0];
            int col = y + offset[1];
            if (row < 0 || row >= layout.length || col < 0 || col >= layout[x].length) {
                continue;
            }
            if (layout[row][col] == '#') {
                count++;
            }
        }
        return count;
    }

    public int countVisible(int x, int y) {
        int count = 0;
        for (int[] offset : OFFSETS) {
            int row = x;
            int col = y;
            while (true) {
                row += offset[0];
                col += offset[1];
                if (row < 0 || row >= layout.length || col < 0 || col >= layout[x].length) {
                    break;
                }
                if (layout[row][col] == 'L') {
                    break;
                }
                if (layout[row][col] == '#') {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public int countOccupied(int x, int y, String pref) {
        int count = 0;
        switch (pref) {
            case "adjacent":
                count = countAdjacent(x, y);
                break;
            case "visible":
                count = countVisible(x, y);
                break;
            default:
                System.out.println("Invalid preference.");
        }
        return count;
    }
}
