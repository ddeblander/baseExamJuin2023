package bibliotheque.mvp.presenter;

import bibliotheque.metier.Auteur;
import bibliotheque.metier.Ouvrage;
import bibliotheque.metier.TypeOuvrage;

import java.util.List;

public interface SpecialOuvragePresenter {
    void setAuteurPresenter(Presenter<Auteur> auteurPresenter);

    Auteur choixAuteur();

    void listerExemplaire(Ouvrage o);

    void listerExemplaire(Ouvrage o, boolean enLocation);

    List<Ouvrage> listerTypeOuvrage(TypeOuvrage to);
    void rechercher(TypeOuvrage to,String idUnique);
}
