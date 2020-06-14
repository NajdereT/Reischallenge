package com.example.reischallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VervoerAdapter extends FirestoreRecyclerAdapter<VervoerViewModel, VervoerAdapter.VervoerHolder>


{ private Context mcontext;
    private BestemmingAdapter.OnItemClickListener listener;
    private int lastPosition = 1;
    int delayAnimate = 1000; //global variable

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public VervoerAdapter(@NonNull FirestoreRecyclerOptions<VervoerViewModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VervoerAdapter.VervoerHolder holder, int position, @NonNull VervoerViewModel model) {


        holder.textViewVoertuignaam.setText(model.getvoertuignaam());
        holder.textViewVoertuigcapaciteit.setText("max. " + model.getvoertuigcapaciteit()+" passagiers");
        holder.textViewVoertuigleeftijd.setText(model.getvoertuigleeftijd()+" oud" );

        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                ;
        Glide.with(holder.itemView.getContext()).applyDefaultRequestOptions(reqOpt).load(model.getvoertuigafbeelding()).into(holder.imageViewVoertuigafbeelding);

        setAnimation(holder.itemView, position);

    }

    @NonNull
    @Override
    public VervoerAdapter.VervoerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vervoer_holder,parent, false);
        return new VervoerAdapter.VervoerHolder(v);
    }


    class VervoerHolder extends RecyclerView.ViewHolder {
        TextView textViewVoertuignaam;
        TextView textViewVoertuigcapaciteit;
        TextView textViewVoertuigleeftijd;
        ImageView imageViewVoertuigafbeelding;



        public VervoerHolder(@NonNull View itemView) {

            super(itemView);
            textViewVoertuignaam = itemView.findViewById(R.id.textViewVoertuigNaam);


            textViewVoertuigcapaciteit = itemView.findViewById(R.id.textViewVoertuigCapaciteit);
            textViewVoertuigleeftijd = itemView.findViewById(R.id.textViewVoertuigLeeftijd);
            imageViewVoertuigafbeelding = itemView.findViewById(R.id.ImageViewVoertuigAfbeelding);




        }
    }
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
    public interface OnItemClickListener {

        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void SetOnItemClickListener(BestemmingAdapter.OnItemClickListener listener) {

        this.listener = listener;
    }
}
