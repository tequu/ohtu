package olutopas.toiminnot;

import java.util.List;
import IO.IO;
import olutopas.model.Brewery;
import olutopas.tietokanta.Datamapper;

public class ListBreweries implements Toiminto {

    private IO io;
    private Datamapper dm;

    public ListBreweries(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }

    @Override
    public void suorita() {
        List<Brewery> breweries = dm.listaaBreweryt();
        for (Brewery brewery : breweries) {
            io.tulosta(brewery.toString());
        }
    }
}
