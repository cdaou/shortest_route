package org.Centralized_Dijkstra.run;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Launch {

	public static void main(String[] args) throws  LifecycleException, ServletException, IOException {

		String contextPath = "/";
        String webappDir = new File("WebContent").getAbsolutePath();
        
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setHostname("localhost");
        tomcat.setPort(8080);
         
        tomcat.addWebapp(contextPath, new File(
                webappDir).getAbsolutePath());
                
        tomcat.start();
        tomcat.getServer().await();


	}

}
