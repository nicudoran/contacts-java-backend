package com.main.tema_c1_2.controller;

import com.main.tema_c1_2.entity.Contact;
import com.main.tema_c1_2.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.lang.Integer.parseInt;

@RestController
public class ContactController {
    @Autowired
    ContactService contactService;

    @CrossOrigin
    @PostMapping("/contact/add")
        public boolean setContactDetails(@RequestBody Contact contact){
            contactService.writeContactDetailsToFile(contact);
            return true;

    }

    @CrossOrigin
    @GetMapping("/contact/getAll")
    public Object getContactsDetails(){
        return contactService.getAll();
    }


    @CrossOrigin
    @GetMapping("/contact/get/{name}")
    public Object getContact(@PathVariable("name") String name){
      return contactService.getByName(name);
    };

    @CrossOrigin
    @DeleteMapping("/contact/delete/{id}")
        public String deleteContact(@PathVariable("id") int id){return contactService.deleteContact(id);
        }

}
