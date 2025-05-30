package org.digitalLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy

public class digitalLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(digitalLibraryApplication.class, args);
    }
}

