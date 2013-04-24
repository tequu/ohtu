package olutopas.toiminnot;

import IO.IO;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.tietokanta.Datamapper;

public class FindBrewery implements Toiminto {

    private IO io;
    private Datamapper dm;
    
    public FindBrewery(IO io, Datamapper dm){
        this.io = io;
        this.dm = dm;
    }
    
    @Override
    public void suorita() {
        io.tulosta("brewery to find: ");
        String n = io.lueRivi();
        Brewery foundBrewery = dm.haeBreweryNimella(n);

        if (foundBrewery == null) {
            io.tulosta(n + " not found");
            return;
        }

        io.tulosta(foundBrewery.toString());
        for (Beer bier : foundBrewery.getBeers()) {
            io.tulosta("   " + bier.getName());
        }
    }
    
}
