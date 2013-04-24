package olutopas.toiminnot;

import IO.IO;
import olutopas.model.Rating;
import olutopas.model.User;
import olutopas.tietokanta.Datamapper;

public class MyRatings implements Toiminto {

    private IO io;
    private Datamapper dm;

    public MyRatings(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }

    @Override
    public void suorita() {
        User user = dm.getKayttaja();
        io.tulosta("Ratings by " + user.getName());
        for (Rating rating : user.getRatings()) {
            io.tulosta(rating.toString());
        }
    }
}
