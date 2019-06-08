package barletta.coding.barlettapp.javaClass;

public class Coupon {

    private int ID;
    private int IDLocale;
    private String NomeLocale;
    private String Descrizione;

    public Coupon(){}

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getIDLocale() {
        return IDLocale;
    }

    public void setIDLocale(int IDLocale) {
        this.IDLocale = IDLocale;
    }

    public String getNomeLocale() {
        return NomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        NomeLocale = nomeLocale;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }
}
