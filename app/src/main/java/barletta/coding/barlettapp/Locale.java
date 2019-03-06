package barletta.coding.barlettapp;

public class Locale {

    private int ID, idGestore;
    private String nome, descrizione, immagine;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdGestore() {
        return idGestore;
    }

    public void setIdGestore(int idGestore) {
        this.idGestore = idGestore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
}
