package DBEntities;

public class Legge extends Riferimento {
    private String numero;
    private String tipo;
    private String codiceLegge;


    protected Legge(Builder builder) {
        super(builder);
        this.numero = builder.numero;
        this.tipo = builder.tipo;
        this.codiceLegge = builder.codiceLegge;
    }

    public static Builder build() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String numero;
        private String tipo;
        private String codiceLegge;

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

        public Builder codiceLegge(String codiceLegge) {
            this.codiceLegge = codiceLegge;
            return this;
        }
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoLegge() {
        return tipo;
    }

    public void setTipoLegge(String tipo) {
        this.tipo = tipo;
    }

    public String getCodiceLegge() {
        return codiceLegge;
    }

    public void setCodiceLegge(String codiceLegge) { this.codiceLegge = codiceLegge; }
}
