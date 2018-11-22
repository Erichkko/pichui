package com.pi.core.uikit.bottomdialog.listener;

import com.pi.core.uikit.bottomdialog.model.Item;

/**
 * Created by CC on 2018/3/9.
 */

public interface OnItemClickListener {
    void onItemClick(int position, Item item);
    void onItemLongClick(int position, Item item);
}
