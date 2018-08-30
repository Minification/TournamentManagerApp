package com.github.thorbenkuck.tm.tournamentmanagement.util;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Consumer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Collection;

public class BindingRecyclerViewAdapter<T> extends RecyclerView.Adapter<BindingRecyclerViewAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private final int ITEM_MODEL = -124;

    private ObservableList<T> items;

    private Consumer<T> clickHandler;

    private UtilLongClickHandler<T> longClickHandler;

    private final ItemBinder<T> itemBinder;

    private LayoutInflater inflater;

    private WeakReferenceOnListChangedCallback onListChangedCallback;

    public BindingRecyclerViewAdapter(ItemBinder<T> itemBinder, @Nullable Collection<T> items) {
        this.itemBinder = itemBinder;
        this.onListChangedCallback = new WeakReferenceOnListChangedCallback<>(this);
        setItems(items);
    }

    public void setItems(Collection<T> items) {
        if(items != null) {
            this.items.removeOnListChangedCallback(onListChangedCallback);
            notifyItemRangeRemoved(0, this.items.size());
        }

        if(items instanceof ObservableList) {
            this.items = (ObservableList<T>) items;
            notifyItemRangeInserted(0, items.size());
            this.items.addOnListChangedCallback(onListChangedCallback);
        } else if (items != null) {
            this.items = new ObservableArrayList<>();
            this.items.addOnListChangedCallback(onListChangedCallback);
            this.items.addAll(items);
        } else {
            this.items = null;
        }
    }

    public ObservableList<T> getItems() {
        return items;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if(items != null) {
            items.removeOnListChangedCallback(onListChangedCallback);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int layoutId) {
        if(inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final T item = items.get(i);
        viewHolder.viewDataBinding.setVariable(itemBinder.getBindingVariable(item), item);
        viewHolder.viewDataBinding.getRoot().setTag(ITEM_MODEL, item);
        viewHolder.viewDataBinding.getRoot().setOnClickListener(this);
        viewHolder.viewDataBinding.getRoot().setOnLongClickListener(this);
        viewHolder.viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return itemBinder.getLayoutRes(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void onClick(View view) {
        if(clickHandler != null) {
            T item = (T) view.getTag(ITEM_MODEL);
            clickHandler.accept(item);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(longClickHandler != null) {
            T item = (T) view.getTag(ITEM_MODEL);
            longClickHandler.onClick(item);
            return true;
        }
        return false;
    }

    public void setClickHandler(Consumer<T> clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setLongClickHandler(UtilLongClickHandler<T> longClickHandler) {
        this.longClickHandler = longClickHandler;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding viewDataBinding;

        public ViewHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.viewDataBinding = itemView;
        }
    }

    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback {

        private final WeakReference<BindingRecyclerViewAdapter<T>> adapterReference;

        public WeakReferenceOnListChangedCallback(BindingRecyclerViewAdapter<T> bindingRecyclerViewAdapter)
        {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }
    }

}
