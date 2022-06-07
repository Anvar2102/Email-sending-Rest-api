package uz.pdp.appjwtemailauth.Servis;

import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appjwtemailauth.Config.JwtToken;
import uz.pdp.appjwtemailauth.Config.SecurityAuth;
import uz.pdp.appjwtemailauth.Dto.AuthDto;
import uz.pdp.appjwtemailauth.Dto.LoginDto;
import uz.pdp.appjwtemailauth.Entity.Enum.RoleEnum;
import uz.pdp.appjwtemailauth.Entity.User;
import uz.pdp.appjwtemailauth.Repository.RolRepository;
import uz.pdp.appjwtemailauth.Repository.UserRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service

public class AuthServis implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtToken jwtToken;


    public ApiResponce registrUser(AuthDto authDto) {
        boolean byEmail = userRepository.existsByEmail(authDto.getEmail());
        if (byEmail) {
            return new ApiResponce("bunday email mavjud", false);
        }
        User user = new User();
        user.setFistname(authDto.getFistname());
        user.setLastname(authDto.getLastname());
        user.setEmail(authDto.getEmail());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));
        user.setRoleList(Collections.singleton(rolRepository.findByRolename(RoleEnum.ROLE_USER)));
        user.setEmailcode(UUID.randomUUID().toString());

        userRepository.save(user);
        sendmail(user.getEmail(), user.getEmailcode());

        return new ApiResponce("Ro'yxatdan o'tdingiz!!! emilgizga kirib tasdiqlang", true);


    }

    public Boolean sendmail(String sendingmail, String emailcode) {
        try {


            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("anvar@java.com");
            simpleMailMessage.setTo(sendingmail);
            simpleMailMessage.setSubject("xabar keldi");
            simpleMailMessage.setText("<a href='https://localhost:8080/api/auth/verifyEmail? emilcode'"
                    + emailcode + "&email" + sendingmail +
                    ">Tasdiqlang<a/>");
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponce veriflyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailcode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailcode(null);
            userRepository.save(user);
            return new ApiResponce("Akkount tasdiqlandi", true);

        }
        return new ApiResponce("Akkount allaqachon tasdiqlangan", false);
    }

    public ApiResponce login(LoginDto loginDto) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtToken.genereteToken(loginDto.getUsername(), user.getRoleList());
            return new ApiResponce("Token", true, token);

        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponce("Username not found", false);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isPresent())
            return byEmail.get();
        throw new UsernameNotFoundException(username + "bunaqa usertopilmadi");

    }
}
