package pl.korpetycje.demo.dto;

import pl.korpetycje.demo.model.Contact;

public class ContactToEntity {
    private ContactDto contactDto;

    public ContactToEntity(ContactDto contactDto){
        this.contactDto = contactDto;
    }

    public static Contact contactDtoToEntity(ContactDto contactDto){
        Contact contact = new Contact();
        contact.setEmail(contactDto.getEmail());
        contact.setName(contactDto.getName());
        contact.setPhone(contactDto.getPhone());
        contact.setMessage(contactDto.getMessage());
        return contact;
    }
}
