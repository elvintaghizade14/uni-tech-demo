package az.et.unitech.account;

import az.et.unitech.common.util.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "az.et.unitech")
public class MsAccountApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsAccountApplication.class);
        Environment env = app.run(args).getEnvironment();
        LogUtil.logApplicationStartup(env);
    }

}
