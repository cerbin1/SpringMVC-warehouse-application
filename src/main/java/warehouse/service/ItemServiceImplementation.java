package warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warehouse.domain.Item;
import warehouse.domain.repository.ItemRepository;

import java.util.List;

@Service
public class ItemServiceImplementation implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    @Override
    public Item getItemById(String itemId) {
        return itemRepository.getItemById(itemId);
    }

    @Override
    public Item getItemByName(String name) {
        return itemRepository.getItemByName(name);
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        return itemRepository.getItemsByCategory(category);
    }

    @Override
    public List<Item> getItemsByColor(String color) {
        return itemRepository.getItemsByColor(color);
    }
}
