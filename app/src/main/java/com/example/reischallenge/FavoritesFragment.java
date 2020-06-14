package com.example.reischallenge;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
public class FavoritesFragment extends Fragment {

    private FavoritesAdapter favoritesAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference opgeslagen = db.collection("opgeslagen");
    private BottomSheetBehavior bottomSheetBehavior;

    private RelativeLayout relativeLayout;


    private TextView textViewbestemmingnaamInfo;
    private TextView textViewbestemminglandInfo;
    private TextView textViewbestemmingbeschrijvingInfo;
    private TextView textViewbestemmingprijsInfo;
    private TextView textViewbestemmingwaarderingInfo;
    private ImageView imageviewbestemmingfotoInfo;

    private RecyclerView recyclerView;

    private ImageButton imageButtonbookmarkInfo;

    private ProgressBar progressBarwaarderinginfo;

    private ImageButton imageButton;
    private CardView cardViewaccount;

    private BottomNavigationView navBar;

    private boolean firstImageShown = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.Recyclerviewfavorites);

        relativeLayout = view.findViewById(R.id.bottom_viewF);

        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setPeekHeight(0);
        textViewbestemmingnaamInfo = view.findViewById(R.id.textViewbestemmingnaaminfoF);
        textViewbestemminglandInfo = view.findViewById(R.id.textViewbestemminglandinfoF);
        textViewbestemmingbeschrijvingInfo = view.findViewById(R.id.textViewbestemmingbeschrijvinginfoF);
        textViewbestemmingprijsInfo = view.findViewById(R.id.textViewbestemmingprijsinfoF);
        textViewbestemmingwaarderingInfo = view.findViewById(R.id.textViewBestemmingwaarderinginfoF);
        imageviewbestemmingfotoInfo = view.findViewById(R.id.imageViewbestemmingfotoinfoF);
        progressBarwaarderinginfo = view.findViewById(R.id.progressBar2F);
        imageButton = view.findViewById(R.id.imageButtonF);
        imageButtonbookmarkInfo = view.findViewById(R.id.imageButtonbookmarkinfoF);
        cardViewaccount = view.findViewById(R.id.cardViewaccountF);

        navBar = getActivity().findViewById(R.id.bottomNavigationView);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                navBar.setVisibility(View.VISIBLE);
            }
        });

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
        Query query = opgeslagen.orderBy("bestemmingland", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FavoritesViewModel> options = new FirestoreRecyclerOptions.Builder<FavoritesViewModel>()
                .setQuery(query, FavoritesViewModel.class)
                .build();

        favoritesAdapter = new FavoritesAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.startListening();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoritesAdapter.deleteitem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        openproductinfo(view);



    }

    private void openproductinfo(View view){
        favoritesAdapter.SetOnItemClickListener(new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                FavoritesViewModel favoritesViewModel = documentSnapshot.toObject(FavoritesViewModel.class);
                assert favoritesViewModel != null;
                final String bestemmingnaam = favoritesViewModel.getbestemmingnaam();
                final String bestemmingland = favoritesViewModel.getbestemmingland();
                final String bestemmingbeschrijving = favoritesViewModel.getbestemmingbeschrijving();
                final String bestemmingprijs = favoritesViewModel.getbestemmingprijs();
                final String bestemmingfoto = favoritesViewModel.getbestemmingfoto();
                String bestemmingwaardering = favoritesViewModel.getBestemmingwaardering();

                textViewbestemmingnaamInfo.setText(bestemmingnaam);
                textViewbestemminglandInfo.setText(bestemmingland);
                textViewbestemmingbeschrijvingInfo.setText(bestemmingbeschrijving);
                textViewbestemmingprijsInfo.setText("€ "+ bestemmingprijs);
                textViewbestemmingwaarderingInfo.setText(bestemmingwaardering);
                progressBarwaarderinginfo.setProgress(Integer.parseInt(bestemmingwaardering));

                Glide.with(Objects.requireNonNull(getContext())).load(bestemmingfoto).into(imageviewbestemmingfotoInfo);

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                navBar.setVisibility(View.GONE);

            }


        });
    }}
