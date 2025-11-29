package com.github.koloss.ascension.repository.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.DivineBranch;
import com.github.koloss.ascension.model.Reputation;
import com.github.koloss.ascension.repository.ReputationRepository;
import com.github.koloss.ascension.repository.base.impl.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReputationRepositoryImpl extends BaseRepository<Reputation, UUID> implements ReputationRepository {
    private static final String TABLE_NAME = "reputations";

    public ReputationRepositoryImpl(DatabaseManager manager, ModelMapper<Reputation> mapper) {
        super(manager, mapper);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertString() {
        return "INSERT INTO " + TABLE_NAME + " (id, user_id, branch, reputation) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateString() {
        return "UPDATE " + TABLE_NAME + " SET reputation = ? WHERE id = ?";
    }

    @Override
    public Optional<Reputation> findByUserIdAndBranch(UUID userId, DivineBranch branch) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? AND branch = ?";

        try (PreparedStatement ps = manager.getConnection().prepareStatement(query)) {
            ps.setString(1, userId.toString());
            ps.setString(2, branch.toString());

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
    public List<Reputation> findAllByUserId(UUID userId) {
        List<Reputation> result = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(query)) {
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
