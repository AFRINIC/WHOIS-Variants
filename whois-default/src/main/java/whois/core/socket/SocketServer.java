package whois.core.socket;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by yogesh on 12/22/14.
 */
class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static final int QUERY_PORT = 4443;

    private static final int UPDATE_PORT = 4444;

    private static final int SOCKET_READ_BUFFER_SIZE = 2048;

    private static final String CHARSET = "UTF-8";

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/whois-core-context.xml");

        bind(applicationContext.getBean(QuerySocketEventListener.class), QUERY_PORT);
        bind(applicationContext.getBean(UpdateSocketEventListener.class), UPDATE_PORT);

        logger.info("STARTED");
    }

    private static IoAcceptor bind(IoHandler ioHandler, int port) throws IOException {
        IoAcceptor queryAcceptor = new NioSocketAcceptor();
        queryAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
        queryAcceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(CHARSET))));
        queryAcceptor.setHandler(ioHandler);
        queryAcceptor.getSessionConfig().setReadBufferSize(SOCKET_READ_BUFFER_SIZE);
        queryAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        queryAcceptor.bind(new InetSocketAddress(port));

        logger.info(ioHandler + " is listening on port " + port);

        return queryAcceptor;
    }
}
