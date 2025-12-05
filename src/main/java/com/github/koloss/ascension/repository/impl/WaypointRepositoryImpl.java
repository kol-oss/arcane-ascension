package com.github.koloss.ascension.repository.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.Waypoint;
import com.github.koloss.ascension.repository.WaypointRepository;
import com.github.koloss.ascension.repository.base.impl.BaseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WaypointRepositoryImpl extends BaseRepository<Waypoint, UUID> implements WaypointRepository {
    private static final String TABLE_NAME = "waypoints";

    public WaypointRepositoryImpl(DatabaseManager manager, ModelMapper<Waypoint> mapper) {
        super(manager, mapper);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertString() {
        return "INSERT INTO " + TABLE_NAME + " (id, user_id, name, x, y, z, teleport_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateString() {
        return "UPDATE " + TABLE_NAME + " SET teleport_at = ? WHERE id = ?";
    }

    @Override
    public List<Waypoint> findAllByUserId(UUID userId) {
        List<Waypoint> result = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, userId.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapper.toEntity(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }
}
