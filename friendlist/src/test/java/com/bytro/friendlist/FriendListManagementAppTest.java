package com.bytro.friendlist;

import com.bytro.friendlist.utils.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(Constant.TEST_PROFILE)
@EnableJpaRepositories(basePackages = "com.bytro.friendlist.repository")
@EntityScan("com.bytro.friendlist.entity")
 class FriendListManagementAppTest {

    @Test
    void contextLoads() {}
}
