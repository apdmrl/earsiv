package com.iris.earsiv;

import com.iris.earsiv.model.testmodels.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EarsivApplicationTests {

    @Autowired
    private firm1Repository firm1Repository;
    @Autowired
    private firm2Repository firm2Repository;
    @Autowired
    private testClassRepository testClassRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testDbreff() {
        firm1 firm1 = new firm1().setId("567");
        firm2 firm2 = new firm2().setId("890");
        firm1Repository.save(firm1);
        firm2Repository.save(firm2);
        testClassRepository.save(new testClass().setId("1").setFirm1(firm1).setFirm2(firm2).setName("test2"));
    }
}
