package me.itwl.dropmenu.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import me.itwl.dropmenu.R;
import me.itwl.dropmenu.adapter.SingleDropMenuListAdapter;
import me.itwl.dropmenu.bean.MenuItemBean;
import me.itwl.dropmenu.callback.OnMenuClickListener;

public class SingleDropMenuFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private SingleDropMenuListAdapter adapter;
    private ArrayList<MenuItemBean> menuItemBeans;
    private MenuItemBean selectionMenuBean;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String tag;
    private int bgImageResId;

    private OnMenuClickListener mListener;


    public static SingleDropMenuFragment newInstance(ArrayList<MenuItemBean> menuItemBeans, String tag) {
        SingleDropMenuFragment fragment = new SingleDropMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, menuItemBeans);
        args.putString(ARG_PARAM2, tag);
        fragment.setArguments(args);
        return fragment;
    }

    public static SingleDropMenuFragment newInstance(ArrayList<MenuItemBean> menuItemBeans, String tag, int backGroundResId) {
        SingleDropMenuFragment fragment = new SingleDropMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, menuItemBeans);
        args.putString(ARG_PARAM2, tag);
        args.putInt(ARG_PARAM3, backGroundResId);
        fragment.setArguments(args);
        return fragment;
    }


    public SingleDropMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menuItemBeans = getArguments().getParcelableArrayList(ARG_PARAM1);
            tag = getArguments().getString(ARG_PARAM2);
            bgImageResId = getArguments().getInt(ARG_PARAM3, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_single_drop_menu, container, false);
        if (bgImageResId != -1) {
            rootView.setPadding(0, 20, 0, 0);
            rootView.setBackgroundResource(bgImageResId);
        }
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        adapter = new SingleDropMenuListAdapter(getActivity());
        listView.setAdapter(adapter);
        updateSelection();
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.replaceAll(menuItemBeans);
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
        //点击了哪一个 前台配合显示选中状态
        adapter.setSelectionAt(position);
        mListener.onMenuClick(tag, adapter.getItem(position));
    }

    public void setSelectionAt(MenuItemBean bean) {
        selectionMenuBean = bean;
        updateSelection();
    }

    private void updateSelection() {
        if (menuItemBeans != null) {
            int index = menuItemBeans.indexOf(selectionMenuBean);
            if (index != -1) {
                adapter.setSelectionAt(index);
            }
        }
    }
}
