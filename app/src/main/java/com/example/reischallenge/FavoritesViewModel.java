package com.example.reischallenge;

import androidx.lifecycle.ViewModel;

public class FavoritesViewModel extends ViewModel {
    private String bestemmingnaam;
    private String bestemmingbeschrijving;
    private String bestemmingfoto;
    private String bestemmingprijs;
    private String bestemmingland;
    private String bestemmingwaardering;

    public FavoritesViewModel(){}

    public FavoritesViewModel(String bestemmingnaam, String bestemmingbeschrijving, String bestemmingfoto, String bestemmingprijs, String bestemmingland, String bestemmingwaardering){
        this.bestemmingnaam = bestemmingnaam;
        this.bestemmingbeschrijving = bestemmingbeschrijving;
        this.bestemmingfoto = bestemmingfoto;
        this.bestemmingprijs = bestemmingprijs;
        this.bestemmingland = bestemmingland;
        this.bestemmingwaardering = bestemmingwaardering;

    }

    public String getbestemmingnaam() {
        return bestemmingnaam;
    }

    public String getbestemmingland() {
        return bestemmingland;
    }

    public String getbestemmingbeschrijving() {
        return bestemmingbeschrijving;
    }

    public String getbestemmingfoto() { return bestemmingfoto; }

    public String getbestemmingprijs()  { return bestemmingprijs; }

    public String getBestemmingwaardering() { return bestemmingwaardering; }



}
