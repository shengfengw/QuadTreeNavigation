import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class QuadTreeViewerTest {
	@Test
	public void generalTest() {
		QuadTreeViewer qtv = new QuadTreeViewer(100, 100, 60);
		assertTrue(qtv.getPath("1", "2").size() != 0);

	}
}
