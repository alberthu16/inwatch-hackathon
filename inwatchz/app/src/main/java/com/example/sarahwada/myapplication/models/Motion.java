package com.example.sarahwada.myapplication.models;

/**
 * Created by yujuncho on 2/21/15.
 */
public class Motion {
    private String mCommand;
    private int mSound;
    private int mImage;
    private MotionsContainer.UserAction mUserAction;
    // duration ms
    private long mDuration;

    public Motion(String command,
                  int sound,
                  int image,
                  MotionsContainer.UserAction userAction,
                  long duration) {
        mCommand= command;
        mSound = sound;
        mImage = image;
        mUserAction = userAction;
        mDuration = duration;
    }

    public void setCommand(String command) {
        mCommand = command;
    }

    public String getText() {
        return mCommand;
    }

    public void setSound(int sound) {
        mSound = sound;
    }

    public int getSound() {
        return mSound;
    }

    public void setImage(int image) { mImage = image; }

    public int getImage() { return mImage; }

    public void setUserAction(MotionsContainer.UserAction userAction) {
        mUserAction = userAction;
    }

    public MotionsContainer.UserAction getUserAction() {
        return mUserAction;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public long getDuration() {
        return mDuration;
    }
}
