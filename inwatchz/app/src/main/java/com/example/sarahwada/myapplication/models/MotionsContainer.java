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

        mAllMotions.add(new Motion("Push", R.raw.push, R.drawable.push, UserAction.PUSH, 1000));
        mAllMotions.add(new Motion("Pull", R.raw.pull, R.drawable.pull, UserAction.PULL, 1000));
        mAllMotions.add(new Motion("Twist", R.raw.twist, R.drawable.twist, UserAction.TWIST, 1000));
        mAllMotions.add(new Motion("Punch", R.raw.punch, R.drawable.punch, UserAction.PUNCH, 1000));
//        mAllMotions.add(new Motion("Tap", R.raw.tap, R.drawable.tap, UserAction.TAP, 2000));

    }

    public Motion random() {
        Random rand = new Random();
        int randomNum = rand.nextInt(mAllMotions.size());
        return mAllMotions.get(randomNum);
    }

}
