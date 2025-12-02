package com.github.koloss.ascension.repository.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.repository.SkillRepository;
import com.github.koloss.ascension.repository.base.impl.BaseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkillRepositoryImpl extends BaseRepository<Skill, UUID> implements SkillRepository {
    private static final String TABLE_NAME = "skills";

    public SkillRepositoryImpl(DatabaseManager manager, ModelMapper<Skill> mapper) {
        super(manager, mapper);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertString() {
        return "INSERT INTO " + TABLE_NAME + " (id, user_id, type, level, count) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateString() {
        return "UPDATE " + TABLE_NAME + " SET level = ?, count = ? WHERE id = ?";
    }

    @Override
    public Optional<Skill> findByUserIdAndType(UUID userId, SkillType type) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? AND type = ?";

        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, userId.toString());
            ps.setString(2, type.toString());

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
    public List<Skill> findAllByUserId(UUID userId) {
        List<Skill> result = new ArrayList<>();

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
