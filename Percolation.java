/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 *
 * @author Myles
 */
public class Percolation {
    
    private static final int TOP = 0;
    private final boolean[][] opened;
    private final int size;
    private final int bottom;
    private int openSites;
    private final WeightedQuickUnionUF qf;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottom = size * size +1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
        openSites = 0;   
    }
    
    //this sets up the grid
    public void open(int row, int col) {
        checkException(row, col);
        opened[row - 1][col  - 1] = true;
        ++openSites;
        
        if (row == 1) {
            qf.union(getQuickFindIndex(row, col), TOP);
        }
        
        if (row == size) {
            qf.union(getQuickFindIndex(row, col), bottom);
        }
        
        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row - 1, col));
        }
        
        if (row < size && isOpen(row + 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row + 1, col));
        }
        
        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col -1));
        }
        
        if (col < size && isOpen(row, col + 1)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col + 1));
        }
    }
    
    private void checkException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }
    
    //this checks if a box is open
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return opened[row - 1][col - 1];
    }
    
    //this returns the open sites
    public int numberOfOpenSites() {
        return openSites;
    }
    
    //checks if box is full
    public boolean isFull(int row, int col) {
        if ((row > 0 && row  <= size) && (col > 0 && col <= size)) {
            return qf.find(TOP) == qf.find(getQuickFindIndex(row, col));
        } else throw new IllegalArgumentException();
    }
    
    private int getQuickFindIndex(int row, int col) {
        return size * (row - 1) + col;
    }
    
    //this checks if it percolates by seeing if the top is connected to the bottom
    public boolean percolates () {
        return qf.find(TOP) == qf.find(bottom);
    }
    
    public static void main(String[] args) {
        
    }
    
}
