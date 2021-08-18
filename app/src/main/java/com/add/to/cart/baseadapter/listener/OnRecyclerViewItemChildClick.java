package com.add.to.cart.baseadapter.listener;

import android.view.View;

public interface OnRecyclerViewItemChildClick<T> {
    void OnItemChildClick(View viewChild, T t, int position);
}
