package com.tzwjkl.bowl.autoindex;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.extension.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AutoindexApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoindexApplication.class, args);
    }

    // --- Spring MVC configuration ---
    // set priority for resource registry (for avoid conflict between static mapping and wildcard mapping)
    @Bean
    WebMvcConfigurer createWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
            }
        };
    }

    // --- Pebble configuration ---
    // Inject filters
    @Bean
    Extension createExtension(Filter[] filters) {
        return new AbstractExtension() {
            @Override
            public Map<String, Filter> getFilters() {
                Map<String, Filter> map = new HashMap<>();
                for (Filter filter : filters) {
                    map.put(filter
                            .getClass()
                            .getSimpleName()
                            .toLowerCase()
                            .replace("filter", ""), filter);
                }
                return map;
            }
        };
    }

}
