package corona;

import junit.framework.TestCase;
import org.junit.Test;

public class CoronaVirusTest extends TestCase {

    @Test
    public void testMethodNaive1() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "1", "Fr");
        coronaVirus.methodNaive();
        assertEquals("France, 0, 10", coronaVirus.getOutput());
    }

    @Test
    public void testMethodNaive2() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "2", "FrIt");
        coronaVirus.methodNaive();
        assertEquals("France, 0, 20", coronaVirus.getOutput());
    }

    @Test
    public void testMethodNaive3() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "3", "FrItSp");
        coronaVirus.methodNaive();
        assertEquals("Italy, 0, 18", coronaVirus.getOutput());
    }

    @Test
    public void testMethodNaive5() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "5", "Fr");
        coronaVirus.methodNaive();
        assertEquals("France, 0, 18, France, 3, 10, France, 2, 4", coronaVirus.getOutput());
    }

    @Test
    public void testMethodNaive6() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "6", "FrItSp");
        coronaVirus.methodNaive();
        assertEquals("Spain, 4, 10, Italy, 5, 10", coronaVirus.getOutput());
    }

    @Test
    public void testMethodNaive10() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "10", "FrIt");
        coronaVirus.methodNaive();
        assertEquals("Italy, 9, 10, Italy, 6, 4", coronaVirus.getOutput());
    }

    @Test
    public void testMethodNaive12() {
        CoronaVirus coronaVirus = new CoronaVirus("test", "12", "FrItSp");
        coronaVirus.methodNaive();
        assertEquals("Spain, 10, 10, France, 11, 10, Italy, 9, 4", coronaVirus.getOutput());
    }

//    public void testMethodMultiThread() {
//    }
}