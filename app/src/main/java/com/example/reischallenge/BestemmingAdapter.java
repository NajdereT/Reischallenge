package com.example.reischallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;


public class BestemmingAdapter extends FirestoreRecyclerAdapter<BestemmingViewModel, BestemmingAdapter.BestemmingHolder> {
    private Context mcontext;
    private OnItemClickListener listener;
    private int lastPosition = 1;
    int delayAnimate = 1000; //global variable

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public BestemmingAdapter(@NonNull FirestoreRecyclerOptions<BestemmingViewModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BestemmingHolder holder, int position, @NonNull BestemmingViewModel model) {


        holder.textViewBestemmingnaam.setText(model.getbestemmingnaam());
        holder.textViewBestemmingland.setText(model.getbestemmingland());
        holder.textViewBestemmingwaardering.setText(model.getBestemmingwaardering());
        holder.textViewBestemmingbeschrijving.setText(model.getbestemmingbeschrijving());
        holder.textViewBestemmingprijs.setText("€" + model.getbestemmingprijs());
        holder.progressBarwaardering.setProgress(Integer.parseInt(model.getBestemmingwaardering()));
        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                ;
        Glide.with(holder.itemView.getContext()).applyDefaultRequestOptions(reqOpt).load(model.getbestemmingfoto()).into(holder.imageViewBestemmingfoto);

        setAnimation(holder.itemView, position);

    }

    @NonNull
    @Override
    public BestemmingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_holder,parent, false);
        return new BestemmingHolder(v);
    }

    class BestemmingHolder extends RecyclerView.ViewHolder {
        TextView textViewBestemmingnaam;
        TextView textViewBestemmingbeschrijving;
        TextView textViewBestemmingprijs;
        TextView textViewBestemmingland;
        TextView textViewBestemmingwaardering;
        ProgressBar progressBarwaardering;
        ImageView imageViewBestemmingfoto;



        public BestemmingHolder(@NonNull View itemView) {

            super(itemView);
            textViewBestemmingnaam = itemView.findViewById(R.id.TextViewBestemmingnaam);

            textViewBestemmingbeschrijving = itemView.findViewById(R.id.TextViewbestemmingbeschrijving);
            textViewBestemmingprijs = itemView.findViewById(R.id.TextViewbestemmingprijs);
            textViewBestemmingland = itemView.findViewById(R.id.TextViewbestemmingland);
            textViewBestemmingwaardering = itemView.findViewById(R.id.textViewBestemmingwaardering);
            progressBarwaardering = itemView.findViewById(R.id.progressBar);
            imageViewBestemmingfoto = itemView.findViewById(R.id.imageViewBestemmingfoto);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    listener.OnItemClick(getSnapshots().getSnapshot(position), position);

                }
            });



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
    public void SetOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
