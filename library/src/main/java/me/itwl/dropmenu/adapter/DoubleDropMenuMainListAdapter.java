package me.itwl.dropmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.itwl.dropmenu.R;
import me.itwl.dropmenu.bean.MenuItemBean;

/**
 * Created by Steven on 2015/11/03 0003.
 */
public class DoubleDropMenuMainListAdapter extends MenuBaseListAdapter<MenuItemBean> {

    public int focusPosition = -1;

    public DoubleDropMenuMainListAdapter(Context context) {
        super(context, null);
    }

    public void setFocusSelectionAt(int position) {
        focusPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MenuItemBean menu = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_menu_double_main, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.menu_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(menu.getName());
        holder.name.setSelected(focusPosition == position);
        return convertView;
    }

    private class ViewHolder {
        TextView name;
    }
}
