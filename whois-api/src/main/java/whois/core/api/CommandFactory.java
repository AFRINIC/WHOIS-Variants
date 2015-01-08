package whois.core.api;

import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yogesh on 1/6/15.
 */
@Named
public class CommandFactory {
    @Inject
    private ApplicationContext applicationContext;

    public Command getCommand(Class clazz) {
        if (Command.class.isAssignableFrom(clazz)) {
            return (Command) applicationContext.getBean(clazz);
        }
        return null;
    }
}
