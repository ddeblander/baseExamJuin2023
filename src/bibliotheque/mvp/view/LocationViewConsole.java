package bibliotheque.mvp.view;

import bibliotheque.metier.Exemplaire;
import bibliotheque.metier.Lecteur;
import bibliotheque.metier.Location;
import bibliotheque.mvp.presenter.LocationPresenter;
import bibliotheque.mvp.presenter.SpecialLocationPresenter;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static bibliotheque.utilitaires.Utilitaire.*;

public class LocationViewConsole extends AbstractViewConsole<Location> implements SpecialLocationViewConsole {
    @Override
    protected void rechercher() {

    }

    @Override
    protected void modifier() {
        int choix = choixElt(ldatas);
        Location l = ldatas.get(choix-1);
        do {
            try {
                l.enregistrerRetour();
                break;
            } catch (Exception e) {
                System.out.println("erreur :" + e);
            }
        }while(true);
        presenter.update(l);
        ldatas=presenter.getAll();//rafraichissement
        affListe(ldatas);
    }

    @Override
    protected void ajouter() {
        Lecteur l =((LocationPresenter)presenter).choixLecteur();
        Exemplaire ex = ((LocationPresenter)presenter).choixExemplaire();
        if(ex.enLocation()) {
            affMsg("exemplaire en location");
            return;
        }
        Location loc = new Location(l,ex);
        presenter.add(loc);
    }

    @Override
    protected void special() {
        int choix =  choixElt(ldatas);
        Location l = ldatas.get(choix-1);

        List options = new ArrayList<>(Arrays.asList("calculer amende","enregistrer retour","suppression et archivage","fin"));
        do {
            int ch = choixListe(options);

            switch (ch) {

                case 1:
                    amende(l);
                    break;
                case 2:
                    retour(l);
                    break;
                case 3 :deleteAndSave();
                case 4 :return;
            }
        } while (true);
    }

    @Override
    public void retour(Location l) {
        if(l.getExemplaire().enLocation()) ((SpecialLocationPresenter)presenter).enregistrerRetour(l);
        else affMsg("exemplaire pas en location");
    }

    @Override
    public void amende(Location l) {
        ((SpecialLocationPresenter)presenter).calculerAmende(l);
    }
 //question 6
    public void deleteAndSave()
    {
        LocalDate deleteDate;
        System.out.println("insérez la date limite des suppression");
        deleteDate=lecDate();
        ObjectOutputStream oos=null;
        try{
            FileOutputStream fos = new FileOutputStream("archive.txt");
            oos=new ObjectOutputStream(fos);
            Iterator i = ldatas.iterator();
            Location l;
            while(i.hasNext())
            {
                l=(Location) i.next();
                if(l.getDateLocation().isBefore(deleteDate))
                {
                    oos.writeBytes(l.toString());
                    oos.writeBytes("\n");
                    i.remove();
                }
            }
            System.out.println("Archivage effectués");
            oos.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
