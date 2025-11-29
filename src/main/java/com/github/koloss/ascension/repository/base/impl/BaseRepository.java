package com.github.koloss.ascension.repository.base.impl;

import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.repository.base.DataRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T, K> implements DataRepository<T, K> {
    protected final DatabaseManager manager;
    protected final ModelMapper<T> mapper;

    public BaseRepository(DatabaseManager manager, ModelMapper<T> mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    protected abstract String getTableName();

    protected abstract String getInsertString();

    protected abstract String getUpdateString();

    @Override
    public Optional<T> findById(K id) {
        String tableName = getTableName();
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, id.toString());

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
    public List<T> findAll() {
        String tableName = getTableName();
        String query = "SELECT * FROM " + tableName;

        List<T> result = new ArrayList<>();
        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapper.toEntity(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public T insert(T value) {
        String query = getInsertString();

        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            mapper.setInsertValues(ps, value);
            ps.executeUpdate();
            return value;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T update(K id, T value) {
        String query = getUpdateString();

        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            mapper.setUpdateValues(ps, value);
            ps.executeUpdate();
            return value;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteById(K id) {
        String tableName = getTableName();
        String query = "DELETE FROM " + tableName + " WHERE id = ?";

        try (
                Connection connection = manager.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, id.toString());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
