package transitivedependencies;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransitiveDependenciesTest {
    
    public TransitiveDependenciesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of {@link TransitiveDependencies#get(java.lang.String)}.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        TransitiveDependencies td = new TransitiveDependencies();
        td.addDirect("A", Arrays.asList("B", "C"));
        td.addDirect("B", Arrays.asList("C", "E"));
        td.addDirect("C", Arrays.asList("G"));
        td.addDirect("D", Arrays.asList("A", "F"));
        td.addDirect("E", Arrays.asList("F"));
        td.addDirect("F", Arrays.asList("H"));
        td.dump(System.out);
        assertEquals(Arrays.asList("B", "C", "E", "F", "G", "H"), td.dependenciesFor("A"));
        assertEquals(Arrays.asList("C", "E", "F", "G", "H"), td.dependenciesFor("B"));
        assertEquals(Arrays.asList("G"), td.dependenciesFor("C"));
        assertEquals(Arrays.asList("A", "B", "C", "E", "F", "G", "H"), td.dependenciesFor("D"));
        assertEquals(Arrays.asList("F", "H"), td.dependenciesFor("E"));
        assertEquals(Arrays.asList("H"), td.dependenciesFor("F"));
    }
}
