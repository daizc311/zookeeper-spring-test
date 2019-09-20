package club.dreamccc.manager.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <h2>ApplicationContextHolder</h2>
 * <p>ApplicationContext保持器</p>
 *
 * @author Daizc
 * @date 2019/9/17
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

}
