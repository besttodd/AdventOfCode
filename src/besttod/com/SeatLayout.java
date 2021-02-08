package besttod.com;

import java.util.Arrays;

public class SeatLayout {
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

    private int checkPrevious(int x, int y, int z) {
        int count = 0;
        for (int c = y - z; c <= y + z; c++) {
            if (layout[x - z][c] == '#') {
                count++;
            }
        }
        return count;
    }

    private int checkNext(int x, int y, int z) {
        int count = 0;
        for (int c = y - z; c <= y + z; c++) {
            if (layout[x + z][c] == '#') {
                count++;
            }
        }
        return count;
    }

    private int checkLeftRight(int x, int y, int z) {
        int count = 0;
        if (layout[x][y - z] == '#') {
            count++;
        }
        if (layout[x][y + z] == '#') {
            count++;
        }
        return count;
    }

    private int checkEdges(int x, int y, int z) {
        int count = 0;
        int maxX = layout.length - 1;
        int maxY = layout[0].length - 1;
        if ((x == 0 && (y == 0 || y == maxY)) || (x == maxX && (y == 0 || y == maxY))) {
            count += checkCorners(x, y, z);
        } else if (x == 0) {
            count += checkNext(x, y, z);
            count += checkLeftRight(x, y, z);
        } else if (y == 0) {
            if (layout[x - z][y] == '#') {
                count++;
            }
            if (layout[x - z][y + z] == '#') {
                count++;
            }
            if (layout[x][y + z] == '#') {
                count++;
            }
            if (layout[x + z][y] == '#') {
                count++;
            }
            if (layout[x + z][y + z] == '#') {
                count++;
            }
        } else if (x == layout.length - 1) {
            count += checkPrevious(x, y, z);
            count += checkLeftRight(x, y, z);
        } else if (y == layout[x].length - 1) {
            if (layout[x - z][y] == '#') {
                count++;
            }
            if (layout[x - z][y - z] == '#') {
                count++;
            }
            if (layout[x][y - z] == '#') {
                count++;
            }
            if (layout[x + z][y] == '#') {
                count++;
            }
            if (layout[x + z][y - z] == '#') {
                count++;
            }
        }
        return count;
    }

    private int checkCorners(int x, int y, int z) {
        int count = 0;
        if (x == 0 && y == 0) {
            if (layout[x][y + z] == '#') {
                count++;
            }
            if (layout[x + z][y] == '#') {
                count++;
            }
            if (layout[x + z][y + z] == '#') {
                count++;
            }
        } else if (x == 0 && y == layout[x].length) {
            if (layout[x][y - z] == '#') {
                count++;
            }
            if (layout[x + z][y] == '#') {
                count++;
            }
            if (layout[x + z][y - z] == '#') {
                count++;
            }
        } else if (x == layout.length - 1 && y == 0) {
            if (layout[x][y + z] == '#') {
                count++;
            }
            if (layout[x - z][y] == '#') {
                count++;
            }
            if (layout[x - z][y + z] == '#') {
                count++;
            }
        } else if (x == layout.length - 1 && y == layout[x].length - 1) {
            if (layout[x][y - z] == '#') {
                count++;
            }
            if (layout[x - z][y] == '#') {
                count++;
            }
            if (layout[x - z][y - z] == '#') {
                count++;
            }
        }
        return count;
    }


    //returns number of occupied seats adjacent to seat x,y
    public int checkSurrounds(int x, int y, int z) {
        int count = 0;
        if (x == 0 | y == 0 | x == layout.length - 1 | y == layout[x].length - 1) {
            count += checkEdges(x, y, z);
        } else {
            count += checkPrevious(x, y, z);
            count += checkNext(x, y, z);
            count += checkLeftRight(x, y, z);
        }
        return count;
    }

    public void seatOccupied() {
        occupiedSeats++;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }
}
