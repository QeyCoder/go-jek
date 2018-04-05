package com.gojek.helper;

import com.gojek.model.Slot;

import java.util.Comparator;

/**
 * Created by Gaurav on 05/04/18.
 */


public class SlotComparator implements Comparator<Slot> {


    @Override
    public int compare(Slot o1, Slot o2) {
        return o1.getId()-o2.getId();
    }
}
