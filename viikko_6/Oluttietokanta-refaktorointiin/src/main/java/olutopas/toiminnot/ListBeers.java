package olutopas.toiminnot;

import java.util.List;
import IO.IO;
import olutopas.model.Beer;
import olutopas.tietokanta.Datamapper;

public class ListBeers implements Toiminto {

    private IO io;
    private Datamapper dm;

    public ListBeers(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }

    @Override
    public void suorita() {
        List<Beer> beers = dm.listaaBeerit();
        for (Beer beer : beers) {
            io.tulosta(beer.toString());
            if (beer.getRatings() != null && !beer.getRatings().isEmpty()) {
                 io.tulosta("  ratings given " + beer.getRatings().size() + " average " + beer.averageRating());
            } else {
                 io.tulosta("  no ratings");
            }
        }
    }
}
