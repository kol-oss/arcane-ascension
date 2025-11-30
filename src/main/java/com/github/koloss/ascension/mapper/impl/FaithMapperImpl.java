package com.github.koloss.ascension.mapper.impl;

import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FaithMapperImpl implements ModelMapper<Faith> {
    @Override
    public Faith toEntity(ResultSet rs) throws SQLException {
        return Faith.builder()
                .id(UUID.fromString(rs.getString(1)))
                .userId(UUID.fromString(rs.getString(2)))
                .aspect(DivineAspect.valueOf(rs.getString(3)))
                .level(new AtomicInteger(rs.getInt(4)))
                .count(new AtomicLong(rs.getLong(5)))
                .build();
    }

    @Override
    public void setInsertValues(PreparedStatement ps, Faith entity) throws SQLException {
        ps.setString(1, entity.getId().toString());
        ps.setString(2, entity.getUserId().toString());
        ps.setString(3, entity.getAspect().name());
        ps.setInt(4, entity.getLevel().get());
        ps.setLong(5, entity.getCount().get());
    }

    @Override
    public void setUpdateValues(PreparedStatement ps, Faith entity) throws SQLException {
        ps.setInt(1, entity.getLevel().get());
        ps.setLong(2, entity.getCount().get());
        ps.setString(3, entity.getId().toString());
    }
}
