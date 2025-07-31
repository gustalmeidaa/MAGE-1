package MAGE.mage.security;

import MAGE.mage.model.Administrador;
import MAGE.mage.repository.AdministradorRepository;
import MAGE.mage.dto.AdministradorDto;
import MAGE.mage.security.dto.LoginResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ResponseEntity<Object> login(@RequestBody @Valid AdministradorDto data){
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Administrador) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

//    public ResponseEntity<Object> register (@RequestBody RegisterDto registerDto){
//        if (this.administradorRepository.findByLogin(registerDto.login()) != null) return ResponseEntity.badRequest().build();
//        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.senha());
//
//        Administrador administrador = new Administrador(registerDto.login(), encryptedPassword);
//        this.administradorRepository.save(administrador);
//        return ResponseEntity.ok().build();
//    }
}
