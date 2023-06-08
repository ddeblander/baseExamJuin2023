package bibliotheque.mvp.presenter;


import bibliotheque.metier.*;
import bibliotheque.mvp.model.DAO;
import bibliotheque.mvp.model.SpecialOuvrage;
import bibliotheque.mvp.view.ViewInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class OuvragePresenter extends Presenter<Ouvrage> implements SpecialOuvragePresenter{

    private Presenter<Auteur> auteurPresenter;

    public OuvragePresenter(DAO<Ouvrage> model, ViewInterface<Ouvrage> view, Comparator<Ouvrage>cmp) {
        super(model,view,cmp);
    }
    @Override
    public void setAuteurPresenter(Presenter<Auteur> auteurPresenter) {
        this.auteurPresenter = auteurPresenter;
    }

    @Override
    public Auteur choixAuteur(){
       return  auteurPresenter.selection();
    }



    @Override
    public void  listerExemplaire(Ouvrage o){
        view.affList(((SpecialOuvrage)model).listerExemplaire(o));
    }
    @Override
    public void listerExemplaire(Ouvrage o, boolean enLocation){
        view.affList(((SpecialOuvrage)model).listerExemplaire(o,enLocation));
    }

    @Override
    public List<Ouvrage> listerTypeOuvrage(TypeOuvrage to)
    {
        List<Ouvrage> lO= model.getAll();
        List ll = null;
        if(to.equals(TypeOuvrage.LIVRE))
        {
            ll = new ArrayList<Livre>();
            for(Ouvrage o : lO)
            {
                if(o.getTo().equals(to))
                {
                    ll.add( o);

                }
            }


        }
        else if(to.equals(TypeOuvrage.CD))
        {
            ll = new ArrayList<CD>();
            for(Ouvrage o : lO)
            {
                if(o.getTo().equals(to))
                {
                    ll.add( o);
                }
            }

        }
        else if(to.equals(TypeOuvrage.DVD))
        {
            ll = new ArrayList<DVD>();
            for(Ouvrage o : lO)
            {
                if(o.getTo().equals(to))
                {
                    ll.add( o);
                }
            }

        }
        Collections.sort(ll);
        return ll;
    }

    @Override
    public void rechercher(TypeOuvrage to,String idUnique)
    {
        List<Ouvrage> lO=listerTypeOuvrage(to);
        List<Ouvrage> lAdd = new ArrayList<>();
        if(to.equals(TypeOuvrage.LIVRE))
        {
            for (Ouvrage o : lO)
            {
                if(((Livre) o).getIsbn().equals(idUnique))
                {
                    lAdd.add(o);
                }
            }
        }
        else if(to.equals(TypeOuvrage.CD))
        {
            for (Ouvrage o : lO)
            {
                if(((CD) o).getCode()==Long.parseLong(idUnique))
                {
                    lAdd.add(o);
                }
            }
        }
        else if(to.equals(TypeOuvrage.DVD))
        {
            for (Ouvrage o : lO)
            {
                if(((DVD) o).getCode() ==Long.parseLong(idUnique))
                {
                    lAdd.add(o);
                }
            }
        }
        if(lAdd.size()==0)
        {
            view.affMsg("recherche infructueuse");

        }
        view.affList(lAdd);

    }


}
