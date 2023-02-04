package com.niliusjulius.lolinfo;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.fonimus,com.niliusjulius.lolinfo")
public class LolInfoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(LolInfoApplication.class).bannerMode(Banner.Mode.OFF).run(args);
	}

}
