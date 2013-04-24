package olutopas.toiminnot;

import IO.IO;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.tietokanta.Datamapper;

public class AddBeer implements Toiminto {

    private IO io;
    private Datamapper dm;

    public AddBeer(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }

    @Override
    public void suorita() {
        io.tulosta("to which brewery: ");
        String name = io.lueRivi();
        Brewery brewery = dm.haeBreweryNimella(name);

        if (brewery == null) {
            io.tulosta(name + " does not exist");
            return;
        }

        io.tulosta("beer to add: ");

        name = io.lueRivi();

        Beer exists = dm.haeBeerNimella(name);
        if (exists != null) {
            io.tulosta(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        dm.tallennBrewery(brewery);
        io.tulosta(name + " added to " + brewery.getName());
    }
}
