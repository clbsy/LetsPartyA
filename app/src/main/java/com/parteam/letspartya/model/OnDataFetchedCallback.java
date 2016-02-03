package com.parteam.letspartya.model;

import java.util.ArrayList;

/**
 * Created by xulin on 2016/2/2.
 */
public interface OnDataFetchedCallback<T> {
    void onDataFetched(ArrayList<T> list);
}
