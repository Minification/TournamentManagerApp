package com.github.thorbenkuck.tm.tournamentmanagement.util;

public class ItemBinderBase<T> implements ItemBinder<T> {

    protected final int bindingVariable;

    protected final int layoutId;

    public ItemBinderBase(int bindingVariable, int layoutId) {
        this.bindingVariable = bindingVariable;
        this.layoutId = layoutId;
    }

    @Override
    public int getBindingVariable(T item) {
        return 0;
    }

    @Override
    public int getLayoutRes(T t) {
        return 0;
    }

}
