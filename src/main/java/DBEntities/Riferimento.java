package DBEntities;

import java.util.Date;

public class Riferimento {

    private String titolo;
    private String descrizione;
    private String data;
    private String lingua;
    private String tipo;
    private String note;

    protected Riferimento(Builder<?> builder) {
        titolo = builder.titolo;
        descrizione = builder.descrizione;
        data = builder.data;
        lingua = builder.lingua;
        tipo = builder.tipo;
        note = builder.note;
    }

    public Riferimento() {
    }

    /*public static Builder builder() {
        return new Builder() {
            @Override
            public Builder getThis() {
                return this;
            }
        };
    }*/

    abstract static class Builder<T extends Builder<T>> {
        private String titolo;
        private String descrizione;
        private String data;
        private String lingua;
        private String tipo;
        private String note;

        public abstract T getThis();

        public T titolo(String titolo) {
            this.titolo = titolo;
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

        public T note(String note) {
            this.note = note;
            return this.getThis();
        }

        public Riferimento build() {
            return new Riferimento(this);
        }

    }

    @Override
    public String toString() {
        return "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data='" + data + '\'' +
                ", lingua='" + lingua + '\'' +
                ", tipo='" + tipo + '\'' +
                ", note='" + note + '\'';
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getData() {
        return data;
    }

    public String getLingua() {
        return lingua;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNote() {
        return note;
    }
}
