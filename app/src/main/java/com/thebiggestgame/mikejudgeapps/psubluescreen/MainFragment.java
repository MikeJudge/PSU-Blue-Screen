package com.thebiggestgame.mikejudgeapps.psubluescreen;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.net.URL;
import java.util.LinkedList;



public class MainFragment extends Fragment {
    private ListView mListView;
    private SwipeRefreshLayout mPullBar;
    private ArrayAdapter<Announcement> mArrayAdapter;


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
            return tracker.getEvents();
        }

        @Override
        protected void onPostExecute(LinkedList<Announcement> list) {
            mArrayAdapter.clear();
            mArrayAdapter.addAll(list);
            mPullBar.setRefreshing(false); //stop the refresh bar animation
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                FragmentTransaction ft = getFragmentManager().beginTransaction();
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
