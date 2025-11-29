package com.github.koloss.ascension.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManager {
    Connection getConnection() throws SQLException;

    void migrate(ClassLoader classLoader);

    void stop();
}
