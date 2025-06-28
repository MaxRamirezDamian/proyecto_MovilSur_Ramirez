package pe.edu.vallegrande.movilsurbackend.infrastructure.service.master;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.User;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.getAll();
    }
}
