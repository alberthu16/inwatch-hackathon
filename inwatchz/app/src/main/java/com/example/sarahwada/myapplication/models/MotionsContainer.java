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
    // timeout 10 seconds
    private long timeout = 10000;

    public enum UserAction {
        PUSH, PULL, TWIST, PUNCH, TAP
    }

    public MotionsContainer() {
        mAllMotions = new ArrayList();
        mAllMotions.add(new Motion("Push", R.raw.boom, UserAction.PUSH, timeout));
        mAllMotions.add(new Motion("Pull", R.raw.boom, UserAction.PULL, timeout));
        mAllMotions.add(new Motion("Twist", R.raw.boom, UserAction.TWIST, timeout));
        mAllMotions.add(new Motion("Punch", R.raw.boom, UserAction.PUNCH, timeout));
//        mAllMotions.add(new Motion("Tap", R.raw.boom, UserAction.TAP, timeout));

    }

    public Motion random() {
        Random rand = new Random();
        int randomNum = rand.nextInt(mAllMotions.size());
        return mAllMotions.get(randomNum);
    }

}
