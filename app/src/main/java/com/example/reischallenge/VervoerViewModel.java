package com.example.reischallenge;

import androidx.lifecycle.ViewModel;

public class VervoerViewModel extends ViewModel {

    private String voertuignaam;
    private String voertuigcapaciteit;
    private String voertuigafbeelding;
    private String voertuigleeftijd;
    

    public VervoerViewModel(){}

    public VervoerViewModel(String voertuignaam, String voertuigcapaciteit, String voertuigafbeelding, String voertuigleeftijd){
        this.voertuignaam = voertuignaam;
        this.voertuigcapaciteit = voertuigcapaciteit;
        this.voertuigafbeelding = voertuigafbeelding;
        this.voertuigleeftijd = voertuigleeftijd;
     

    }

    public String getvoertuignaam() {
        return voertuignaam;
    }


    public String getvoertuigcapaciteit() {
        return voertuigcapaciteit;
    }

    public String getvoertuigafbeelding() { return voertuigafbeelding; }

    public String getvoertuigleeftijd()  { return voertuigleeftijd; }




}




