package com.pFrame.controller.alogrithm;

public interface Sorter<T extends Comparable<T>> {
    public void load(T[] elements);

    public void sort();

    public String getPlan();
}
