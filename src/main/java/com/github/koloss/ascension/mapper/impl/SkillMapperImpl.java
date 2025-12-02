package com.github.koloss.ascension.mapper.impl;

import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SkillMapperImpl implements ModelMapper<Skill> {
    @Override
    public Skill toEntity(ResultSet rs) throws SQLException {
        return Skill.builder()
                .id(UUID.fromString(rs.getString(1)))
                .userId(UUID.fromString(rs.getString(2)))
                .type(SkillType.valueOf(rs.getString(3)))
                .levelCount(new AtomicInteger(rs.getInt(4)))
                .progressCount(new AtomicLong(rs.getLong(5)))
                .build();
    }

    @Override
    public void setInsertValues(PreparedStatement ps, Skill entity) throws SQLException {
        ps.setString(1, entity.getId().toString());
        ps.setString(2, entity.getUserId().toString());
        ps.setString(3, entity.getType().name());
        ps.setInt(4, entity.getLevel());
        ps.setLong(5, entity.getProgress());
    }

    @Override
    public void setUpdateValues(PreparedStatement ps, Skill entity) throws SQLException {
        ps.setInt(1, entity.getLevel());
        ps.setLong(2, entity.getProgress());
        ps.setString(3, entity.getId().toString());
    }
}
