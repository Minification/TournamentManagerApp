package com.github.thorbenkuck.tm.tournamentmanagement.util.fuckingadapter;

import java.util.List;
import java.util.Set;

public interface BindableAdapter<T> {

    void setData(List<T> items);

    void changedPositions(Set<Integer> positions);
}
