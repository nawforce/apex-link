@IsTest
public class BaseTest {

    class TestImpl extends Base {
        public void func() {}
    }

    void testFunc() {
        TestImpl sut = new TestImpl();

    }
}