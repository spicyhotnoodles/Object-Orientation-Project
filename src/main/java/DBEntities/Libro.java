package DBEntities;

import java.util.List;

public class Libro extends Riferimento {
    private List<String> autori;
    private String pagine;
    private String isbn;
    private String serie;
    private String volume;

    public Libro(Builder builder) {
        super(builder);
        this.autori = builder.autori;
        this.pagine = builder.pagine;
        this.isbn = builder.isbn;
        this.serie = builder.serie;
        this.volume = builder.volume;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Riferimento.Builder<Builder> {
        private List<String> autori;
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

        public Builder autori(List<String> autori) {
            this.autori = autori;
            return this;
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
        return "Libro{" +
                super.toString() +
                "autori=" + autori +
                ", pagine='" + pagine + '\'' +
                ", isbn='" + isbn + '\'' +
                ", serie='" + serie + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

    public List<String> getAutori() {
        return autori;
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
}
