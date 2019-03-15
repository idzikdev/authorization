package pl.idzikdev.authorization.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.idzikdev.authorization.entities.UserEntity;
import pl.idzikdev.authorization.forms.LoginForm;
import pl.idzikdev.authorization.forms.RegisterForm;
import pl.idzikdev.authorization.repositories.UserRepository;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Optional;

@Service
@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class UserService {
    public enum LoginResponse {
        SUCCESS, BAD_CREDENTIALS, BANNED;
    }


    final UserRepository userRepository;

    final UserSession userSession;

    @Autowired
    public UserService(UserRepository userRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean registerUser(RegisterForm registerForm) {
        if (!isLoginFree(registerForm.getLogin())) {
            return false;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerForm.getEmail());
        userEntity.setLogin(registerForm.getLogin());
        userEntity.setPassword(getBCrypt().encode(registerForm.getPassword()));

        userRepository.save(userEntity);
        return true;
    }

    public LoginResponse login(LoginForm loginForm) {
        Optional<UserEntity> userOptional = userRepository.findByLogin(loginForm.getLogin());
        if (!userOptional.isPresent()) {
            return LoginResponse.BAD_CREDENTIALS;
        }

        if (!getBCrypt().matches(loginForm.getPassword(), userOptional.get().getPassword())) {
            return LoginResponse.BAD_CREDENTIALS;
        }

        userSession.setLogin(true);
        userSession.setUserEntity(userOptional.get());
        return LoginResponse.SUCCESS;
    }

    public void logout() {
        userSession.setLogin(false);
        userSession.setUserEntity(null);
    }

    private boolean isLoginFree(String login) {
        return !userRepository.existsByLogin(login);
    }

    @Bean
    public BCryptPasswordEncoder getBCrypt() {
        return new BCryptPasswordEncoder();
    }
}