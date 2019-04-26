package com.ysc.graderank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描需要用到的mapper类
@MapperScan("com.ysc.graderank.mapper")
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"com.ysc.graderank", "org.n3r.idworker"})
public class GradeRankApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeRankApplication.class, args);
	}

}
