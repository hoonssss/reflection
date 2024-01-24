package org.example;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Controller 애노테이션이 설정되어 있는 모든 클래스를 찾아서 출력
 */
public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

        logger.debug("beans : [{}]", beans);
    }

    private static Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");//패키지

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));
//        beans.addAll(reflection.getTypesAnnotatedWith(Controller.class));//Controller 대상을 찾아서 HashSet에 담음
//        beans.addAll(reflection.getTypesAnnotatedWith(Service.class));
        return beans;
    }
}
