package com.github.thorbenkuck.tm.tournamentmanagement.util;

public class CompositeItemBinder<T> implements ItemBinder<T>
{
    private final ConditionalDataBinder<T>[] conditionalDataBinders;

    public CompositeItemBinder(ConditionalDataBinder<T>... conditionalDataBinders)
    {
        this.conditionalDataBinders = conditionalDataBinders;
    }

    @Override
    public int getLayoutRes(T model)
    {
        for (ConditionalDataBinder<T> dataBinder : conditionalDataBinders) {
            if (dataBinder.canHandle(model)) {
                return dataBinder.getLayoutRes(model);
            }
        }

        throw new IllegalStateException();
    }

    @Override
    public int getBindingVariable(T model)
    {
        for (ConditionalDataBinder<T> dataBinder : conditionalDataBinders) {
            if (dataBinder.canHandle(model)) {
                return dataBinder.getBindingVariable(model);
            }
        }

        throw new IllegalStateException();
    }
}