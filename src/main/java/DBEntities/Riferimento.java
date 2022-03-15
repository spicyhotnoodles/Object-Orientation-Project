package DBEntities;

import java.util.List;

public class Riferimento {

    private String codice;
    private String titolo;
    private List<String> autori;
    private String descrizione;
    private String data;
    private String lingua;
    private String tipo;
    private List<Categoria> categorie;
    private List<String> tags;
    private List<Riferimento> citazioni;
    private int rimandi;

    protected Riferimento(Builder<?> builder) {
        codice = builder.codice;
        titolo = builder.titolo;
        autori = builder.autori;
        descrizione = builder.descrizione;
        data = builder.data;
        lingua = builder.lingua;
        tipo = builder.tipo;
        categorie = builder.categorie;
        tags = builder.tags;
        citazioni = builder.citazioni;
        rimandi = builder.rimandi;
    }

    public static Builder builder() {
        return new Builder() {
            @Override
            public Builder getThis() {
                return this;
            }
        };
    }

    public abstract static class Builder<T extends Builder<T>> {

        private String codice;
        private String titolo;
        private List<String> autori;
        private String descrizione;
        private String data;
        private String lingua;
        private String tipo;
        private List<Categoria> categorie;
        private List<String> tags;
        private List<Riferimento> citazioni;
        private int rimandi;
        public abstract T getThis();

        public T titolo(String titolo) {
            this.titolo = titolo;
            return this.getThis();
        }

        public T autori(List<String> autori) {
            this.autori = autori;
            return this.getThis();
        }

        public T descrizione(String descrizione) {
            this.descrizione = descrizione;
            return this.getThis();
        }

        public T data(String data) {
            this.data = data;
            return this.getThis();
        }

        public T lingua(String lingua) {
            this.lingua = lingua;
            return this.getThis();
        }

        public T tipo(String tipo) {
            this.tipo = tipo;
            return this.getThis();
        }

        public T categorie(List<Categoria> categorie) {
            this.categorie = categorie;
            return this.getThis();
        }

        public T codice(String codice) {
            this.codice = codice;
            return this.getThis();
        }

        public T tags(List<String> tags) {
            this.tags = tags;
            return this.getThis();
        }

        public T citazioni(List<Riferimento> citazioni) {
            this.citazioni = citazioni;
            return this.getThis();
        }

        public T rimandi(int rimandi) {
            this.rimandi = rimandi;
            return this.getThis();
        }

        public Riferimento build() {
            return new Riferimento(this);
        }

    }
    @Override
    public String toString() {
        return "Riferimento{" +
                "codice='" + codice + '\'' +
                ", titolo='" + titolo + '\'' +
                ", autori=" + autori +
                ", descrizione='" + descrizione + '\'' +
                ", data='" + data + '\'' +
                ", lingua='" + lingua + '\'' +
                ", tipo='" + tipo + '\'' +
                ", categorie=" + categorie +
                ", tags=" + tags +
                ", citazioni=" + citazioni +
                ", rimandi=" + rimandi +
                '}';
    }

    public String getTitolo() {
        return titolo;
    }

    public List<String> getAutori() { return autori; }

    public String getDescrizione() { return descrizione; }

    public String getData() {
        return data;
    }

    public String getLingua() {
        return lingua;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Categoria> getCategorie() { return categorie; }

    public String getCodice() { return codice; }

    public List<String> getTags() { return tags; }

    public List<Riferimento> getCitazioni() { return citazioni; }

    public int getRimandi() { return rimandi; }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutori(List<String> autori) {
        this.autori = autori;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public void setTipoTesi(String tipoTesi) {
        this.tipo = tipoTesi;
    }

    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCitazioni(List<Riferimento> citazioni) {
        this.citazioni = citazioni;
    }

    public void setRimandi(int rimandi) { this.rimandi = rimandi; }
}
