package com.github.koloss.ascension.repository.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.model.Reputation;
import com.github.koloss.ascension.repository.ReputationRepository;
import com.github.koloss.ascension.repository.base.impl.BaseRepository;

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
        return "UPDATE " + TABLE_NAME + " SET user_id = ?, branch = ?, reputation = ? WHERE id = ?";
    }
}
