package eternal.hoge.spring.boot.example.simple.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    private ApplicationContextProvider() {
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        setAppContext(ctx);
    }

    public static <T> T getBean(String name, Class<T> aClass) {
        return context.getBean(name, aClass);
    }

    public static <T> T getBean(Class<T> aClass) {
        return context.getBean(aClass);
    }

    public static synchronized void setAppContext(ApplicationContext ctx) {
        context = ctx;
    }
}
