package az.et.unitech.exchange;

import az.et.unitech.common.util.LogUtil;
import az.et.unitech.exchange.client.ExchangeRateClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackageClasses = ExchangeRateClient.class)
@SpringBootApplication(scanBasePackages = "az.et.unitech")
@EnableScheduling
public class MsExchangeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsExchangeApplication.class);
        Environment env = app.run(args).getEnvironment();
        LogUtil.logApplicationStartup(env);
    }

}
