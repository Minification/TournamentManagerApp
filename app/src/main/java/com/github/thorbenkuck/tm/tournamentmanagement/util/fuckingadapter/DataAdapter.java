package com.github.thorbenkuck.tm.tournamentmanagement.util.fuckingadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class DataAdapter<M> extends RecyclerView.Adapter<DataAdapter.ItemViewHolder> {

    private List<M> list;

    private int emptyResourceId;

    private int dataResourceId;

    private int variableId;

    private static final int VIEW_TYPE_EMPTY = 0;

    private static final int VIEW_TYPE_DATA = 1;

    public DataAdapter(List<M> list, int emptyResourceId, int dataResourceId, int variableId) {
        this.list = list;
        this.emptyResourceId = emptyResourceId;
        this.dataResourceId = dataResourceId;
        this.variableId = variableId;
    }

    @NonNull
    @Override
    public DataAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, i == VIEW_TYPE_EMPTY ? emptyResourceId : dataResourceId, viewGroup, false);
        Log.wtf("DeineMudder", viewDataBinding+"");
        return new DataAdapter.ItemViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ItemViewHolder itemViewHolder, int i) {
        int viewType = getItemViewType(i);
        if (viewType == VIEW_TYPE_DATA) {
            itemViewHolder.bind(list.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 1 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.size() == 0 ? VIEW_TYPE_EMPTY : VIEW_TYPE_DATA;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object viewModel) {
            binding.setVariable(variableId, viewModel);
            binding.executePendingBindings();
        }

    }

}
