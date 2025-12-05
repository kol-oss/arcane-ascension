package com.github.koloss.ascension.mapper.impl;

import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.Waypoint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WaypointMapperImpl implements ModelMapper<Waypoint> {
    @Override
    public Waypoint toEntity(ResultSet rs) throws SQLException {
        return Waypoint.builder()
                .id(UUID.fromString(rs.getString(1)))
                .userId(UUID.fromString(rs.getString(2)))
                .name(rs.getString(3))
                .x(rs.getDouble(4))
                .y(rs.getDouble(5))
                .z(rs.getDouble(6))
                .teleportAt(rs.getLong(7))
                .build();
    }

    @Override
    public void setInsertValues(PreparedStatement ps, Waypoint entity) throws SQLException {
        ps.setString(1, entity.getId().toString());
        ps.setString(2, entity.getUserId().toString());
        ps.setString(3, entity.getName());
        ps.setDouble(4, entity.getX());
        ps.setDouble(5, entity.getY());
        ps.setDouble(6, entity.getZ());
        ps.setLong(7, entity.getTeleportAt());
    }

    @Override
    public void setUpdateValues(PreparedStatement ps, Waypoint entity) throws SQLException {
        ps.setLong(1, entity.getTeleportAt());
        ps.setString(2, entity.getId().toString());
    }
}
