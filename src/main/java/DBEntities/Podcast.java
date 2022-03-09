package DBEntities;

public class Podcast extends Riferimento {
    private String doi;
    private String episodio;
    private String serie;


    protected Podcast(Builder builder) {
        super(builder);
        this.doi = builder.doi;
        this.episodio = builder.episodio;
        this.serie = builder.serie;
    }

    public static Builder build() { return new Builder(); }

    public static class Builder extends Riferimento.Builder<Builder> {
        private String doi;
        private String episodio;
        private String serie;

        @Override
        public Builder getThis() { return this; }

        public Podcast build() { return new Podcast(this); }

        public Builder doi(String doi) {
            this.doi = doi;
            return this;
        }

        public Builder episodio(String episodio) {
            this.episodio = episodio;
            return  this;
        }

        public  Builder serie(String serie) {
            this.serie = serie;
            return this;
        }
    }
}
