/*
 * @author Collin Crowthers
 */

public class Backracking {

    public static void main(String[] args) {
        //setup the array. In this case it is predetermined.
        //test case 1 
        int[][] test = new int[][] {{1, 1, 0, 1, 1, 1},
                                    {0, 0, 0, 0, 1, 0},
                                    {0, 0, 1, 0, 1, 0},
                                    {0, 0, 1, 0, 1, 1},
                                    {1, 1, 0, 0, 0, 1}};

        
        //map the matrix
        int MaxSize = 0;
        int MaxIsland = 0;
        MaxIsland = mapIslands(test, MaxSize, MaxIsland);
        
        //print the matrix, with X as the largest island, and 1 as others
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
                if (test[i][j] == MaxIsland+1) {
                    System.out.print("X  ");
                } else if (test[i][j] != 0) {
                    System.out.print("1  ");
                } else System.out.print("0  ");
            }
            System.out.println("");
        }
        
    }

//this loops through every cell in the matrix
//if a cell is promising it will explore around it and map the island
//as well as calculate the size and mark the cell with its assigned region
//(depth first search)
//if not, it will move on to the next cell
//this was originally written recursively, which allowed for one less method 
//to be needed, however with a backtracking algorithm in this case that would
//check over each cell in the array more than once potentially, so using loops 
//is more effecient and readable
    static int mapIslands(int[][] matrix, int MaxSize, int MaxIsland) {
        int nextIsland = 1;
        int newSize = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //check if the cell is an island, find its size
                newSize = exploreIsland(matrix, i, j, nextIsland);

                //find the size of the island, and check if its larger than the max
                if (newSize > MaxSize) {
                    MaxIsland = nextIsland;
                    MaxSize = newSize;
                }

                //if the island exists then set the nextIsland so it can be seperate
                if (newSize != 0) {
                    nextIsland++;
                }
            }
        }
        //print out the largest region, its size
        System.out.println("Region: "+MaxIsland+", Size: "+MaxSize);
        return MaxIsland;
    }

    //recursively finds the whole island, and returns its size
    static int exploreIsland(int[][] matrix, int i, int j, int currentIsland) {
        //initialized to 1 because it will only return size if the promising is true
        int islandSize = 1;
        if (promising(matrix, i, j)) {
            //mark the island so it wont be revisited
            matrix[i][j] = 1 + currentIsland;

            //these for loops allow for not having 8 calls all typed out
            for (int k = i - 1; k <= i + 1; k++) {
                for (int l = j - 1; l <= j + 1; l++) {
                    //making sure same cell isnt revisited
                    if (k != i || l != j) {
                        islandSize += exploreIsland(matrix, k, l, currentIsland);
                    }
                }
            }
            return islandSize;
        }
        //else this isnt an island and return 0
        return 0;
    }

    //this returns if the cell is a valid possible cell, and if it is a 1
    //this means that if this is not the case, the cell can be skipped over
    static boolean promising(int[][] matrix, int i, int j) {
        if (i < 0 || i > matrix.length - 1 || j < 0 || j > matrix[i].length - 1 || matrix[i][j] != 1) {
            return false;
        }
        return true;
    }

}
