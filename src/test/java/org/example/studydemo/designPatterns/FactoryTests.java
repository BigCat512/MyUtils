package org.example.studydemo.designPatterns;

import org.example.designPatterns.factory.Computer;
import org.example.designPatterns.factory.Middle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FactoryTests {

    @Test
    void demo01() {
        Integer type = 1;
        Computer suggest = new Middle().suggest(type);
        System.out.println(suggest.descrise());
    }

}
