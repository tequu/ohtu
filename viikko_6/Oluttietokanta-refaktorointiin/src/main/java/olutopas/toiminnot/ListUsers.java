package olutopas.toiminnot;

import java.util.List;
import IO.IO;
import olutopas.model.User;
import olutopas.tietokanta.Datamapper;

public class ListUsers implements Toiminto {

    private IO io;
    private Datamapper dm;

    public ListUsers(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }

    @Override
    public void suorita() {
        List<User> users = dm.listaaKayttajat();
        for (User user : users) {
            io.tulosta(user.getName() + " " + user.getRatings().size() + " ratings");
        }
    }
}
