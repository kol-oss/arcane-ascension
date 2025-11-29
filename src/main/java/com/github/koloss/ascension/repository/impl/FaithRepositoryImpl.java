package com.github.koloss.ascension.repository.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.repository.FaithRepository;
import com.github.koloss.ascension.repository.base.impl.BaseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FaithRepositoryImpl extends BaseRepository<Faith, UUID> implements FaithRepository {
    private static final String TABLE_NAME = "faiths";

    public FaithRepositoryImpl(DatabaseManager manager, ModelMapper<Faith> mapper) {
        super(manager, mapper);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertString() {
        return "INSERT INTO " + TABLE_NAME + " (id, user_id, aspect, count) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateString() {
        return "UPDATE " + TABLE_NAME + " SET count = ? WHERE id = ?";
    }

    @Override
    public Optional<Faith> findByUserIdAndAspect(UUID userId, DivineAspect aspect) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? AND aspect = ?";

        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, userId.toString());
            ps.setString(2, aspect.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                return Optional.of(mapper.toEntity(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Faith> findAllByUserId(UUID userId) {
        List<Faith> result = new ArrayList<>();

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
