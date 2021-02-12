package com.wimdeblauwe.examples.junit5testorder;

import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;

public class SpringBootTestClassOrderer implements ClassOrderer {
    @Override
    public void orderClasses(ClassOrdererContext classOrdererContext) {
        classOrdererContext.getClassDescriptors().sort(Comparator.comparingInt(SpringBootTestClassOrderer::getOrder));
    }

    private static int getOrder(ClassDescriptor classDescriptor) {
        if (classDescriptor.findAnnotation(SpringBootTest.class).isPresent()) {
            return 4;
        } else if (classDescriptor.findAnnotation(WebMvcTest.class).isPresent()) {
            return 3;
        } else if (classDescriptor.findAnnotation(DataJpaTest.class).isPresent()) {
            return 2;
        } else {
            return 1;
        }
    }
}
