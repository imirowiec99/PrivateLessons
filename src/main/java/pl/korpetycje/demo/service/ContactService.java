package pl.korpetycje.demo.service;

import org.springframework.stereotype.Service;
import pl.korpetycje.demo.dto.ContactDto;
import pl.korpetycje.demo.model.Contact;
import pl.korpetycje.demo.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {
    public final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts(){
        return this.contactRepository.findAll();
    }

    public Contact getContact(long id){
        return this.contactRepository.findById(id).orElseThrow();
    }

    public void addContact(Contact contact){
        this.contactRepository.save(contact);
    }
    public void deleteContact(long id){
        this.contactRepository.deleteById(id);
    }
}
