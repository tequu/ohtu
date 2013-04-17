
import ohtu.verkkokauppa.Kauppa;
import ohtu.verkkokauppa.Pankki;
import ohtu.verkkokauppa.Tuote;
import ohtu.verkkokauppa.Varasto;
import ohtu.verkkokauppa.Viitegeneraattori;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class VerkkokauppaTest {

    @Before
    public void setUp() {
    }

    @Test
    public void ostoksenPaaytyttyapankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
    }

    @Test
    public void testaaEttaKutsutaanTilisiirtoaOikeillaTiedoillaYhdellaTuotteella() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1))
                .thenReturn(1);
        when(varasto.haeTuote(1))
                .thenReturn(new Tuote(1, "testi", 2));
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(eq("testi1"), anyInt(), eq("123"), anyString(), eq(2));
    }

    @Test
    public void testaaEttaKutsutaanTilisiirtoaOikeillaTiedoillaKahdellaEriTuotteella() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(anyInt()))
                .thenReturn(1)
                .thenReturn(1);
        when(varasto.haeTuote(1))
                .thenReturn(new Tuote(1, "testi", 2));
        when(varasto.haeTuote(2))
                .thenReturn(new Tuote(2, "testi2", 3));
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(eq("testi1"), anyInt(), eq("123"), anyString(), eq(5));
    }

    @Test
    public void testaaEttaKutsutaanTilisiirtoaOikeillaTiedoillaKahdellaSamallaTuotteella() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        Tuote tuote = new Tuote(1, "testi", 2);
        when(varasto.saldo(anyInt()))
                .thenReturn(1)
                .thenReturn(1);
        when(varasto.haeTuote(1))
                .thenReturn(tuote)
                .thenReturn(tuote);
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(eq("testi1"), anyInt(), eq("123"), anyString(), eq(4));
    }

    @Test
    public void testaaEttaKutsutaanTilisiirtoaOikeillaTiedoillaYhdellaVarastossaOlevallaJaYhdellaLoppuneellaTuotteella() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        Tuote tuote = new Tuote(1, "testi", 2);
        when(varasto.saldo(anyInt()))
                .thenReturn(1)
                .thenReturn(0);
        when(varasto.haeTuote(1))
                .thenReturn(tuote);
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(eq("testi1"), anyInt(), eq("123"), anyString(), eq(2));
    }

    @Test
    public void testaaEttaAloitaAsioitiNollaaAiemmatTiedot() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        Tuote tuote = new Tuote(1, "testi", 2);
        when(varasto.saldo(anyInt()))
                .thenReturn(1)
                .thenReturn(0);
        when(varasto.haeTuote(1))
                .thenReturn(tuote);
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(eq("testi1"), anyInt(), eq("123"), anyString(), eq(2));
        kauppa.aloitaAsiointi();

        kauppa.tilimaksu("testi", "123");
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(0));
    }

    @Test
    public void testaaEttaKauppaPyytaaUudenViitenumeronUudelleMaksuTapahtumalle() {
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        when(viitegeneraattori.uusi())
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3);
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(anyString(), eq(1), anyString(), anyString(), anyInt());

        kauppa.tilimaksu("testi", "123");
        verify(pankki).tilisiirto(anyString(), eq(2), anyString(), anyString(), anyInt());
        
        kauppa.tilimaksu("testi", "123");
        verify(pankki).tilisiirto(anyString(), eq(3), anyString(), anyString(), anyInt());
    }
    
    @Test
    public void testaaEttaPoistettuaTuotettaEiLasketaLoppuSummaan(){
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viitegeneraattori = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        Tuote tuote = new Tuote(1, "testi", 2);
        when(varasto.saldo(anyInt()))
                .thenReturn(1);
        when(varasto.haeTuote(1))
                .thenReturn(tuote);
        Kauppa kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("testi1", "123");
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(0));
    }
}