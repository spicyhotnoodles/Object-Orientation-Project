package DBEntities;

public class Convegno extends Riferimento {

    private String doi;
    private String luogo;

    public static Convegno.Builder builder() { return new Convegno.Builder(); }

    public Convegno(Builder builder) {
        super(builder);
        this.doi = builder.doi;
        this.luogo = builder.luogo;
    }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String doi;
        private String luogo;


        @Override
        public Builder getThis() {
            return this;
        }

        public Convegno build() { return new Convegno(this); }

        public Builder doi(String doi) {
            this.doi = doi;
            return this;
        }

        public Builder luogo(String luogo) {
            this.luogo = luogo;
            return this;
        }
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
}
