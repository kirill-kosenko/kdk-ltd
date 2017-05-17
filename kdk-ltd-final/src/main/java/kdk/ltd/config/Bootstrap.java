package kdk.ltd.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import kdk.ltd.site.web.rest.SimpleCorsFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;



public class Bootstrap implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.getServletRegistration("default").addMapping("/resources/*");

        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext servletContext =
                new AnnotationConfigWebApplicationContext();
        servletContext.register(WebServletContextConfiguration.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet(
                "springDispatcher", new DispatcherServlet(servletContext)
        );
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        AnnotationConfigWebApplicationContext restContext =
                new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfiguration.class);
        DispatcherServlet restServlet = new DispatcherServlet(restContext);
        restServlet.setDispatchOptionsRequest(true);
        dispatcher = container.addServlet("springRestDispatcher", restServlet);
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/api/*");

        CharacterEncodingFilter characterEncodingFilter =
                new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("utf-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic registration = container.addFilter(
                "characterEncodingFilter", characterEncodingFilter
        );
        registration.addMappingForUrlPatterns(null, false, "/*");
        container.addFilter("simpleCorsFilter", new SimpleCorsFilter()).addMappingForUrlPatterns(null, false, "/api/*");
    }
}
