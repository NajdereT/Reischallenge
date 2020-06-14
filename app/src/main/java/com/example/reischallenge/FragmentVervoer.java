package com.example.reischallenge;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVervoer extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference voertuigen = db.collection("voertuigen");
    private RecyclerView recyclerView;

    private VervoerAdapter vervoerAdapter;


    public FragmentVervoer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_vervoer, container, false);





        recyclerView = view.findViewById(R.id.RecyclerviewVervoer);



        Loadwaterfles(view);

        return view;

    }

    private void Loadwaterfles(View view) {
        Query query = voertuigen.orderBy("voertuignaam", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<VervoerViewModel> options = new FirestoreRecyclerOptions.Builder<VervoerViewModel>()
                .setQuery(query, VervoerViewModel.class)
                .build();

        vervoerAdapter = new VervoerAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(vervoerAdapter);
        vervoerAdapter.startListening();



    }

}
