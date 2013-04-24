
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JoukkoOperaatiotTest {
    
    
    @Test
    public void testSomething() {
        IntJoukko eka = teeJoukko(1,2);
        IntJoukko toka = teeJoukko(3,4);
        
        IntJoukko tulos = IntJoukko.yhdiste(eka, toka);
        int[] vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu = {1,2,3,4};
        
        assertArrayEquals(odotettu, vastauksenLuvut);        
    } 

    @Test
    public void testaaLeikkaus(){
        IntJoukko eka = teeJoukko(1,2,5);
        IntJoukko toka = teeJoukko(3,4,5);
        
        IntJoukko tulos = IntJoukko.leikkaus(eka, toka);
        int[] luvut = tulos.toIntArray();
        Arrays.sort(luvut);
        
        int[] odotettu = {5};
        
        assertArrayEquals(odotettu, luvut);
    }
    
    @Test
    public void testaaErotus(){
        IntJoukko eka = teeJoukko(1,2,3,4,5);
        IntJoukko toka = teeJoukko(1,3,5);
        
        IntJoukko tulos = IntJoukko.erotus(eka, toka);
        int[] luvut = tulos.toIntArray();
        Arrays.sort(luvut);
        
        int[] odotettu = {2,4};
        
        assertArrayEquals(odotettu, luvut);
    }
    private IntJoukko teeJoukko(int... luvut) {
        IntJoukko joukko = new IntJoukko();
        
        for (int luku : luvut) {
            joukko.lisaa(luku);
        }
        
        return joukko;
    }
    
    
}
