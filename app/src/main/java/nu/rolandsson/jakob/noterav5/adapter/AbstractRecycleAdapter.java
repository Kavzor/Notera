package nu.rolandsson.jakob.noterav5.adapter;

/**
 * @Copyright
 * Created by Jakob Rolandsson 2018-05-18
 * Intended to provide a clean interface with Recycling adapter
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;


/**
 *
 * @param <E> list adapter type
 */
public abstract class AbstractRecycleAdapter<E> extends
        RecyclerView.Adapter<AbstractRecycleAdapter.ViewHolder> {

    protected List<E> mItems;

    public abstract int getAdapterView();
    public abstract void bindView(ViewHolder holder, E item, int adapterPosition);

    /**
     * Constructs an adapter with an empty list
     */
    public AbstractRecycleAdapter() {
        mItems = new LinkedList<>();
    }

    /**
     * fetches the size of all items in the adapter
     * @return amount of items
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Fetches a specific item
     * @param position item position
     * @return the item
     */
    public E getItem(int position) {
        return mItems.get(position);
    }

    /**
     * Removes an item from the adapter
     * @param position of the item
     */
    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Adds an item to the adapter
     * @param element to add
     */
    public void addItem(E element) {
        List<E> elements = new LinkedList<>();
        elements.add(element);
        elements.addAll(mItems);
        setItems(elements);
    }

    /**
     * Fetches the item id or position
     * @param element to fetch id from
     * @return the id or position
     */
    public int getItemId(E element) {
        return mItems.indexOf(element);
    }

    /**
     * Removes an item from the adapter
     * @param element to remove
     */
    public void removeItem(E element) {
        mItems.remove(element);
        notifyDataSetChanged();
    }

    /**
     * Fetches all added items
     * @return a list of items
     */
    public List<E> getItems() {
        return mItems;
    }

    /**
     * replaces the current list in the adapter
     * @param items to replace with
     */
    public void setItems(List<E> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(getAdapterView(), parent, false);
        return new ViewHolder(view);
    }

    /**
     * Invokes abstract method bindView which should implement the view components
     * @param holder used by the RecycleAdapter
     * @param position  of the current item
     */
    @Override
    public void onBindViewHolder(@NonNull AbstractRecycleAdapter.ViewHolder holder, int position) {
        bindView(holder, mItems.get(position), position);
    }

    /**
     * Internal ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
        }

        /**
         * Use to fetch view components
         * @param id from resources
         * @param <T> view
         * @return the returned view
         */
        public <T extends View> T get(int id) {
            return mView.findViewById(id);
        }
    }
}
