package cn.yznu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kaori
 */
@RestController
@SpringBootApplication
//@EnableScheduling
//@ComponentScan(basePackages = {"cn.yznu.modules.sys.controller.*","cn.yznu.modules.sys.service.*"})
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
