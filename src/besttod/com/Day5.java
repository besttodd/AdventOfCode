package besttod.com;

//Day 5: Part 1 - A seat specified like 'FBFBBFFRLR', where F means "front", B means "back", L means "left", and R means "right"
//                will break down to row: FBFBBFF of of 128 rows on the plane & seat: RLR of 7 seats in a row.
//                Each letter tells you which half of a region the given seat is in. Start with the whole list of rows;
//                the first letter indicates whether the seat is in the front (0 through 63) or the back (64 through 127).
//                The next letter indicates which half of that region the seat is in, and so on until you're left with exactly one row.
//                The last three characters will be either L or R; these specify exactly one of the 8 columns of seats on the plane
//                (numbered 0 through 7). The same process as above proceeds again, this time with only three steps. L means to keep the lower half, while R means to keep the upper half.
//                Decoding FBFBBFFRLR reveals that it is the seat at row 44, column 5, Where seat ID = row * 8 + column.
//                What is the highest seat ID on a boarding pass?
//Correct answer: 890
//       Part 2 - It's a completely full flight, so your seat should be the only missing boarding pass in your list. However, there's a catch: some of the seats at the very front and back of the plane don't exist
//                on this aircraft, so they'll be missing from your list as well. The seats with IDs +1 and -1 from yours will be in your list.
//                What is the ID of your seat?
//Correct answer: 651

import java.util.List;

public class Day5 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day5_input.txt";
    private final static int ROW_RANGE = 128;
    private final static int COLUMN_RANGE = 8;

    public Day5() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        int[] seatIDs = new int[input.size()];

        int[] rowRange = new int[ROW_RANGE];
        for (int i = 0; i < ROW_RANGE; i++) {
            rowRange[i] = i + 1;
        }

        int[] colRange = new int[COLUMN_RANGE];
        for (int i = 0; i < COLUMN_RANGE; i++) {
            colRange[i] = i;
        }

        for (int j = 0; j < input.size(); j++) {
            String rowCode = input.get(j).substring(0, 7);
            String columnCode = input.get(j).substring(7);
            System.out.println(rowCode + "::" + columnCode);

            int row = findMedian(rowCode, rowRange);
            int column = findMedian(columnCode, colRange);

            int seatID = row * 8 + column;
            seatIDs[j] = seatID;

            System.out.println("Row: " + row + " Column: " + column + " ID: " + seatID);
        }

        sortArray(seatIDs);
        int missingID = findMissing(seatIDs);

        System.out.println("\nHighest SeatID: " + seatIDs[seatIDs.length - 1]);
        System.out.println("Missing SeatID: " + missingID);
    }

    private int findMedian(String code, int[] range) {
        int mid = (range.length / 2) - 1;
        int first = 0;
        int last = range.length - 1;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == 'B' | code.charAt(i) == 'R') {
                first = mid + 1;
            } else {
                last = mid;
            }
            mid = (first + last) / 2;
        }
        return mid;
    }

    private void sortArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {      //swap elements if not in order
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    private int findMissing(int[] array) {
        int mid = 0;
        int first = 0;
        int last = array.length - 1;
        while ((last - first) > 1) {
            mid = (first + last) / 2;
            if ((array[first] - first) != (array[mid] - mid)) {
                last = mid;
            } else if ((array[last] - last) != (array[mid] - mid)) {
                first = mid;
            }
        }
        return array[mid] - 1;
    }
}
