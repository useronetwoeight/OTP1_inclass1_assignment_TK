package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TemperatureUnitDAO {

    public List<TemperatureUnit> findAll() throws SQLException {
        List<TemperatureUnit> units = new ArrayList<>();

        try (Connection conn = DBConnection.get();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, symbol FROM temperature_unit ORDER BY id")) {

            while (rs.next()) {
                units.add(new TemperatureUnit(rs.getInt("id"), rs.getString("name"), rs.getString("symbol")));
            }
        }
        return units;
    }

    public TemperatureUnit findBySymbol(String symbol) throws SQLException {
        try (Connection conn = DBConnection.get();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name, symbol FROM temperature_unit WHERE symbol = ?")) {

            ps.setString(1, symbol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TemperatureUnit(rs.getInt("id"), rs.getString("name"), rs.getString("symbol"));
                }
            }
        }
        return null;
    }

    public TemperatureUnit findById(int id) throws SQLException {
        try (Connection conn = DBConnection.get();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name, symbol FROM temperature_unit WHERE id = ?")) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TemperatureUnit(rs.getInt("id"), rs.getString("name"), rs.getString("symbol"));
                }
            }
        }
        return null;
    }
}