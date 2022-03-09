package DBEntities;

public class Legge extends Riferimento {
    private String numero;
    private String tipo;
    private String codice;


    protected Legge(Builder builder) {
        super(builder);
        this.numero = builder.numero;
        this.tipo = builder.tipo;
        this.codice = builder.codice;
    }

    public static Builder build() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String numero;
        private String tipo;
        private String codice;

        @Override
        public Builder getThis() { return this; }

        public Legge build() { return new Legge(this); }

        public Builder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder codice(String codice) {
            this.codice = codice;
            return this;
        }
    }
}
