package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
        // private Constructor
        // 외부에서의 생성을 막음
    }

    public static void main(String[] args) {

    }

    public void logic() {
        System.out.println("call singleton obj");
    }
}
