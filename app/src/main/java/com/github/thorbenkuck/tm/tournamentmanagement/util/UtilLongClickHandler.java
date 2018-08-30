package com.github.thorbenkuck.tm.tournamentmanagement.util;

@FunctionalInterface
public interface UtilLongClickHandler<T> {

    boolean onClick(T t);

}
