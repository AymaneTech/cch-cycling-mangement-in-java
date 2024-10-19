package com.wora.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class DatabaseProperties {
    private final String url;

    private final String username;

    private final String password;

    private final String hibernateDdlAuto;

    private final Boolean hibernateShowSql;

    private final Boolean hibernateFormatSql;

    public DatabaseProperties(
            @Value("${app.database.url}") String url,
            @Value("${app.database.username}") String username,
            @Value("${app.database.password}") String password,
            @Value("${app.database.hibernate.ddl-auto}") String hibernateDdlAuto,
            @Value("${app.database.hibernate.show-sql}") Boolean hibernateShowSql,
            @Value("${app.database.hibernate.format-sql}") Boolean hibernateFormatSql
    ) {

        this.url = url;
        this.username = username;
        this.password = password;
        this.hibernateDdlAuto = hibernateDdlAuto;
        this.hibernateShowSql = hibernateShowSql;
        this.hibernateFormatSql = hibernateFormatSql;
    }

    public String url() {
        return url;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String hibernateDdlAuto() {
        return hibernateDdlAuto;
    }

    public Boolean hibernateShowSql() {
        return hibernateShowSql;
    }

    public Boolean hibernateFormatSql() {
        return hibernateFormatSql;
    }
}
