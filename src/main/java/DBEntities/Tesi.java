package DBEntities;

public class Tesi extends Riferimento {
    private String doi;
    private String tipo;
    private String ateneo;


    protected Tesi(Builder builder) {
        super(builder);
        this.doi = builder.doi;
        this.tipo = builder.tipo;
        this.ateneo = builder.ateneo;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String doi;
        private String tipo;
        private String ateneo;

        @Override
        public Builder getThis() { return this; }

        public Tesi build() { return new Tesi(this); }

        public Builder doi(String doi) {
            this.doi = doi;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder ateneo(String ateneo) {
            this.ateneo = ateneo;
            return this;
        }
    }

}
