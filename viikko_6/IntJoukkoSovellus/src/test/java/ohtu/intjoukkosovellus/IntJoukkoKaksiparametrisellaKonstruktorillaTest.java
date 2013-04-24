
package ohtu.intjoukkosovellus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntJoukkoKaksiparametrisellaKonstruktorillaTest extends IntJoukkoTest {
    
    @Before
    public void setUp() {
        joukko = new IntJoukko(4, 2);
        joukko.lisaa(10);
        joukko.lisaa(3);
    }
    
    @Test
    public void tetaaEttaLiianPienellaKapasiteetillaLuontiEiOnnistu(){
        try{
            joukko = new IntJoukko(-1, 2);
        } catch(IndexOutOfBoundsException e){
            return;
        }
        fail("IntJoukon luonti onnistui negatiivisella kapasiteetilla");
    }
    @Test
    public void testaaEttaLiianPienellaKasvullaLuontiEiOnnistu(){
        try{
            joukko = new IntJoukko(1, -1);
        } catch(IndexOutOfBoundsException e){
            return;
        }
        fail("IntJoukon luonti onnistui negatiivisella kasvulla");
    }
}
