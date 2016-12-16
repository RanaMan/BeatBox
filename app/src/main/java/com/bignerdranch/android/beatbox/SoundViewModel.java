package com.bignerdranch.android.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

/**
 * Created by rana_ on 12/15/2016.
 */

public class SoundViewModel extends BaseObservable {

    public static final String TAG ="SOUNDVIEW MODEL";

    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatbox){
        mBeatBox = beatbox;
    }

    @Bindable
    public String getTitle(){
        return mSound.getName();
    }

    public Sound getSound() {
        return mSound;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        notifyChange();
    }

    public void onClick(){
        mBeatBox.play(mSound);
        Log.d(TAG, "onClick: WHASSUP!");
    }
}
