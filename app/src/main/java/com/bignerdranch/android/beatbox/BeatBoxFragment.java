package com.bignerdranch.android.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.beatbox.databinding.FragmentBeatBoxBinding;
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rana_ on 12/15/2016.
 */

public class BeatBoxFragment extends Fragment {

    public static Fragment newInstance(){
        return new BeatBoxFragment();
    }

    public Fragment createFragment(){
        return new BeatBoxFragment();
    }

    private BeatBox mBeatBox;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBeatBox = new BeatBox(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_beat_box,container, false);

        //Notice how I didn't need to make a new instance variable for the recycler view and then set it?
        //That is what the binding does!
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        //Again, notice how I am not calling findViewbyID
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return binding.getRoot();
    }
    /**************** Sound Holder Class ************/
    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;

        public SoundHolder(ListItemSoundBinding binding){
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind (Sound sound){
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }
    }

    /**************** Sound Adapter Class **********/
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{

        private List<Sound> mSounds = new ArrayList<>();

        public SoundAdapter (List<Sound> sounds){
            mSounds= sounds;
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            //Get the inflator... need that shit...
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            //Get the inflated Binding object
            ListItemSoundBinding binding = DataBindingUtil
                                                .inflate(inflater,R.layout.list_item_sound,parent,false);

            //Return the new instance of the sound holder... (Nothing was bound... just moving whole objects
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }
    }
}
