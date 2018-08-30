package com.github.thorbenkuck.tm.tournamentmanagement.util;

public interface ItemBinder<T> {
    int getBindingVariable(T item);

    int getLayoutRes(T t);
}
