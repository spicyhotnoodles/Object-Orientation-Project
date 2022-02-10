# Progetto Object Orientation A.A. 2021-2022
## Traccia
Si sviluppi un sistema informativo, composto da una base di dati relazionale e da un applicativo Java dotato di GUI (Swing o JavaFX), per la gestione di bibliografie. Il sistema permette agli utenti di salvare e organizzare i propri riferimenti bibliografici. In particolare, è possibile inserire/modificare/rimuovere riferimenti bibliografici di diverso tipo (e.g.: articoli scientifici su conferenza o rivista, libri, risorse on-line, dataset, etc.). Ciascun riferimento è caratterizzato da un titolo univoco, un elenco di autori, una data, un URL (obbligatorio solo per risorse on-line), un DOI (facoltativo, ma univoco ove presente), e una descrizione testuale in cui l’utente può indicare aspetti significativi. Inoltre, un riferimento può essere associato a un insieme di rimandi, ovvero di altri riferimenti presenti nel sistema che vengono menzionati nel testo. Un utente, infine, può definire un insieme di categorie personalizzate e possibilmente gerarchiche, e associare ciascun riferimento a una o più categorie. Per organizzazione gerarchica delle categorie si intende la possibilità di specificare che una certa categoria (e.g.: “Informatica”) ha una o più sotto-categorie (e.g.: “Basi di Dati” o “Testing”). Non è possibile introdurre dipendenze cicliche, ovvero non è possibile che una categoria sia una sotto-categoria (anche transitivamente) di sé stessa. L’appartenenza a una sotto-categoria implica l’appartenenza a tutte le sue super-categorie. Non è pertanto possibile associare esplicitamente a un riferimento una categoria e una sua super-categoria. Il sistema permette infine di effettuare interrogazioni avanzate, con possibilità di filtraggio per una o più categorie, per data, per parole chiave e per autore. Inoltre, è possibile ordinare i riferimenti per numero di citazioni ricevute, ovvero per il numero di volte in cui il riferimento è presente nei rimandi di altri riferimenti.

## Progettazione del database
Di seguito verrà descritta la fase progettuale del database.
### Class Diagram
![Class Diagram](Pictures/class-diagram.png)

Facendo riferimento alla traccia ho stilato il presente diagramma delle classi. Ho individuato 2 entità fondamentali:
1. Riferimento: il rimando ad una data fonte che può essere:
	1. Un libro.
	2. Una risorsa in rete: sito web, blog, social network, ecc.
	3. Un articolo scientifico su conferenza (ancora non ho idea di cosa sia) o su rivista.
	4. Un dataset.
2. Categoria: una suddivisione di riferimenti che può essere definita dall'utente.
### Ristrutturazione

![ClassDiagramRistrutturato](Pictures/class-diagram-ristrutturato.png)

Per la ristrutturazione del diagramma ho dovuto:

1. Rimuovere le specializzazioni; ho preferito includere le classi specializzate nella generalizzazione. La motivazione di questa scelta è dovuta alla grande quantità di associazioni che andrebbero codificate se fossero mantenute separate le varie specializzazioni.
2. Rimuovere gli attributi a valore multiplo; sono stati sostituiti con delle nuove classi. 

### Schema delle relazioni

$$
\text{Citazione(}\underline{\text{ID}},\underline{\underline{\text{Menzionato}}},\underline{\underline{\text{Riferimento}}}\text{)}\\
\text{Riferimento(}\underline{\text{Titolo}}\text{, Autori, Data, Descrizione, ISBN, DOI, URL, ISNN)}\\
\text{Categoria(}\underline{\text{Nome}},\text{Descrizione)}\\
\text{Sottocategoria(}\underline{\text{Nome}}\text{, Descrizione},\underline{\underline{\text{Supercategoria}}})\\
\text{Bibliografia(}\underline{\underline{\text{Riferimento}}},\underline{\underline{\text{Categoria}}})
$$

### Script SQL per la definizione del DB

```sql

-- La tabella `riferimento` tiene traccia dei riferimenti. Ogni riferimento può essere: di un libro (ISBN), di una risorsa online (URL), di un dataset (DOI) o di un articolo scientifico (ISSN).

create table riferimento (
    id_riferimento serial primary key,
    titolo varchar(100),
    autori varchar(250),
    data_pub date,
    descrizione varchar(2000),
    isbn char(14) unique check (isbn ~* '^[0-9]{3}-[0-9]{10}$'),
    doi varchar unique,
    url varchar(300) unique,
    issn char(8) unique check (issn ~*'^ISSN [0-9]{4}-[0-9]{4}$'),
    -- Vincoli intrarelazionali
    -- il vincolo `discriminante` ha il compito di distinguere un riferimento da un altro (uno stesso riferimento non può essere di un libro e di un sito web) 
    constraint discriminante check( (isbn is not null and doi is null and url is null and issn is null) or (isbn is null and doi is not null and url is null and issn is null) or (isbn is null and doi is null and url is not null and issn is null) or (isbn is null and doi is null and url is null and issn is not null))
)

-- La tabella `citazione` codifica la relazione * a * riflessiva. L'attributo `citazione.riferimento` indica il riferimento citante mentre `citazione.menzionato` indica il riferimento citato. 

create table citazione(
    id_citazione serial primary key,
    menzionato varchar(100),
    riferimento varchar(100),
    -- Vincoli interrelazionali
    constraint riferito foreign key (menzionato) references Riferimento(titolo),
    constraint referente foreign key (riferimento) references Riferimento(titolo)
)

-- La tabella `categoria` contiene tutte le categorie create dall'utente

create table categoria (
    id_categoria serial primary key,
    nome varchar(100),
    descrizione varchar(2000)
)

-- La tabella `catalogo` codifica la relazione * a * tra `riferimento` e `categoria`. Consente di risalire alla categoria di ciascun riferimento, oppure di verificare quali riferimenti appartengono ad una data categoria.

create table catalogo (
    id_bibliografia serial primary key,
    riferimento varchar(100),
    categoria varchar(100),
    -- Vincoli interrelazionali
    constraint riferimento foreign key (riferimento) references Riferimento(titolo),
    constraint categoria foreign key (categoria) references Categoria(nome)
)

-- La tabella `sottocategoria` indica le possibili sottocategorie di una supercategoria. Poiché potenzialmente una supercategoria potrebbe avere più di una sottocategoria, è inclusa una chiave esterna `supercategoria` che consente di risalire a tutte le sottocategorie di una supercategoria.

create table sottocategoria (
    id_sottocategoria serial primary key,
    nome varchar(100),
    descrizione varchar(1000),
    supercategoria varchar(100),
    -- Vincoli interrelazionali
    constraint categoria_padre foreign key (supercategoria) references Categoria(id_categoria)
)
```

## Progettazione Software Java

### Class Diagram

![ClassDiagramJava](Pictures/class-diagram-java.png)
