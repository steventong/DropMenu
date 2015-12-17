package me.itwl.dropmenu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import me.itwl.dropmenu.R;
import me.itwl.dropmenu.adapter.DoubleDropMenuMainListAdapter;
import me.itwl.dropmenu.adapter.DoubleDropMenuSubListAdapter;
import me.itwl.dropmenu.bean.MenuItemBean;
import me.itwl.dropmenu.callback.OnMenuClickListener;

public class DoubleDropMenuFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private boolean multiSelected;
    private String tag;
    private int bgImageResId;

    private ListView listView, subListView;
    private DoubleDropMenuMainListAdapter mainAdapter;
    private DoubleDropMenuSubListAdapter subAdapter;
    private ArrayList<MenuItemBean> menuItemBeans;
    private Map<Integer, MenuItemBean> selectionMenuBeans;

    private OnMenuClickListener mListener;

    public static DoubleDropMenuFragment newInstance(ArrayList<MenuItemBean> menuItemBeans, boolean multiSelected, String tag) {
        return newInstance(menuItemBeans, multiSelected, tag, -1);
    }

    public static DoubleDropMenuFragment newInstance(ArrayList<MenuItemBean> menuItemBeans, boolean multiSelected, String tag, int backGroundResId) {
        DoubleDropMenuFragment fragment = new DoubleDropMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, menuItemBeans);
        args.putBoolean(ARG_PARAM2, multiSelected);
        args.putString(ARG_PARAM3, tag);
        args.putInt(ARG_PARAM4, backGroundResId);
        fragment.setArguments(args);
        return fragment;
    }

    public DoubleDropMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menuItemBeans = getArguments().getParcelableArrayList(ARG_PARAM1);
            multiSelected = getArguments().getBoolean(ARG_PARAM2);
            tag = getArguments().getString(ARG_PARAM3);
            bgImageResId = getArguments().getInt(ARG_PARAM4, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_double_drop_menu, container, false);
        if (bgImageResId != -1) {
            rootView.setPadding(0, 20, 0, 0);
            rootView.setBackgroundResource(bgImageResId);
        }
        listView = (ListView) rootView.findViewById(R.id.listView);
        subListView = (ListView) rootView.findViewById(R.id.subListView);
        listView.setOnItemClickListener(this);
        subListView.setOnItemClickListener(this);

        mainAdapter = new DoubleDropMenuMainListAdapter(getActivity());
        listView.setAdapter(mainAdapter);

        subAdapter = new DoubleDropMenuSubListAdapter(getActivity(), multiSelected);
        subListView.setAdapter(subAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainAdapter.replaceAll(menuItemBeans);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnMenuClickListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement OnMenuClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.listView) {
            //更新一级分类选中状态
            // 这个状态仅仅表示点击了哪一个栏目
            mainAdapter.setFocusSelectionAt(position);
            //更新对应的二级菜单
            subAdapter.replaceAll(menuItemBeans.get(position).getSubItem());

            //一级菜单点到了『不限』 直接隐藏菜单
            if (mainAdapter.getItem(position).getId() == -1) {
                subAdapter.clearSelection();
                mListener.onMenuClick(tag, mainAdapter.getItem(position));
            } else {
                //更新二级菜单 是否选中默认的{不限}
                subAdapter.updateSelectedParentPosition(position);
                if (selectionMenuBeans != null && selectionMenuBeans.get(position) != null) {
                    subAdapter.setSelectionAt(selectionMenuBeans.get(position));
                }
            }
        } else if (i == R.id.subListView) {
            //二级菜单选中
            subAdapter.setSelectionAt(subAdapter.getItem(position));
            mListener.onMenuClick(tag, subAdapter.getItem(position));
        }
    }

    public void setSelectionAt(Map<Integer, MenuItemBean> beans) {
        selectionMenuBeans = beans;
        if (subAdapter != null) {
            for (Map.Entry<Integer, MenuItemBean> entry : beans.entrySet()) {
                if (entry.getKey() >= 0) {
                    subAdapter.setSelectionAt(entry.getValue());
                }
            }
        }
    }

}
