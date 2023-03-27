package baronvice.mvcstuff.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Not used right now so null is returned
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // What class is used as configuration
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // All users http requests are send to dispatcherServlet
        return new String[]{"/"};
    }
}
