package olutopas.tietokanta;

import java.util.List;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Rating;
import olutopas.model.User;

public interface Datamapper {

    void setKayttaja(User kayttaja);

    User getKayttaja();

    Brewery haeBreweryNimella(String n);

    void tallennBrewery(Brewery b);

    void tallennaBeer(Beer b);

    Beer haeBeer(Integer id);

    void tallennaUser(User u);

    Brewery haeBrewery(Integer id);

    Beer haeBeerNimella(String n);

    List<Brewery> listaaBreweryt();

    List<Beer> listaaBeerit();

    User haeKayttajaNimella(String n);

    void tallennaRating(Rating r);

    List<User> listaaKayttajat();
}
