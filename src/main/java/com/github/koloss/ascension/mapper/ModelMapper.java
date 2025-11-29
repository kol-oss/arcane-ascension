package com.github.koloss.ascension.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelMapper<T> {
    T toEntity(ResultSet rs) throws SQLException;

    void setPreparedValues(PreparedStatement ps, T entity) throws SQLException;
}
