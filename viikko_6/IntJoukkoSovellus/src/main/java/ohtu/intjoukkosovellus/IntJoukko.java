package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");//heitin vaan jotain :D
        }
        alusta(kapasiteetti);
        this.kasvatuskoko = kasvatuskoko;
    }

    private void alusta(int kapasiteetti) {
        ljono = new int[kapasiteetti];
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku)) {
            return false;
        }
        if (alkioidenLkm == ljono.length) {
            kasvataKokoa();
        }
        ljono[alkioidenLkm++] = luku;
        return true;
    }

    private void kasvataKokoa() {
        int[] uusi = new int[ljono.length + kasvatuskoko];
        System.arraycopy(ljono, 0, uusi, 0, ljono.length);
        ljono = uusi;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (ljono[i] == luku) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (ljono[i] == luku) {
                siirraTaaksePain(i);
                alkioidenLkm--;
                return true;
            }
        }
        return false;
    }

    private void siirraTaaksePain(int alkaen) {
        for (; alkaen < alkioidenLkm - 1; alkaen++) {
            ljono[alkaen] = ljono[alkaen + 1];
        }
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String tulostus = "{";
        for (int i = 0; i < alkioidenLkm; i++) {
            tulostus += ljono[i] + (i == alkioidenLkm - 1 ? "" : ", ");
        }
        return tulostus + "}";
    }

    public int[] toIntArray() {
        int[] uusi = new int[alkioidenLkm];
        System.arraycopy(ljono, 0, uusi, 0, alkioidenLkm);
        return uusi;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko(a.mahtavuus() + b.mahtavuus());
        lisaaAlkiot(a, joukko);
        lisaaAlkiot(b, joukko);
        return joukko;
    }

    private static void lisaaAlkiot(IntJoukko mista, IntJoukko mihin) {
        for (int i : mista.toIntArray()) {
            mihin.lisaa(i);
        }
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        for (int i : a.toIntArray()) {
            if (b.kuuluu(i)) {
                joukko.lisaa(i);
            }
        }
        return joukko;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        for (int i : a.toIntArray()) {
            if (!b.kuuluu(i)) {
                joukko.lisaa(i);
            }
        }
        return joukko;
    }
}