package warehouse.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import warehouse.domain.Item;
import warehouse.exception.CategoryNotFoundException;
import warehouse.exception.ColorNotFoundException;
import warehouse.exception.ItemNotFoundException;
import warehouse.exception.SizeNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public InMemoryItemRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM ITEMS";
        Map<String, Object> params = new HashMap<>();
        return jdbcTemplate.query(sql, params, new ItemMapper());
    }

    @Override
    public Item getItemById(String itemId) {
        String sql = "SELECT * FROM ITEMS WHERE ID = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", itemId);
        try {
            return jdbcTemplate.queryForObject(sql, params, new ItemMapper());
        } catch (DataAccessException e) {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public Item getItemByName(String name) {
        String sql = "SELECT * FROM ITEMS WHERE NAME = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        try {
            return jdbcTemplate.queryForObject(sql, params, new ItemMapper());
        } catch (DataAccessException e) {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        String sql = "SELECT * FROM ITEMS WHERE CATEGORY = :category";
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        List<Item> items = jdbcTemplate.query(sql, params, new ItemMapper());
        if (items.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return items;
    }

    @Override
    public List<Item> getItemsByColor(String color) {
        String sql = "SELECT * FROM ITEMS WHERE COLOR = :color";
        Map<String, Object> params = new HashMap<>();
        params.put("color", color);
        List<Item> items = jdbcTemplate.query(sql, params, new ItemMapper());
        if (items.isEmpty()) {
            throw new ColorNotFoundException();
        }
        return items;
    }

    @Override
    public List<Item> getItemsBySize(String size) {
        String sql = "SELECT * FROM ITEMS WHERE SIZE = :size";
        Map<String, Object> params = new HashMap<>();
        params.put("size", size);
        List<Item> items = jdbcTemplate.query(sql, params, new ItemMapper());
        if (items.isEmpty()) {
            throw new SizeNotFoundException();
        }
        return items;
    }

    @Override
    public List<Item> getItemsByArchived(boolean archived) {
        String sql = "SELECT * FROM ITEMS WHERE ARCHIVED = :archived";
        Map<String, Object> params = new HashMap<>();
        params.put("archived", archived);
        return jdbcTemplate.query(sql, params, new ItemMapper());
    }

    @Override
    public void addItem(Item newItem) {
        String sql = "INSERT INTO ITEMS VALUES (" +
                ":id, :name, :category, :color, :size, :quantity, :archived)";
        Map<String, Object> params = new HashMap<>();
        params.put("id", newItem.getItemId());
        params.put("name", newItem.getName());
        params.put("category", newItem.getCategory());
        params.put("color", newItem.getColor());
        params.put("size", newItem.getSize());
        params.put("quantity", newItem.getQuantity());
        params.put("archived", newItem.isArchived());
        jdbcTemplate.update(sql, params);
    }

    private class ItemMapper implements RowMapper<Item> {
        @Override
        public Item mapRow(ResultSet resultSet, int i) throws SQLException {
            Item item = new Item();
            item.setItemId(resultSet.getString("ID"));
            item.setName(resultSet.getString("NAME"));
            item.setCategory(resultSet.getString("CATEGORY"));
            item.setColor(resultSet.getString("COLOR"));
            item.setSize(resultSet.getString("SIZE"));
            item.setQuantity(resultSet.getLong("QUANTITY"));
            item.setArchived(resultSet.getBoolean("ARCHIVED"));
            return item;
        }
    }
}
