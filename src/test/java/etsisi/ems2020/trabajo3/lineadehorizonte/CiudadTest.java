package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import junit.framework.TestCase;

public class CiudadTest extends TestCase{

	public CiudadTest(String sTestName) {
		super(sTestName);
	}

	@Before
	public void setUp() throws Exception {		
		
	}

	@Test
	public void testGetLineaHorizonte1() {
		
		LineaHorizonte linea;
		Ciudad c;
		
		try {			
			
			c = new Ciudad();
			Edificio e1 = new Edificio(Arrays.asList(1,3,4));
			c.addEdificio(e1);
			Edificio e2 = new Edificio(Arrays.asList(2,9,7));
			c.addEdificio(e2);
			Edificio e3 = new Edificio(Arrays.asList(4,12,4));
			c.addEdificio(e3);	
			Edificio e4 = new Edificio(Arrays.asList(6,8,9));
			c.addEdificio(e4);
			Edificio e5 = new Edificio(Arrays.asList(11,13,6));
			c.addEdificio(e5);
			Edificio e6 = new Edificio(Arrays.asList(14,15,2));
			c.addEdificio(e6);		

			
			linea = c.getLineaHorizonte();			
			assertTrue(linea.getPunto(0).getX()== 1  && linea.getPunto(0).getY()==4);
			assertTrue(linea.getPunto(1).getX()== 2  && linea.getPunto(1).getY()==7);
			assertTrue(linea.getPunto(2).getX()== 6  && linea.getPunto(2).getY()==9);
			assertTrue(linea.getPunto(3).getX()== 8  && linea.getPunto(3).getY()==7);   
			assertTrue(linea.getPunto(4).getX()== 9  && linea.getPunto(4).getY()==4);
			assertTrue(linea.getPunto(5).getX()== 11 && linea.getPunto(5).getY()==6);
			assertTrue(linea.getPunto(6).getX()== 13 && linea.getPunto(6).getY()==0);
			assertTrue(linea.getPunto(7).getX()== 14  && linea.getPunto(7).getY()==2);
			assertTrue(linea.getPunto(8).getX()== 15  && linea.getPunto(8).getY()==0);
			
		} catch (Exception e) {			
			fail("Test failed");
		}
	}
	
	@Test
	public void testGetLineaHorizonte2() {
		LineaHorizonte linea;
		Ciudad c;
		
		try {			
			
			c = new Ciudad();
			Edificio e1 = new Edificio(Arrays.asList(3,6,5));
			c.addEdificio(e1);
			Edificio e2 = new Edificio(Arrays.asList(4,9,3));
			c.addEdificio(e2);
			
			linea = c.getLineaHorizonte();			
			assertTrue(linea.getPunto(0).getX()== 3  && linea.getPunto(0).getY()==5);
			assertTrue(linea.getPunto(1).getX()== 6  && linea.getPunto(1).getY()==3);
			assertTrue(linea.getPunto(2).getX()== 9  && linea.getPunto(2).getY()==0);			
			
		} catch (Exception e) {			
			fail("Test failed");
		}
	}


	@After
	public void tearDown() throws Exception {
	}
	
	public static void main(String args[]) {
		Result result = JUnitCore.runClasses(CiudadTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

	}


}
