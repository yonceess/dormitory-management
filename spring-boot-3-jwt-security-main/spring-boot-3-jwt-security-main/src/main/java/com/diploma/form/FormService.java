package com.diploma.form;

import com.diploma.items.Items;
import com.diploma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;

    public Form createForm(FormRequest formRequest, String email, User user){

        var form = Form.builder().
                name(formRequest.getName()).
                address(formRequest.getAddress()).
                date(formRequest.getDate()).
                phone(formRequest.getPhone()).
                reason(formRequest.getReason()).
                email(email).
                dormitory(user.getDormitory())
                .room(user.getRoom())
                 .apartment(user.getApartment()).
                user(user).build();

        return formRepository.save(form);

    }

    public List<Form> findAllForm() {
        return (List<Form>) formRepository.findAll();
    }



    public void deleteFormById(Integer id){
        formRepository.deleteById(id);
    }

    public Form getForm(Integer id){
        return formRepository.findById((int) id.longValue()).orElse(null);
    }

    public Page<Form> pageForm(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Form> formsPages = formRepository.pageForm(pageable);
        return formsPages;
    }

    public Page<Form> searchForm(int pageNo, String keyword){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Form> formPages = formRepository.search(keyword,pageable);
        return formPages;
    }
}
