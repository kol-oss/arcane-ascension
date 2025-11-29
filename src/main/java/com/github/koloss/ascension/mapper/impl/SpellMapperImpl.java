package com.github.koloss.ascension.mapper.impl;

import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.Spell;
import com.github.koloss.ascension.model.SpellLevel;
import com.github.koloss.ascension.model.SpellType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpellMapperImpl implements ModelMapper<Spell> {
    @Override
    public Spell toEntity(ResultSet rs) throws SQLException {
        return Spell.builder()
                .id(UUID.fromString(rs.getString(1)))
                .userId(UUID.fromString(rs.getString(2)))
                .type(SpellType.valueOf(rs.getString(3)))
                .level(SpellLevel.valueOf(rs.getString(4)))
                .build();
    }

    @Override
    public void setInsertValues(PreparedStatement ps, Spell entity) throws SQLException {
        ps.setString(1, entity.getId().toString());
        ps.setString(2, entity.getUserId().toString());
        ps.setString(3, entity.getType().name());
        ps.setString(4, entity.getLevel().name());
    }

    @Override
    public void setUpdateValues(PreparedStatement ps, Spell entity) throws SQLException {
        ps.setString(1, entity.getLevel().name());
        ps.setString(2, entity.getId().toString());
    }
}
