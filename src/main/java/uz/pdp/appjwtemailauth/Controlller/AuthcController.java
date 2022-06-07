package uz.pdp.appjwtemailauth.Controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjwtemailauth.Dto.AuthDto;
import uz.pdp.appjwtemailauth.Dto.LoginDto;
import uz.pdp.appjwtemailauth.Servis.ApiResponce;
import uz.pdp.appjwtemailauth.Servis.AuthServis;

@RestController

@RequestMapping("/api/auth")
public class AuthcController {

    @Autowired
    AuthServis authServis;

    @PostMapping("/registr")
    public HttpEntity<?> registrUser(@RequestBody AuthDto authDto) {
        ApiResponce apiResponce = authServis.registrUser(authDto);
        return ResponseEntity.status(apiResponce.isSuccess() ? 201 : 409).body(apiResponce);

    }

    @GetMapping("/veriflyEmail")
    public HttpEntity<?> veriflyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponce apiResponce = authServis.veriflyEmail(email, emailCode);
        return ResponseEntity.status(apiResponce.isSuccess() ? 200 : 409).body(apiResponce);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponce apiResponce = authServis.login(loginDto);
        return ResponseEntity.status(apiResponce.isSuccess() ? 200 : 401).body(apiResponce);
    }
}

