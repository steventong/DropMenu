package me.itwl.dropmenu.presenter;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import me.itwl.dropmenu.R;
import me.itwl.dropmenu.callback.OnMenuClickListener;
import me.itwl.dropmenu.view.DropMenuView;


/**
 * Created by Steven on 2015/11/04 0004.
 */
public class MenuPresenter implements View.OnClickListener {

    private OnMenuClickListener onMenuClickListener;
    private FragmentManager fragmentManager;
    private DialogFragment showFragment;
    private String lastExpandTag;

    private FrameLayout contentView;
    private View menuWindowFrame;

    public MenuPresenter(Fragment fragment, DropMenuView dropMenuView, boolean isChildFragment) {
        this.fragmentManager = isChildFragment ? fragment.getChildFragmentManager() : fragment.getFragmentManager();
        this.onMenuClickListener = (OnMenuClickListener) fragment;
        this.contentView = dropMenuView.getMenuContainer();
        this.menuWindowFrame = dropMenuView.getPopupWindowFrame();
        this.menuWindowFrame.setOnClickListener(this);
    }

    public MenuPresenter(FragmentActivity fragmentActivity, DropMenuView dropMenuView) {
        this.fragmentManager = fragmentActivity.getSupportFragmentManager();
        this.onMenuClickListener = (OnMenuClickListener) fragmentActivity;
        this.contentView = dropMenuView.getMenuContainer();
        this.menuWindowFrame = dropMenuView.getPopupWindowFrame();
        this.menuWindowFrame.setOnClickListener(this);
    }

    public void menuClick(String tag, DialogFragment menuFragment) {
        hideAllExpandedMenus(tag.equals(lastExpandTag));

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);

        if (menuFragment.isVisible()) {
            fragmentTransaction.hide(menuFragment);
            lastExpandTag = null;
            menuWindowFrame.setVisibility(View.GONE);
            onMenuClickListener.onMenuStatusChanged(tag, false);
        } else {
            lastExpandTag = tag;
            if (menuFragment.isAdded()) {
                fragmentTransaction.show(menuFragment);
            } else {
                fragmentTransaction.add(contentView.getId(), menuFragment);
            }
            onMenuClickListener.onMenuStatusChanged(tag, true);
            showFragment = menuFragment;
            menuWindowFrame.setVisibility(View.VISIBLE);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        hideAllExpandedMenus(true);
    }

    public void hideAllExpandedMenus(boolean anim) {
        if (showFragment != null && showFragment.isVisible()) {
            if (anim) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
                fragmentTransaction.hide(showFragment);
                fragmentTransaction.commit();
            } else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(showFragment);
                fragmentTransaction.commit();
            }
            menuWindowFrame.setVisibility(View.GONE);

            if (lastExpandTag != null) {
                onMenuClickListener.onMenuStatusChanged(lastExpandTag, false);
                lastExpandTag = null;
            }
        }

    }

    public boolean isShowing() {
        if (showFragment == null) {
            return false;
        } else {
            return showFragment.isVisible();
        }
    }
}
