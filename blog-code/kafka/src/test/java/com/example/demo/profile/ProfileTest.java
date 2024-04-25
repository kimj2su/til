package com.example.demo.profile;

import com.example.demo.ProfileConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProfileTest {

    @Test
    void profileTest() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(ProfileConfig.class)
                .withInitializer(context -> context.getEnvironment().setActiveProfiles("dev"))
                .run(context -> {
                    assertThat(context).hasSingleBean(String.class);
                    assertThat(context.getBean(String.class)).isEqualTo("dev");
                    assertThat(context).doesNotHaveBean("local");
                });
    }
}
