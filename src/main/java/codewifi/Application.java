package codewifi;



import codewifi.utils.LogUtil;
import jodd.introspector.Mapper;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableCaching
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@AllArgsConstructor
@MapperScan(annotationClass = Mapper.class)

public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        final LogUtil logUtil = LogUtil.getLogger(Application.class);
        try {
            SpringApplication.run(Application.class, args);
        }
        catch (Throwable e) {
            logUtil.error("启动异常", e,null);
        }
    }

    @Override
    public void run(String... args) throws InterruptedException {

    }
}
