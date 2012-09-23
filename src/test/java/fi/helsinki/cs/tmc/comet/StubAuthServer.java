package fi.helsinki.cs.tmc.comet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.LoggerFactory;

public class StubAuthServer {
    private final int port;
    private final Set<StubUser> users;
    private Server server;
    
    private static class StubUser {
        public final String username;
        public final String password;
        public StubUser(String username, String password) {
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            this.username = username;
            this.password = password;
        }

        @Override
        public int hashCode() {
            return username.hashCode() + password.hashCode() << 16;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StubUser) {
                StubUser that = (StubUser)obj;
                return this.username.equals(that.username) && this.password.equals(that.password);
            } else {
                return false;
            }
        }
    }

    public StubAuthServer(int port) throws Exception {
        this.port = port;
        this.users = new HashSet<StubUser>();
        this.server = new Server(port);
        server.setHandler(handler);
        server.start();
    }
    
    public void addUser(String username, String password) {
        synchronized (users) {
            users.add(new StubUser(username, password));
        }
    }
    
    private Handler handler = new AbstractHandler() {
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            String pathInfo = baseRequest.getPathInfo();
            if (baseRequest.getPathInfo() != null && pathInfo.equals("/foo/auth.text")) {
                response.setContentType("text/plain; charset=utf-8");
                PrintWriter w = response.getWriter();
                boolean userOk;
                synchronized (users) {
                    userOk = users.contains(new StubUser(request.getParameter("username"), request.getParameter("password")));
                }
                if (userOk) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    w.println("OK");
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    w.println("FAIL");
                }
                w.close();
                baseRequest.setHandled(true);
            }
        }
    };

    public int getPort() {
        return port;
    }
    
    public void close() {
        try {
            server.stop();
            server.join();
        } catch (Exception ex) {
            LoggerFactory.getLogger(StubAuthServer.class).error("While shutting down StubAuthServer: " + ex.getMessage());
        }
    }
}
