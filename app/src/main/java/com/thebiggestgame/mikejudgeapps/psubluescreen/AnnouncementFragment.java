package com.thebiggestgame.mikejudgeapps.psubluescreen;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class AnnouncementFragment extends Fragment {
    // the fragment initialization parameters
    private static final String TITLE = "param1";
    private static final String MESSAGE = "param2";

    private String mTitle;
    private String mMessage;

    private TextView mTitleTextView;
    private TextView mMessageTextView;


    public static AnnouncementFragment newInstance(Announcement announcement) {
        AnnouncementFragment fragment = new AnnouncementFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, announcement.getTitle());
        args.putString(MESSAGE, announcement.getMessage());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mMessage = getArguments().getString(MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_announcement, container, false);

        mTitleTextView = (TextView)v.findViewById(R.id.title);
        mMessageTextView = (TextView)v.findViewById(R.id.message);

        mTitleTextView.setText(mTitle);
        mMessageTextView.setText(mMessage);

        return v;

    }



}
