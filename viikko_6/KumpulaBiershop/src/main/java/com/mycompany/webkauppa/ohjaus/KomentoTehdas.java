package com.mycompany.webkauppa.ohjaus;

import com.mycompany.webkauppa.sovelluslogiikka.Ostoskori;

public class KomentoTehdas {
    
    public Komento ostoksenLisaysKoriin(Ostoskori ostoskori, long tuoteId){
        return new OstoksenLisaysKoriin(ostoskori, tuoteId);
    }
    
    public Komento OstoksenPoistoKorista(Ostoskori ostoskori, long tuoteId){
        return new OstoksenPoistoKorista(ostoskori, tuoteId);
    }
    
    public Komento OstoksenSuoritus(String nimi, String osoite, String luottokorttinumero, Ostoskori kori){
        return new OstoksenSuoritus(nimi, osoite, luottokorttinumero, kori);
    }
}
