package me.itwl.dropmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.itwl.dropmenu.R;
import me.itwl.dropmenu.bean.MenuItemBean;

/**
 * Created by Steven on 2015/11/03 0003.
 */
public class SingleDropMenuListAdapter extends MenuBaseListAdapter<MenuItemBean> {

    public int selectedPosition = 0;

    public SingleDropMenuListAdapter(Context context) {
        super(context, null);
    }

    public void setSelectionAt(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MenuItemBean menu = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_menu_single, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.menu_item_name);
            holder.checked = (ImageView) convertView.findViewById(R.id.menu_item_selected);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(menu.getName());
        holder.name.setSelected(selectedPosition == position);
        holder.checked.setVisibility(selectedPosition == position ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        ImageView checked;
    }
}
