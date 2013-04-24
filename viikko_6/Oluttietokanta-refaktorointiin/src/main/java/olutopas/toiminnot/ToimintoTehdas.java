package olutopas.toiminnot;

import java.util.HashMap;
import java.util.Map;
import IO.IO;
import olutopas.tietokanta.Datamapper;

public class ToimintoTehdas {
    private Map<String, Toiminto> toiminnot;
    
    public ToimintoTehdas(IO io, Datamapper dm){
        toiminnot = new HashMap<String, Toiminto>();
        toiminnot.put("q", new Ulos(io));
        toiminnot.put("1", new FindBrewery(io, dm));
        toiminnot.put("2", new FindBeer(io, dm));
        toiminnot.put("3", new AddBeer(io, dm));
        toiminnot.put("4", new ListBreweries(io, dm));
        toiminnot.put("5", new ListBeers(io, dm));
        toiminnot.put("6", new AddBrewery(io, dm));
        toiminnot.put("7", new MyRatings(io, dm));
        toiminnot.put("8", new ListUsers(io, dm));
        toiminnot.put("tuntematon", new Unknow(io));
        toiminnot.put("login", new Login(io, dm));
    }
    
    public Toiminto hae(String m){
        Toiminto t = toiminnot.get(m);
        if(t == null){
            t = toiminnot.get("tuntematon");
        }
        return t;
    }
}
