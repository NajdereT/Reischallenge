package com.example.reischallenge;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBestemmingen extends Fragment {

    private BestemmingAdapter bestemmingadapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private BottomSheetBehavior bottomSheetBehavior;

    private CollectionReference reisbestemmingen = db.collection("reisbestemmingen");
    private RecyclerView recyclerView;

    private RelativeLayout relativeLayout;

    private TextView textViewbestemmingnaamInfo;
    private TextView textViewbestemminglandInfo;
    private TextView textViewbestemmingbeschrijvingInfo;
    private TextView textViewbestemmingprijsInfo;
    private TextView textViewbestemmingwaarderingInfo;
    private ImageView imageviewbestemmingfotoInfo;

    private ImageButton imageButtonbookmarkInfo;

    private ProgressBar progressBarwaarderinginfo;

    private ImageButton imageButton;

    private BottomNavigationView navBar;

    private boolean firstImageShown = true;

    private NestedScrollView nestedScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_bestemmingen, container, false);

        recyclerView = view.findViewById(R.id.Recyclerview);

        relativeLayout = view.findViewById(R.id.bottom_view);
        nestedScrollView = view.findViewById(R.id.Nestedscrollview);

        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(0);
        textViewbestemmingnaamInfo = view.findViewById(R.id.textViewbestemmingnaaminfo);
        textViewbestemminglandInfo = view.findViewById(R.id.textViewbestemminglandinfo);
        textViewbestemmingbeschrijvingInfo = view.findViewById(R.id.textViewbestemmingbeschrijvinginfo);
        textViewbestemmingprijsInfo = view.findViewById(R.id.textViewbestemmingprijsinfo);
        textViewbestemmingwaarderingInfo = view.findViewById(R.id.textViewBestemmingwaarderinginfo);
        imageviewbestemmingfotoInfo = view.findViewById(R.id.imageViewbestemmingfotoinfo);
        progressBarwaarderinginfo = view.findViewById(R.id.progressBar2);
        imageButton = view.findViewById(R.id.imageButton);
        imageButtonbookmarkInfo = view.findViewById(R.id.imageButtonbookmarkinfo);

        navBar = getActivity().findViewById(R.id.bottomNavigationView);



        imageButtonbookmarkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((imageButtonbookmarkInfo != null) && (firstImageShown)) {
                    imageButtonbookmarkInfo.setImageResource(R.drawable.ic_bookmark_border_24px);
                    firstImageShown = false;
                    Toast.makeText(getActivity(), "Bestemming verwijdert uit opgeslagen", Toast.LENGTH_SHORT ).show();

                } else {
                    if (imageButtonbookmarkInfo != null) imageButtonbookmarkInfo.setImageResource(R.drawable.ic_bookmark_24px);
                    firstImageShown = true;
                    Toast.makeText(getActivity(), "Bestemming bewaard in opgeslagen", Toast.LENGTH_SHORT ).show();

                }


            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                navBar.setVisibility(View.VISIBLE);


            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    navBar.setVisibility(View.GONE);

                    //Log.e("BottomSheet", "Expanded");
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    //Log.e("BottomSheet", "Collapsed");
                    navBar.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }});


            hide(view);


        Loadwaterfles(view);



        return view;
    }

    private  void hide(View view){
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            navBar.setVisibility(View.VISIBLE);
        }
        else{navBar.setVisibility(View.GONE);
        }


    }

    private void Loadwaterfles(View view) {
        Query query = reisbestemmingen.orderBy("bestemmingland", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<BestemmingViewModel> options = new FirestoreRecyclerOptions.Builder<BestemmingViewModel>()
                .setQuery(query, BestemmingViewModel.class)
                .build();

        bestemmingadapter = new BestemmingAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(bestemmingadapter);
        bestemmingadapter.startListening();
        openproductinfo(view);



    }

    private void openproductinfo(View view){
        bestemmingadapter.SetOnItemClickListener(new BestemmingAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                BestemmingViewModel bestemmingViewModel = documentSnapshot.toObject(BestemmingViewModel.class);
                assert bestemmingViewModel != null;
                final String bestemmingnaam = bestemmingViewModel.getbestemmingnaam();
                final String bestemmingland = bestemmingViewModel.getbestemmingland();
                final String bestemmingbeschrijving = bestemmingViewModel.getbestemmingbeschrijving();
                final String bestemmingprijs = bestemmingViewModel.getbestemmingprijs();
                final String bestemmingfoto = bestemmingViewModel.getbestemmingfoto();
                final String bestemmingwaardering = bestemmingViewModel.getBestemmingwaardering();


                textViewbestemmingnaamInfo.setText(bestemmingnaam);
                textViewbestemminglandInfo.setText(bestemmingland);
                textViewbestemmingbeschrijvingInfo.setText(bestemmingbeschrijving);
                textViewbestemmingprijsInfo.setText("€ "+ bestemmingprijs);
                textViewbestemmingwaarderingInfo.setText(bestemmingwaardering);
                progressBarwaarderinginfo.setProgress(Integer.parseInt(bestemmingwaardering));

                Glide.with(Objects.requireNonNull(getContext())).load(bestemmingfoto).into(imageviewbestemmingfotoInfo);

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                navBar.setVisibility(View.GONE);
                imageButtonbookmarkInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((imageButtonbookmarkInfo != null) && (firstImageShown)) {
                            imageButtonbookmarkInfo.setImageResource(R.drawable.ic_bookmark_border_24px);
                            firstImageShown = false;
                            Toast.makeText(getActivity(), "Bestemming verwijdert uit opgeslagen", Toast.LENGTH_SHORT ).show();

                        } else {
                            if (imageButtonbookmarkInfo != null) imageButtonbookmarkInfo.setImageResource(R.drawable.ic_bookmark_24px);
                            firstImageShown = true;
                            Toast.makeText(getActivity(), "Bestemming bewaard in opgeslagen", Toast.LENGTH_SHORT ).show();

                            CollectionReference notebookref = FirebaseFirestore.getInstance()
                                    .collection("opgeslagen");
                            notebookref.add(new BestemmingViewModel(bestemmingnaam, bestemmingbeschrijving,bestemmingfoto,bestemmingprijs,bestemmingland,bestemmingwaardering));

                        }


                    }
                });

            }


        });


    }



}
