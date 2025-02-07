package com.sawwere.sber.homework18.crud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class MigrationsTest extends BasicTestContainerTest{
    @Test
    void migrate() {

    }
}
