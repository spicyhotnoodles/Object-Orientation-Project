package DBEntities;

public class Libro extends Riferimento {
    private String pagine;
    private String isbn;
    private String serie;
    private String volume;

    protected Libro(Builder builder) {
        super(builder);
        this.pagine = builder.pagine;
        this.isbn = builder.isbn;
        this.serie = builder.serie;
        this.volume = builder.volume;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String pagine;
        private String isbn;
        private String serie;
        private String volume;

        @Override
        public Builder getThis() {
            return this;
        }

        public Libro build() {
            return new Libro(this);
        }

        public Builder pagine(String pagine) {
            this.pagine = pagine;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder serie(String serie) {
            this.serie = serie;
            return this;
        }

        public Builder volume(String volume) {
            this.volume = volume;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Libro{ " +
                "codice='" + super.getCodice() +
                ", titolo='" + super.getTitolo() + '\'' +
                ", autori=" + super.getAutori() +
                ", descrizione='" + super.getDescrizione() + '\'' +
                ", data='" + super.getData() + '\'' +
                ", lingua='" + super.getLingua() + '\'' +
                ", tipo='" + super.getTipo() + '\'' +
                ", categorie=" + super.getCategorie() + '\'' +
                ", tags= " + super.getTags() + '\'' +
                ", rimandi= " + super.getCitazioni() + '\'' +
                ", pagine='" + pagine + '\'' +
                ", isbn='" + isbn + '\'' +
                ", serie='" + serie + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

    public String getPagine() {
        return pagine;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSerie() {
        return serie;
    }

    public String getVolume() {
        return volume;
    }

    public void setPagine(String pagine) {
        this.pagine = pagine;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
