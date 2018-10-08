package sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

public class sudoku_test {

	@Test
	public void test_checkRegion() {
		assertEquals(0, sudoku.checkRegion(0, 0));
	}
	
	@Test
	public void test_checkRegion2() {
		assertEquals(1, sudoku.checkRegion(1, 4));
	}
	
	@Test
	public void test_checkRegion3() {
		assertEquals(2, sudoku.checkRegion(2, 7));
	}
	
	@Test
	public void test_checkRegion4() {
		assertEquals(3, sudoku.checkRegion(3, 1));
	}
	
	@Test
	public void test_checkRegion5() {
		assertEquals(4, sudoku.checkRegion(4, 3));
	}
	
	@Test
	public void test_checkRegion6() {
		assertEquals(5, sudoku.checkRegion(5, 6));
	}
	
	@Test
	public void test_checkRegion7() {
		assertEquals(6, sudoku.checkRegion(6, 2));
	}
	
	@Test
	public void test_checkRegion8() {
		assertEquals(7, sudoku.checkRegion(7, 5));
	}
	
	@Test
	public void test_checkRegion9() {
		assertEquals(8, sudoku.checkRegion(8, 8));
	}
	
	@Test
	public void test_checkRegion10() {
		assertEquals(0, sudoku.checkRegion(0, 0));
	}

}
