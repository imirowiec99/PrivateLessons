package pl.korpetycje.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.korpetycje.demo.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
