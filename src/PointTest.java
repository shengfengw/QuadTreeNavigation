import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

	@Test
	public void testPointDoubleDoubleT() {
		Point<String> point = new Point<String>(1.0, 2.0, "test");
		assertNotNull(point);
	}

	@Test
	public void testPointDoubleDouble() {
		Point<String> point = new Point<String>(1.0, 2.0);
		assertNotNull(point);
	}

	@Test
	public void testEqualsObject() {
		Point<String> point = new Point<String>(1.0, 2.0);
		Point<String> point1 = new Point<String>(1.0, 2.0);
		assertEquals(point, point1);
		Point<String> point3 = new Point<String>(1.0, 3.0);
		Point<String> point4 = new Point<String>(2.0, 2.0);
		assertFalse(point.equals(point3));
		assertFalse(point.equals(point4));
		
	}

	@Test
	public void testGetX() {
		Point<String> point = new Point<String>(1.0, 2.0);
		assertEquals(1.0, point.getX(), 0.01);
	}

	@Test
	public void testSetX() {
		Point<String> point = new Point<String>(1.0, 2.0);
		point.setX(2.0);
	}

	@Test
	public void testGetY() {
		Point<String> point = new Point<String>(1.0, 2.0);
		assertEquals(2.0, point.getY(), 0.01);
	}

	@Test
	public void testSetY() {
		Point<String> point = new Point<String>(1.0, 2.0);
		point.setY(3.0);
	}

	@Test
	public void testGetValue() {
		Point<String> point = new Point<String>(1.0, 2.0, "test");
		assertEquals("test", point.getValue());
	}

	@Test
	public void testSetValue() {
		Point<String> point = new Point<String>(1.0, 2.0, "test");
		point.setValue("new test");
	}

	@Test
	public void testGetParent() {
		Point<String> point = new Point<String>(1.0, 2.0, "test");
		point.getParent();
	}

	@Test
	public void testSetParent() {
		Point<String> point = new Point<String>(1.0, 2.0);
		Point<String> point1 = new Point<String>(1.0, 2.0);
		point.setParent(point1);
	}

	@Test
	public void testGetRank() {
		Point<String> point = new Point<String>(1.0, 2.0);
		assertEquals(0, point.getRank());
	}

	@Test
	public void testSetRank() {
		Point<String> point = new Point<String>(1.0, 2.0);
		point.setRank(1);
		assertEquals(1, point.getRank());
	}

}
