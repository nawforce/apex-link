public class InnerServiceImpl{
    public class InnerImpl implements Service.API {
        void func() {}

        void foo() {
            Hello h = new Hello();
        }
    }

    public void bar() {
        NoTest n = new NoTest();
    }
}
