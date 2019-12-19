package de.hsba.bi.Umfrage.web.form;

import de.hsba.bi.Umfrage.user.User;
import org.springframework.stereotype.Component;

@Component
public class FormAssembler {

    public User update(User user, UserForm form) {
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        return user;
    }


}
