package main.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Testing {

    public static void main(String[] args) {
        int[][] ints = new int[6][16];
        for(int[] arr : ints){
            Arrays.fill(arr, 0);
        }
        for(int[] arr : ints){
            System.out.println(Arrays.toString(arr));
        }
    }
}
