package MAGE.mage.security;

import MAGE.mage.model.Administrador;
import MAGE.mage.repository.AdministradorRepository;
import MAGE.mage.security.dto.AuthenticationDto;
import MAGE.mage.security.dto.LoginResponseDto;
import MAGE.mage.security.dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return administradorRepository.findByLogin(login);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto data){
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        System.out.println("Aqui temos o service \n"+usernamePassword+"\n\n\n\n\n");
        var auth = authenticationManager.authenticate(usernamePassword);
        System.out.println("Aqui temos o service2 \n"+usernamePassword+"\n\n\n\n\n");
        var token = tokenService.generateToken((Administrador) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    public ResponseEntity<Object> register (@RequestBody RegisterDto registerDto){
        if (this.administradorRepository.findByLogin(registerDto.login()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.senha());

        Administrador administrador = new Administrador(/*registerDto.login(), encryptedPassword*/);
        administrador.setLogin(registerDto.login());
        administrador.setSenha(encryptedPassword);
        this.administradorRepository.save(administrador);
        return ResponseEntity.ok().build();
    }
}
