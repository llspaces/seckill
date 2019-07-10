package com.llspace.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * SeckillApplication 系统启动类
 *
 * @author llspace
 * @since  2019/6/17 11:57
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.llspace.seckill.dao"})
public class SeckillApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SeckillApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SeckillApplication.class, args);
	}

}
