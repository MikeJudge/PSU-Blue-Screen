package com.thebiggestgame.mikejudgeapps.psubluescreen;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.net.URL;
import java.util.LinkedList;



public class ListFragment extends Fragment {
    public static final int EVENTS = 1;
    public static final int CANCELLATIONS = 2;
    public static final String SETTING = "Setting";

    private ListView mListView;
    private SwipeRefreshLayout mPullBar;
    private ArrayAdapter<Announcement> mArrayAdapter;

    private int mInfoDisplay;

    /*this task refreshes the blue screen feed by creating a BlueScreenTracker object
      calling the refreshFeed() method, and lastly adding the Announcements from the
      blue screen to the arrayadapter for the list. By default the arraylist will
      automatically update the listview
     */
    private class RefreshFeedTask extends AsyncTask<URL, Integer, LinkedList<Announcement>> {
        @Override
        protected LinkedList<Announcement> doInBackground(URL... urls) {
            BlueScreenTracker tracker = new BlueScreenTracker();
            tracker.refreshFeed();
            if (mInfoDisplay == CANCELLATIONS)
                return tracker.getCancellations();
            else
                return tracker.getEvents();
        }

        @Override
        protected void onPostExecute(LinkedList<Announcement> list) {
            mArrayAdapter.clear();
            mArrayAdapter.addAll(list);
            mPullBar.setRefreshing(false); //stop the refresh bar animation
        }
    }

    public static ListFragment newInstance(int infoSetting) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(SETTING, infoSetting);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfoDisplay = getArguments().getInt(SETTING, EVENTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, R.id.item_text_view);

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView)v.findViewById(R.id.announcement_list);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announcement clicked = mArrayAdapter.getItem(position);
                AnnouncementFragment fragment = AnnouncementFragment.newInstance(clicked);

                //puts this fragment on the back stack, and starts a new AnnouncementFragment
                //containing the clicked Announcement as the data
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        mPullBar = (SwipeRefreshLayout)v.findViewById(R.id.refresh_bar);
        mPullBar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RefreshFeedTask().execute();
            }
        });
        mPullBar.setColorSchemeColors(getResources().getColor(R.color.Penn_State_Blue));

        new RefreshFeedTask().execute();
        //gets the announcements in the listview without need for user refresh on app startup

        return v;
    }






}
