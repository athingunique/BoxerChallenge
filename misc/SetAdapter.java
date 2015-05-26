package com.evanbaker.boxerchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class SetAdapter<T> extends BaseAdapter implements ListAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Set<T> mSet;
    private final int mViewResId;

    private Iterator<T> mTraversalIterator;
    private int traversalIteratorPosition;

    public SetAdapter(Context context, int viewResId, Set<T> set) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResId = viewResId;
        mSet = set;
    }

    @Override
    public int getCount() {
        return mSet.size();
    }

    @Override
    public T getItem(int index) {
        if (index < getCount()) {

            Iterator<T> setIterator = mSet.iterator();
            int count = 0;

            while (count < index && setIterator.hasNext()) {
                count++;
                setIterator.next();
            }

            return setIterator.next();

        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {

        if (view == null || !(view instanceof TextView)) {
            view = mLayoutInflater.inflate(mViewResId, viewGroup, false);
        }

        ((TextView) view).setText(String.valueOf(getItem(index)));

        return view;
    }
}
