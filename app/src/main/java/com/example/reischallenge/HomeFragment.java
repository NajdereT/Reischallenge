package com.example.reischallenge;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;


/**
 */
public class HomeFragment extends Fragment {
    private ViewPageAdapter viewpageadapter;
    private CardView cardViewaccount;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardViewaccount = view.findViewById(R.id.cardViewaccount);

        ViewPager viewPager = view.findViewById(R.id.Viewpager);
        viewpageadapter = new ViewPageAdapter(getFragmentManager());
        viewPager.setAdapter(viewpageadapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tabCall = tabLayout.getTabAt(0);
        tabCall.setIcon(R.drawable.ic_card_travel_24px);

        TabLayout.Tab tabHeart = tabLayout.getTabAt(1);
        tabHeart.setIcon(R.drawable.ic_hotel_24px);

        TabLayout.Tab tabContacts = tabLayout.getTabAt(2);
        tabContacts.setIcon(R.drawable.ic_local_airport_24px);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:

                        break;
                    case 2:
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });


        cardViewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Binnenkort kunt u hier inloggen en uw accountgegevens zien.", Toast.LENGTH_SHORT ).show();

            }
        });

        return view;

    }



}
