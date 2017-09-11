import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {
  
	@Test
	public void test() {
		//Assert.assertEquals(true, true);
		System.out.println("maven test");
		Assert.assertEquals(5, 5);
	}
}
