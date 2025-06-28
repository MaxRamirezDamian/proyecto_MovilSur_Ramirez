package pe.edu.vallegrande.movilsurbackend.infrastructure.rest.master;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.User;
import pe.edu.vallegrande.movilsurbackend.infrastructure.service.master.UserService;

import java.util.List;

@RestController
@CrossOrigin( origins = "*")
@RequiredArgsConstructor
public class UserRest {
    private final UserService userService;

    @GetMapping("/listar-usuarios")
    public List<User> getall(){
        return userService.getAll();
    }
}
