package baronvice.mvcstuff.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("baronvice.mvcstuff")
// To enable web functions
@EnableWebMvc
/* WebMvcConfigurer is implemented when we want to configure SpringMVC for our needs.
  Instead of using default template we may take thymeleaf template and set it in configureViewResolver */
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    // Spring will inject context by itself
    @Autowired
    public SpringConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    // In templateResolver we configure thymeleaf using applicationContext
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setApplicationContext(applicationContext);
        // Root of views
        springResourceTemplateResolver.setPrefix("/WEB-INF/views/");
        // Their extension
        springResourceTemplateResolver.setSuffix(".html");
        return springResourceTemplateResolver;
    }

    // Also views configuration
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());
        springTemplateEngine.setEnableSpringELCompiler(true);
        return springTemplateEngine;
    }

    // And here Spring is notified that we want to use thymeleaf template
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        registry.viewResolver(thymeleafViewResolver);
    }
}
