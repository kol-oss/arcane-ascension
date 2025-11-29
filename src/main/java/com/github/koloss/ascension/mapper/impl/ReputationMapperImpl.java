package com.github.koloss.ascension.mapper.impl;

import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.DivineBranch;
import com.github.koloss.ascension.model.Reputation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ReputationMapperImpl implements ModelMapper<Reputation> {
    @Override
    public Reputation toEntity(ResultSet rs) throws SQLException {
        return Reputation.builder()
                .id(UUID.fromString(rs.getString(1)))
                .userId(UUID.fromString(rs.getString(2)))
                .branch(DivineBranch.valueOf(rs.getString(3)))
                .reputation(rs.getLong(4))
                .build();
    }

    @Override
    public void setPreparedValues(PreparedStatement ps, Reputation entity) throws SQLException {
        ps.setString(1, entity.getId().toString());
        ps.setString(2, entity.getUserId().toString());
        ps.setString(3, entity.getBranch().name());
        ps.setLong(4, entity.getReputation());
    }
}
