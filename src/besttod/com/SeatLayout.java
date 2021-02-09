package besttod.com;

import java.util.Arrays;

public class SeatLayout {
    private final static int[][] OFFSETS = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private char[][] layout;
    private int occupiedSeats;

    public SeatLayout(String[] input) {
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

    public String getRow(int row) {
        return Arrays.toString(layout[row]);
    }

    public String getColumn(int column) {
        StringBuilder seatColumn = new StringBuilder();
        for (char[] chars : layout) {
            seatColumn.append(chars[column]);
        }
        return seatColumn.toString();
    }

    public char getSeat(int x, int y) {
        return layout[x][y];
    }

    public void setSeat(int x, int y, char c) {
        layout[x][y] = c;
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
}
