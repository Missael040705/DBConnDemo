package org.example.dbconndemo.database;

import javafx.collections.FXCollections;
import org.example.dbconndemo.models.Category;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CategoryDao extends MySQLConnection implements Dao<Category> {
    Connection conn = getConnection();
    @Override
    public Optional<Category> findById(int id) {
        return Optional.empty();
    }
    @Override
    public List<Category> findAll() {

        List<Category> categoriesList = FXCollections.observableArrayList();
        String query = "select * from categories";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                Category c = new Category();
                c.setId(rs.getInt("id_category"));
                c.setName(rs.getString("name"));
                categoriesList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoriesList;
    }

    private Category getCategory(int category_id)
    {
        String query = "select * from categories where id_category = " + category_id;

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            Category category = new Category();
            category.setId(rs.getInt("id_category"));
            category.setName(rs.getString("name"));
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean save(Category category) {

        String query = "insert into categories (name, color, image)" +
                       " values (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.setString(2, category.getColor());
            ps.setString(3, category.getIcon());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return false;

    }
    @Override
    public boolean update(Category category) {
        String query = "update categories set name=?, color=?, image=? where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.setString(2, category.getColor());
            ps.setString(3, category.getIcon());
            ps.setInt(4, category.getId_category());
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(int category_id) {
        String query = "delete from categories where id_category = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, category_id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
