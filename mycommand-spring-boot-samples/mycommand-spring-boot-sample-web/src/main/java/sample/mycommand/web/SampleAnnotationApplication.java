package sample.mycommand.web;

import com.hippocp.mycommand.spring.annotation.CommandMapperScan;
import com.hippocp.mycommand.spring.annotation.CommandMapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhouYifan
 * @date 2022/3/15
 */
@SpringBootApplication
@CommandMapperScans(@CommandMapperScan("sample.mycommand.web.mapper"))
public class SampleAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleAnnotationApplication.class, args);
    }

}
