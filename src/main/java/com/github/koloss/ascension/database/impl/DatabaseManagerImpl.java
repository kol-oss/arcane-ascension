package com.github.koloss.ascension.database.impl;

import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.database.DatabaseManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.logging.log4j.util.Strings;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerImpl implements DatabaseManager {
    public static final String CACHE_STATEMENTS = "true";
    public static final String STATEMENT_CACHE_SIZE = "250";
    public static final String STATEMENT_CACHE_SQL_LIMIT = "2048";
    private static final int MIN_IDLE = 2;

    private final HikariDataSource dataSource;
    private final String changelogPath;

    public DatabaseManagerImpl(DatabaseConfiguration config) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(config.getConnectionString());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());

        hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        hikariConfig.setMinimumIdle(MIN_IDLE);
        hikariConfig.setDriverClassName(config.getDriverClassName());

        hikariConfig.addDataSourceProperty("cachePrepStmts", CACHE_STATEMENTS);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", STATEMENT_CACHE_SIZE);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", STATEMENT_CACHE_SQL_LIMIT);

        this.dataSource = new HikariDataSource(hikariConfig);
        this.changelogPath = config.getChangelogPath();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void migrate(ClassLoader classLoader) {
        Thread.currentThread().setContextClassLoader(classLoader);

        try (Connection connection = getConnection()) {
            Database database = DatabaseFactory
                    .getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogPath, new ClassLoaderResourceAccessor(classLoader), database);
            liquibase.update(Strings.EMPTY);
        } catch (Exception ex) {
            throw new RuntimeException("Can not migrate database because exception occurred: " + ex.getMessage(), ex);
        }
    }

    public void stop() {
        dataSource.close();
    }
}
