package org.pmu.global.config;

import org.pmu.PmuApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = PmuApplication.class)
@Configuration
public class FeignClientConfig {
}
