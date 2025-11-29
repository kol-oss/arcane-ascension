package com.github.koloss.ascension.repository.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.Spell;
import com.github.koloss.ascension.repository.SpellRepository;
import com.github.koloss.ascension.repository.base.impl.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpellRepositoryImpl extends BaseRepository<Spell, UUID> implements SpellRepository {
    private static final String TABLE_NAME = "spells";

    public SpellRepositoryImpl(DatabaseManager manager, ModelMapper<Spell> mapper) {
        super(manager, mapper);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertString() {
        return "INSERT INTO " + TABLE_NAME + " (id, user_id, type, level) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateString() {
        return "UPDATE " + TABLE_NAME + " SET user_id = ?, type = ?, level = ? WHERE id = ?";
    }

    @Override
    public List<Spell> findAllByUserId(UUID userId) {
        List<Spell> result = new ArrayList<>();

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
