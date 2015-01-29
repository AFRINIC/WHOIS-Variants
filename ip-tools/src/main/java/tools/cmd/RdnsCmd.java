package tools.cmd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tools.rdns.RdnsCommand;
import whois.core.api.Command;
import whois.core.api.CommandFactory;

/**
 * Created by yogesh on 1/28/15.
 */
public class RdnsCmd {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/ip-tools-context.xml");
        CommandFactory commandFactory = applicationContext.getBean(CommandFactory.class);
        Command command = commandFactory.getCommand(RdnsCommand.class);

        command.setParameter(args);
        command.run();
        System.out.println(command.getResult());
    }
}
