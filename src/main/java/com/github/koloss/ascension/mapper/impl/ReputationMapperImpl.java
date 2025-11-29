package com.github.koloss.ascension.mapper.impl;

import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.DivineBranch;
import com.github.koloss.ascension.model.Reputation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReputationMapperImpl implements ModelMapper<Reputation> {
    @Override
    public Reputation toEntity(ResultSet rs) throws SQLException {
        return Reputation.builder()
                .id(UUID.fromString(rs.getString(1)))
                .userId(UUID.fromString(rs.getString(2)))
                .branch(DivineBranch.valueOf(rs.getString(3)))
                .reputation(new AtomicLong(rs.getLong(4)))
                .build();
    }

    @Override
    public void setInsertValues(PreparedStatement ps, Reputation entity) throws SQLException {
        ps.setString(1, entity.getId().toString());
        ps.setString(2, entity.getUserId().toString());
        ps.setString(3, entity.getBranch().name());
        ps.setLong(4, entity.getReputation().get());
    }

    @Override
    public void setUpdateValues(PreparedStatement ps, Reputation entity) throws SQLException {
        ps.setLong(1, entity.getReputation().get());
        ps.setString(2, entity.getId().toString());
    }
}
