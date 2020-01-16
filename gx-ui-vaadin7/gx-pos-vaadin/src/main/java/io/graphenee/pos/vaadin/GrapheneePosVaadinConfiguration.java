package io.graphenee.pos.vaadin;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(com.vaadin.spring.VaadinConfiguration.class)
@ComponentScan("io.graphenee.pos.vaadin")
public class GrapheneePosVaadinConfiguration {

}
