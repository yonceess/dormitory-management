package com.diploma.items;


import com.diploma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsRepository itemsRepository;

    public Items createItems(ItemsRequest itemsRequest, String email, String dormitory,
                             String apartment, int room, User user){
        var items = Items.builder().
                name(itemsRequest.getName()).
                dormitory(dormitory).
                apartment(apartment).
                room(room).
                problem(itemsRequest.getProblem()).
                phone(itemsRequest.getPhone()).
                email(email).
                user(user).build();

        return itemsRepository.save(items);

    }

    public List<Items> findAllItems() {
        return (List<Items>) itemsRepository.findAll();
    }

    public List<Items> usersItems(String email){
        List<Items> items = itemsRepository.findByEmail(email);
        return items;
    }

    public void deleteItemById(Integer id){
        itemsRepository.deleteById(id);
    }

    public Items getItems(Integer id){
        return itemsRepository.findById((int) id.longValue()).orElse(null);
    }

    public Page<Items> pageItem(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Items> itemPages = itemsRepository.pageItems(pageable);
        return itemPages;
    }

    public Page<Items> searchItem(int pageNo,String keyword){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Items> itemPages = itemsRepository.search(keyword,pageable);
        return itemPages;
    }
}
