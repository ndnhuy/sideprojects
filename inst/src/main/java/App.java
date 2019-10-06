import com.simpleinstagram.web.ApplicationContext;
import com.simpleinstagram.web.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.Registration;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws LifecycleException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(new File(".").getAbsolutePath());

        Host host = tomcat.getHost();
        Context context = new StandardContext();
        context.setName("");
        context.setDisplayName("application");
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.setAllowCasualMultipartParsing(true);
        context.addServletContainerInitializer((c, ctx) -> {
            Registration.Dynamic dynamic = ctx.addServlet("dispatcherServlet", new DispatcherServlet(new ApplicationContext()));
            ((ServletRegistration.Dynamic) dynamic).addMapping("/*");
        }, Collections.<Class<?>>emptySet());
        host.addChild(context);
        tomcat.start();

        // From org.springframework.boot.web.embedded.tomcat.TomcatWebServer#initialize():
        // Unlike Jetty, all Tomcat threads are daemon threads. We create a
        // blocking non-daemon to stop immediate shutdown
        Thread thread = new Thread("tomcat-container") {
            @Override
            public void run() {
                tomcat.getServer().await();
            }
        };
        // Set this thread as a non-daemon to prevent JVM from exiting
        thread.setDaemon(false);
        thread.start();
    }
}
