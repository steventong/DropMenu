package me.itwl.dropmenu.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Steven on 2015/11/04 0004.
 */
public class MenuItemBean implements Parcelable {
    private int id;
    private String name;
    private int position;
    private int parentPosition;
    private Bundle extras;

    private List<MenuItemBean> subItem;

    public MenuItemBean() {
    }

    protected MenuItemBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        position = in.readInt();
        parentPosition = in.readInt();
        extras = in.readBundle();
        subItem = in.createTypedArrayList(MenuItemBean.CREATOR);
    }

    public static final Creator<MenuItemBean> CREATOR = new Creator<MenuItemBean>() {
        @Override
        public MenuItemBean createFromParcel(Parcel in) {
            return new MenuItemBean(in);
        }

        @Override
        public MenuItemBean[] newArray(int size) {
            return new MenuItemBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItemBean> getSubItem() {
        return subItem;
    }

    public void setSubItem(List<MenuItemBean> subItem) {
        this.subItem = subItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItemBean bean = (MenuItemBean) o;

        if (id != bean.id) return false;
        if (name != null ? !name.equals(bean.name) : bean.name != null) return false;
        return !(subItem != null ? !subItem.equals(bean.subItem) : bean.subItem != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(position);
        dest.writeInt(parentPosition);
        dest.writeTypedList(subItem);
        dest.writeBundle(extras);
    }
}
