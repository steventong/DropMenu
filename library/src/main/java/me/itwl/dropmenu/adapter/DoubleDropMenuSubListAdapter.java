package me.itwl.dropmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.itwl.dropmenu.R;
import me.itwl.dropmenu.bean.MenuItemBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wumeifang on 15/11/5.
 */
public class DoubleDropMenuSubListAdapter extends MenuBaseListAdapter<MenuItemBean> {

    public boolean multiSelection = false;
    public Map<Integer, MenuItemBean> selectedItems;
    public int selectedPosition = 0;

    public DoubleDropMenuSubListAdapter(Context context, boolean multiSelection) {
        super(context, null);
        this.multiSelection = multiSelection;
        selectedItems = new HashMap<>();
    }

    /**
     * 单选的菜单项目
     * 如果菜单是多选的 在这里存成一个list
     *
     * @param selectedItem
     */
    public void setSelectionAt(MenuItemBean selectedItem) {
        if (!multiSelection) {
            selectedItems.clear();
        }
        selectedItems.put(selectedItem.getParentPosition(), selectedItem);
        notifyDataSetChanged();
    }

    /**
     * 判断一下 如果这个栏目没有选中 就默认选中第一项
     */
    public void updateSelectedParentPosition(int parentPosition) {
        if (multiSelection && selectedItems.get(parentPosition) == null) {
            setSelectionAt(data.get(0));
        }
    }

    /**
     * 清除所有的选中状态
     */
    public void clearSelection(){
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MenuItemBean menu = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_menu_double_sublist, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.menu_item_name);
            holder.checked = (ImageView) convertView.findViewById(R.id.menu_item_selected);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(menu.getName());

        holder.name.setSelected(menu.equals(selectedItems.get(menu.getParentPosition())));
        holder.checked.setVisibility(menu.equals(selectedItems.get(menu.getParentPosition())) ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        ImageView checked;
    }

}
