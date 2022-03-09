package DBEntities;

import java.util.ArrayList;

public class Intervista extends  Riferimento {
    private String doi;
    private String mezzo;
    private ArrayList<String> ospiti;


    protected Intervista(Builder builder) {
        super(builder);
        this.doi = builder.doi;
        this.mezzo = builder.mezzo;
        this.ospiti = builder.ospiti;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String doi;
        private String mezzo;
        private ArrayList<String> ospiti;

        @Override
        public Builder getThis() { return this; }

        public Intervista build() { return new Intervista(this); }

        public Builder doi(String doi) {
            this.doi = doi;
            return this;
        }

        public Builder mezzo(String mezzo) {
            this.mezzo = mezzo;
            return this;
        }

        public Builder ospiti(ArrayList<String> ospiti) {
            this.ospiti = ospiti;
            return this;
        }
    }
}
