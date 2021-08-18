package com.add.to.cart.baseadapter.listener;

import android.view.View;

public interface OnRecyclerViewItemChildLongClick<T> {
    void OnItemChildLongClick(View viewChild, T t, int position);
}
