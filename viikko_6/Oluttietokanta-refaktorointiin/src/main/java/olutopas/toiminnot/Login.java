package olutopas.toiminnot;

import IO.IO;
import olutopas.model.User;
import olutopas.tietokanta.Datamapper;

public class Login implements Toiminto {

    private IO io;
    private Datamapper dm;

    public Login(IO io, Datamapper dm) {
        this.io = io;
        this.dm = dm;
    }
    
    
    @Override
    public void suorita() {
        while (true) {
            io.tulosta("\nLogin (give ? to register a new user)\n");

            System.out.print("username: ");
            String name = io.lueRivi();

            if (name.equals("?")) {
                registerUser();
                continue;
            }

            User user = dm.haeKayttajaNimella(name);

            if (user != null) {
                dm.setKayttaja(user);
                break;
            }
            io.tulosta("unknown user");
        }
    }
    private void registerUser() {
        io.tulosta("Register a new user");
        io.tulosta("give username: ");
        String name = io.lueRivi();
        User u = dm.haeKayttajaNimella(name);
        if (u != null) {
           io.tulosta("user already exists!");
            return;
        }
        dm.tallennaUser(new User(name));
        io.tulosta("user created!\n");
    }
}
