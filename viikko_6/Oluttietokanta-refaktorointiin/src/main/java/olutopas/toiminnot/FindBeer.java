package olutopas.toiminnot;

import IO.IO;
import olutopas.model.Beer;
import olutopas.model.Rating;
import olutopas.tietokanta.Datamapper;

public class FindBeer implements Toiminto{

    private IO io;
    private Datamapper dm;

    public FindBeer(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }
    
    
    @Override
    public void suorita() {
        io.tulosta("beer to find: ");
        String n = io.lueRivi();
        Beer foundBeer = dm.haeBeerNimella(n);

        if (foundBeer == null) {
            io.tulosta(n + " not found");
            return;
        }

        io.tulosta(foundBeer.toString());

        if (foundBeer.getRatings() != null && !foundBeer.getRatings().isEmpty()) {
            io.tulosta("  number of ratings: " + foundBeer.getRatings().size() + " average " + foundBeer.averageRating());
        } else {
            io.tulosta("no ratings");
        }

        io.tulosta("give rating (leave emtpy if not): ");
        try {
            int rating = Integer.parseInt(io.lueRivi());
            addRating(foundBeer, rating);
        } catch (Exception e) {
        }
    }
    
    private void addRating(Beer foundBeer, int value) {
        Rating rating = new Rating(foundBeer, dm.getKayttaja(), value);
        dm.tallennaRating(rating);
    }
    
}
