package com.example.sarahwada.myapplication.models;

import com.example.sarahwada.myapplication.R;

/**
 * Created by yujuncho on 2/21/15.
 */
public class Motion {
    private String mCommand;
    private R mSound;
    private MotionsContainer.UserAction mUserAction;
    private double mDuration;

    public Motion(String command,
                  R sound,
                  MotionsContainer.UserAction userAction,
                  double duration) {
        mCommand= command;
        mSound = sound;
        mUserAction = userAction;
        mDuration = duration;
    }

    public void setCommand(String command) {
        mCommand = command;
    }

    public String getText() {
        return mCommand;
    }

    public void setSound(R sound) {
        mSound = sound;
    }

    public R getSound() {
        return mSound;
    }

    public void setUserAction(MotionsContainer.UserAction userAction) {
        mUserAction = userAction;
    }

    public MotionsContainer.UserAction getUserAction() {
        return mUserAction;
    }

    public void setDuration(double duration) {
        mDuration = duration;
    }

    public double getDuration() {
        return mDuration;
    }
}
