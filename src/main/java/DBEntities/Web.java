package DBEntities;

public class Web extends Riferimento {
    private String url;
    private String sito;
    private String tipoSito;

    public Web(Builder builder) {
        super(builder);
        this.url = builder.url;
        this.sito = builder.sito;
        this.tipoSito = builder.tipoSito;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String url;
        private String sito;
        private String tipoSito;

        @Override
        public Builder getThis() {
            return this;
        }

        public Web build() { return new Web(this); }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder sito(String sito) {
            this.sito = sito;
            return this;
        }

        public Builder tipoSito(String tipoSito) {
            this.tipoSito = tipoSito;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Web{" +
                "url='" + url + '\'' +
                ", sito='" + sito + '\'' +
                ", tipoSito='" + tipoSito + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public String getSito() {
        return sito;
    }

    public String getTipoSito() {
        return tipoSito;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSito(String sito) {
        this.sito = sito;
    }

    public void setTipoSito(String tipoSito) {
        this.tipoSito = tipoSito;
    }
}
