package org.csu.mypetstore;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({Account.class,test.class})
@MapperScan("org.csu.mypetstore.persistence")
public class MypetstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypetstoreApplication.class, args);
    }

}