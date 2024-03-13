package pl.korpetycje.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private String subject;
    private String message;

    @Override
    public String toString(){
        return this.subject + this.message;
    }
}
