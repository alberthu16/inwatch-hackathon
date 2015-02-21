package com.example.sarahwada.myapplication.models;

import com.example.sarahwada.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yujuncho on 2/21/15.
 */
public class MotionsContainer {
    private List<Motion> mAllMotions;

    public enum UserAction {
        PUSH, PULL, TWIST
    }

    public MotionsContainer() {
        mAllMotions = new ArrayList();
        mAllMotions.add(new Motion("Push", R.raw.boom, UserAction.PUSH, 2L));
    }

    public Motion random() {
        Random rand = new Random();
        int randomNum = rand.nextInt(mAllMotions.size());
        return mAllMotions.get(randomNum);
    }

}
