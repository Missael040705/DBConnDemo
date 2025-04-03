package org.example.dbconndemo.database;

import javafx.collections.FXCollections;
import org.example.dbconndemo.models.Category;
import org.example.dbconndemo.models.Product;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductDao extends MySQLConnection implements Dao<Product> {
    Connection conn = getConnection();

    @Override
    public Optional<Product> findById(int id_product) {
        Optional<Product> optProduct = Optional.empty();
        String query = "select * from products where id_product = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id_product);
            ResultSet rs = statement.executeQuery();
            if ( rs.next() )
            {
                Product product = new Product();
                product.setId(rs.getInt("id_product"));
                product.setName(rs.getString("name"));
                product.setColor(rs.getString("color"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(getCategory(rs.getInt("category_id")));
                product.setImage(rs.getString("image"));
                optProduct = Optional.of(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optProduct;
    }

    @Override
    public List<Product> findAll() {

        List<Product> productsList = FXCollections.observableArrayList();
        String query = "select p.*, c.name as category_Name\n" +
                "from products p \n" +
                "join categories c on p.category_id = c.id_category";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                Product p = new Product();
                p.setId(rs.getInt("id_product"));
                p.setName(rs.getString("name"));
                p.setColor(rs.getString("color"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory_Name(rs.getString("category_Name"));
                p.setDescription(rs.getString("description"));
                //p.setCategory(getCategory(rs.getInt("category_id")));
                p.setImage(rs.getString("image"));
                productsList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productsList;
    }

    public List<Product> findByCategories(String nameC) {

        List<Product> productsList = FXCollections.observableArrayList();
        String query = "select p.*, c.name as category_Name\n" +
                "from products p \n" +
                "join categories c on p.category_id = c.id_category\n" +
                "where c.name = '" + nameC + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                Product p = new Product();
                p.setId(rs.getInt("id_product"));
                p.setName(rs.getString("name"));
                p.setColor(rs.getString("color"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory_Name(rs.getString("category_Name"));
                p.setDescription(rs.getString("description"));
                //p.setCategory(getCategory(rs.getInt("category_id")));
                p.setImage(rs.getString("image"));
                productsList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productsList;
    }

    public Map<String, Integer> totalProductsByCategory() {

        Map<String, Integer> results = new HashMap<String, Integer>();
        String query = "select c.name as category, COUNT(*) as total \n" +
                "from products p\n" +
                "join categories c on p.category_id = c.id_category\n" +
                "group by 1\n" +
                "order by 2 desc";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                results.put(rs.getString("category"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
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
            category.setColor(rs.getString("color"));
            category.setIcon(rs.getString("icon"));
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean save(Product product) {

        String query = "insert into products " +
                        " (name, description, price, quantity, color, image, category_id)" +
                        " values (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getColor());
            ps.setString(6, product.getImage());
            ps.setInt(7, product.getCategory_id());
            ps.execute();
            return true;
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;

    }
    @Override
    public boolean update(Product product) {
        String query = "update products set name=?, description=?, color=?, price=?, quantity=?, image=?, category_id=?  where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getColor());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6, product.getCategory_id());
            ps.setString(7, product.getImage());
            ps.setInt(8, product.getId_product());
            System.out.println("ID to update: " + product.getId_product());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateName(String name, int productId) {
        String query = "update products set name=?  where id_product = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, productId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int product_id) {
        String query = "delete from products where id_product = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, product_id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
