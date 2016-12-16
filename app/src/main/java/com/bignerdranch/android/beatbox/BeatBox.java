package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rana_ on 12/15/2016.
 */

public class BeatBox {
    private static final String TAG = "BEATBOX";
    private static final String SOUNDS_FOLDER ="sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundsPool;


    public BeatBox(Context context){
        mAssets = context.getAssets();
        mSoundsPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId =mSoundsPool.load(afd,1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        mSoundsPool.play(soundId, 1.0f,1.0f,1,0,1.0f);
    }

    private void loadSounds(){

        String[] soundNames;

        try{
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "loadSounds: Found ["+soundNames.length +"] Sound");
        }catch(Exception ee){
            Log.e(TAG, "loadSounds: COULDN'T LOAD SOUNDS",ee);
            ee.printStackTrace();
            return;
        }

        for(String filename : soundNames){
            try{
                String assetPath = SOUNDS_FOLDER +"/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }

    }

    public List<Sound> getSounds(){
        return mSounds;
    }

}
