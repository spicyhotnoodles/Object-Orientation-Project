package DBEntities;

public class Rivista extends Riferimento {
    private String issn;
    private String pagine;
    private String fascicolo;


    protected Rivista(Builder builder) {
        super(builder);
        this.issn = builder.issn;
        this.pagine = builder.pagine;
        this.fascicolo = builder.fascicolo;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String issn;
        private String pagine;
        private String fascicolo;

        @Override
        public Builder getThis() { return this; }

        public Rivista build() { return new Rivista(this); }

        public Builder issn(String issn) {
            this.issn = issn;
            return this;
        }

        public Builder pagine(String pagine) {
            this.pagine = pagine;
            return this;
        }

        public Builder fascicolo(String fascicolo) {
            this.fascicolo = fascicolo;
            return this;
        }

    }

}
