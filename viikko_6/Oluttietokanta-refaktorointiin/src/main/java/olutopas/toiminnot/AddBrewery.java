package olutopas.toiminnot;

import IO.IO;
import olutopas.model.Brewery;
import olutopas.tietokanta.Datamapper;

public class AddBrewery implements Toiminto {

    private IO io;
    private Datamapper dm;

    public AddBrewery(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }
    
    @Override
    public void suorita() {
        io.tulosta("brewery to add: ");
        String name = io.lueRivi();
        Brewery brewery = dm.haeBreweryNimella(name);

        if (brewery != null) {
            io.tulosta(name + " already exists!");
            return;
        }

        dm.tallennBrewery(new Brewery(name));
    }
}
