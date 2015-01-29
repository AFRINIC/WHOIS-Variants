package tools.rdns;

import org.springframework.context.annotation.Scope;
import whois.core.api.Command;
import whois.core.api.CommandException;

import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;

/**
 * Created by yogesh on 1/28/15.
 */
@Named
@Scope("prototype")
public class RdnsCommand implements Command<String[], String> {

    private InitialDirContext dnsTool;

    private StringBuilder result = new StringBuilder();

    private String domain;

    private String nServerList;


    public void setParameter(String[] parameter) {
        if (parameter.length < 2) {
            throw new CommandException("The following parameters are expected: <IP address> <name server 1> ... <name server n>");
        }
        domain = parameter[0];
        nServerList = dnsUrl(parameter);
    }

    public String getResult() {
        return result.toString();
    }

    public void run() {
        initDnsTool();
        result.append("Domain: ").append(domain).append("\n");
        result.append("NServer(s): ").append(nServerList).append("\n");
        result.append("Hostname: ").append(getReverseName(domain)).append("\n");
    }

    /**
     * Perform a reverse DNS on an IP address
     *
     * @param ipAddr IP address
     * @return Hostname
     * @throws NamingException
     */
    private String getReverseName(String ipAddr) {
        String revName = null;
        Attributes attrs = null;
        try {
            attrs = dnsTool.getAttributes(reverse(ipAddr), new String[]{"PTR"});
        } catch (NamingException e) {
            throw new CommandException("ERROR performing reverse DNS on \"" + ipAddr + "\": " + e.toString());
        }
        Attribute attr = attrs.get("PTR");
        if (attr != null) {
            try {
                revName = (String) attr.get(0);
            } catch (NamingException e) {
                throw new CommandException("ERROR performing reverse DNS on \"" + ipAddr + "\": " + e.toString());
            }
        }
        return revName;
    }

    private void initDnsTool() {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        env.put(Context.PROVIDER_URL, nServerList);
        try {
            dnsTool = new InitialDirContext(env);
        } catch (NamingException e) {
            throw new CommandException("ERROR initializing DNS tool: " + e.toString());
        }
    }

    /**
     * Convert "W.X.Y.Z" to "Z.Y.X.W.in-addr.arpa.".
     *
     * @param ipAddr IP Address
     * @return Reverse format
     */
    private String reverse(String ipAddr) {
        StringBuilder query = new StringBuilder();
        String[] quads = ipAddr.split("\\.");
        for (int i = quads.length - 1; i >= 0; i--) {
            query.append(quads[i]).append(".");
        }
        query.append("in-addr.arpa.");
        return query.toString();
    }

    private String dnsUrl(String[] parameter) {
        StringBuilder nServerListSBuilder = new StringBuilder();
        for (int i = 1; i < parameter.length; i++) {
            if (i == 1) {
                nServerListSBuilder.append(dnsUrl(parameter[i]));
            } else {
                nServerListSBuilder.append(" ").append(dnsUrl(parameter[i]));
            }
        }
        return nServerListSBuilder.toString();
    }

    /**
     * Prepends "dns://"
     *
     * @return
     */
    private String dnsUrl(String nServer) {
        return "dns://" + nServer;
    }

}
