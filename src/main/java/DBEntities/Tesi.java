package DBEntities;

public class Tesi extends Riferimento {
    private String doi;
    private String tipoTesi;
    private String ateneo;


    protected Tesi(Builder builder) {
        super(builder);
        this.doi = builder.doi;
        this.tipoTesi = builder.tipoTesi;
        this.ateneo = builder.ateneo;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String doi;
        private String tipoTesi;
        private String ateneo;

        @Override
        public Builder getThis() { return this; }

        public Tesi build() { return new Tesi(this); }

        public Builder doi(String doi) {
            this.doi = doi;
            return this;
        }

        public Builder tipoTesi(String tipo) {
            this.tipoTesi = tipo;
            return this;
        }

        public Builder ateneo(String ateneo) {
            this.ateneo = ateneo;
            return this;
        }
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTipoTesi() { return tipoTesi; }

    public void setTipoTesi(String tipoTesi) {
        this.tipoTesi = tipoTesi;
    }

    public String getAteneo() {
        return ateneo;
    }

    public void setAteneo(String ateneo) {
        this.ateneo = ateneo;
    }
}
