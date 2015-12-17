package me.itwl.dropmenu.callback;

import me.itwl.dropmenu.bean.MenuItemBean;

/**
 * Created by Steven on 2015/11/04 0004.
 */
public interface OnMenuClickListener {

    void onMenuClick(String tag, MenuItemBean bean);

    void onMenuStatusChanged(String tag, boolean expand);
}
