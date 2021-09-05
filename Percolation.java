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
    private boolean[] grid;
    private WeightedQuickUnionUF percolationCheck;
    private WeightedQuickUnionUF unionFind;
    private int size;
    private int Top;
    private int Bottom;

    
    //creates a gid with all sites blocked off initially
    public Percolation(int N) {
        if (N < 1 )  throw new IllegalArgumentException("Must be larger than 0");
        size = N;
        Top = N*N;
        Bottom = N*N + 1;
        percolationCheck = new WeightedQuickUnionUF((N*N) + 2);
        unionFind = new WeightedQuickUnionUF((N*N) + 1);
        grid = new boolean[N*N];
        for (int i = 0; i < N*N; i++) {
            grid[i] = false;
        }
    }
    
    
    //opens a site, with row and column r and c
    public void open(int r, int c) {
        isValidInput(r, c);
        grid[oneDimensional(r, c)] = true;
        
        if (c < size - 1 && isOpen(r, c + 1))
            union(oneDimensional(r, c), oneDimensional(r, c) + 1);
        
        if (c > 0 && isOpen(r, c - 1))
            union(oneDimensional(r, c), oneDimensional(r, c) - 1);
        
        if(r > 0 && isOpen(r - 1, c))
            union(oneDimensional(r, c), oneDimensional(r, c) - size);
        else if (r == 0)
            union(oneDimensional(r, c), Top);
        
        if (r  < size - 1 && isOpen(r + 1, c))
            union(oneDimensional(r, c), oneDimensional(r, c) + size);
        else if (r == size - 1)
            percolationCheck.union(oneDimensional(r, c), Bottom);          
    }
    
    
    //checks if site is open
    public boolean isOpen(int r, int c) {
        isValidInput(r, c);
        return grid[oneDimensional(r, c)] == true;
    }
    
    
    //checks if site is full
    public boolean isFull(int r, int c) {
        isValidInput(r, c);
        return unionFind.connected(oneDimensional(r, c), Top);
    }
    
    
    //checks to see if percolation happens
    public boolean percolates() {
        return percolationCheck.connected(Top, Bottom);
    }
    
    
    //1d coordinates
    private int oneDimensional(int r, int c) {
        return (r * size) + c;
    }
    
    private void isValidInput(int r, int c) {
        if (r < 0 || r > (size - 1))
                throw new IndexOutOfBoundsException("row index i = " + r + "must be between 0 and " + (size - 1));
        if (c < 0 || c > (size - 1))
                throw new IndexOutOfBoundsException("column index j = " + c + "must be between 0 and " + (size - 1));
    }
    
    private void union(int p, int q) {
        percolationCheck.union(p, q);
        unionFind.union(p, q);
    }






}

