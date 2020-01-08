package com.github.xl;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 只会扫描注解类所处包下的组件
 *
 * @SpringBootApplication same as @Configuration @EnableAutoConfiguration @ComponentScan
 */
@SpringBootApplication(scanBasePackages = {"com.github.xl"})
public class Application {

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        System.out.println(context.getBean(Cat.class).getAge());
//        new SpringApplicationBuilder()
//                .sources(Application.class)
//                .bannerMode(Banner.Mode.OFF)
//                .run(args);

        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setRegisterShutdownHook(true);
        springApplication.run(args);
    }
}
