package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Controller, Service")
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

        logger.debug("beans : [{}]", beans);
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class; //힙 영역에 로드되어 있는 클래스 타입의 객체를 가져옴
        logger.debug(clazz.getName());

        logger.debug("User All declared fields: [{}]",
            Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User All declared constructors: [{}]", //class
            Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList())); //선언 되어 있는 모든 생성자
        logger.debug("User All declared method: [{}]",
            Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList())); //method
    }

    @Test
    @DisplayName("힙 영역에 로드되어 있는 클래스타입 가져오는 방법")
    void load() throws ClassNotFoundException {
        //1
        Class<User> clazz = User.class;

        //2
        User user = new User("gdgd","gdgd");
        Class<? extends User> clazz2 = user.getClass();

        //3
        Class<?> clazz3 = Class.forName("org.example.model.User");

        logger.debug("class : [{}]" , clazz);
        logger.debug("class : [{}]" , clazz2);
        logger.debug("class : [{}]" , clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz3 == clazz).isTrue();
    }

    private static Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");//패키지

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(
            annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));
//        beans.addAll(reflection.getTypesAnnotatedWith(Controller.class));//Controller 대상을 찾아서 HashSet에 담음
//        beans.addAll(reflection.getTypesAnnotatedWith(Service.class));
        return beans;
    }
}
