package com.diploma.auth;

import com.diploma.config.JwtService;
import com.diploma.token.Token;
import com.diploma.token.TokenRepository;
import com.diploma.token.TokenType;
import com.diploma.user.Role;
import com.diploma.user.User;
import com.diploma.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public boolean register(RegisterRequest request) {
      Optional<User> check;
      check = repository.findByEmail(request.getEmail());
      System.out.println(check.toString());
      System.out.println(check.isEmpty());
    if(!check.isEmpty()){
      return false;
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
            .dormitory(request.getDormitory())
            .room(request.getRoom())
            .apartment(request.getApartment())
            .idCard(request.getIdCard())
        .role(Role.ROLE_USER)
        .build();


     repository.save(user);
     return true;

  }

  public boolean authenticate(AuthenticationRequest request) {
      if(repository.findByEmail(request.getEmail()) != null){
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return true;}
      else {
          return false;
      }
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public User updateUser(RegisterRequest request){
    Optional<User> userOptional = repository.findByEmail(request.getEmail());
    User user1 = userOptional.get();
    String email = user1.getEmail();
     if(email.equals(request.getEmail())){
       Optional<User> userOptional1 = repository.findByEmail(request.getEmail());
       User user = userOptional1.get();
       user.setFirstname(request.getFirstname());
       user.setLastname(request.getLastname());
       user.setDormitory(request.getDormitory());
       user.setApartment(request.getApartment());
       user.setRoom(request.getRoom());
       user.setIdCard(request.getIdCard());
       return repository.save(user);
     }
     else {
       return null;
     }
   }

   public void deleteUserById(Integer id){
    repository.deleteById(id);
   }

    public User getUser(Integer id){
        return repository.findById((int) id.longValue()).orElse(null);
    }

    public Page<User> listAllU(int pageNum, String keyword) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<User> page;
        if (keyword != null) {
            page = repository.search(keyword,pageable);
        }
        else {
            page = repository.findAll(pageable);
        }

        return
                page;
    }

    public List<User> all() {
        return repository.findAll();
    }

    public Page<User> pageUser(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<User> userPages = repository.pageUser(pageable);
        return userPages;
    }

    public Page<User> searchUser(int pageNo,String keyword){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<User> users = repository.search(keyword,pageable);
        return users;
    }

    public Page<User> searchUserByDorm(int pageNo,String keyword){
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<User> users = repository.searchDorm(keyword,pageable);
        return users;
    }

    public boolean checkRole(String email){
      Optional<User> user = repository.findByEmail(email);
      User user1 = user.get();
        System.out.println(user1.getRole());
      if(user1.getRole().toString().equals("ROLE_ADMIN")){
          return true;
      }
      else {
          return false;
      }
    }

    public User getByEmail(String email){
         Optional<User> optUser=repository.findByEmail(email);
         User user = optUser.get();
         return user;
    }


}
