package barletta.coding.barlettapp;

public class Locale {

    private int ID, idGestore, tipologia;
    private String nome, descrizione, immagine, descrizioneCompleta;

    public String getDescrizioneCompleta() {
        return descrizioneCompleta;
    }

    public void setDescrizioneCompleta(String descrizioneCompleta) {
        this.descrizioneCompleta = descrizioneCompleta;
    }

    public int getTipologia() {
        return tipologia;
    }

    public void setTipologia(int tipologia) {
        this.tipologia = tipologia;
    }

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
