package DBEntities;

public class Film extends Riferimento {

    private String isan;
    private String genere;
    private String distribuzione;

    protected Film(Builder builder) {
        super(builder);
        this.isan = builder.isan;
        this.genere = builder.genere;
        this.distribuzione = builder.distribuzione;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String isan;
        private String genere;
        private String distribuzione;

        @Override
        public Builder getThis() { return this; }

        public Film build() { return new Film(this); }

        public Builder isan(String isan) {
            this.isan = isan;
            return this;
        }

        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }

        public Builder distribuzione(String distribuzione) {
            this.distribuzione = distribuzione;
            return this;
        }
    }

    public String getIsan() {
        return isan;
    }

    public void setIsan(String isan) {
        this.isan = isan;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getDistribuzione() {
        return distribuzione;
    }

    public void setDistribuzione(String distribuzione) {
        this.distribuzione = distribuzione;
    }
}
