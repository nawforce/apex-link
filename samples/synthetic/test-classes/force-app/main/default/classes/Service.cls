public class Service {
    public interface API {
        void func();
    }

    static API getInstance() {
        (API)type.forName('name');
    }
}
