package club.dreamccc.manager.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
        ApplicationContextProvider.ctx = ctx;
    }

    /**
     * Tries to autowire the specified instance of the class if one of the specified
     * beans which need to be autowired are null.
     *
     * @param classToAutowire        the instance of the class which holds @Autowire
     *                               annotations
     * @param beansToAutowireInClass the beans which have the @Autowire annotation
     *                               in the specified {#classToAutowire}
     */
    public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
        for (Object bean : beansToAutowireInClass) {
            if (bean == null) {
                ctx.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
            }
        }
    }

}
