package me.itwl.dropmenu.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 2015/11/04 0004.
 */
public abstract class MenuBaseListAdapter<T> extends BaseAdapter {
    public Context context;
    public List<T> data;

    public MenuBaseListAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<T> _data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        if (_data != null) {
            data.addAll(_data);
            notifyDataSetChanged();
        }
    }

    public void replaceAll(List<T> _data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        data.clear();
        if (_data != null) {
            data.addAll(_data);
        }
        notifyDataSetChanged();
    }
}
