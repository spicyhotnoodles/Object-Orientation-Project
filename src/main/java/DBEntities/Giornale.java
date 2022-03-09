package DBEntities;

public class Giornale extends Riferimento {
    private String issn;
    private String testata;
    private String sezione;

    protected Giornale(Builder builder) {
        super(builder);
        this.issn = builder.issn;
        this.testata = builder.testata;
        this.sezione = builder.sezione;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String issn;
        private String testata;
        private String sezione;

        @Override
        public Builder getThis() { return this; }

        public Giornale build() { return new Giornale(this); }

        public Builder issn(String issn) {
            this.issn = issn;
            return this;
        }

        public Builder testata(String testata) {
            this.testata = testata;
            return this;
        }

        public Builder sezione(String sezione) {
            this.sezione = sezione;
            return this;
        }
    }

}
