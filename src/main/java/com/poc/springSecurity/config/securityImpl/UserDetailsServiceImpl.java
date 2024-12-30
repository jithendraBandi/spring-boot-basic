package com.poc.springSecurity.config.securityImpl;

import com.poc.springSecurity.entity.Users;
import com.poc.springSecurity.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given username and password"));
//        List<GrantedAuthority> authorities = user.getRoles() != null ?
//                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//                :
//                List.of(new SimpleGrantedAuthority("USER"));
//        return new User(user.getUsername(),user.getPassword(), authorities);
//        return new User(user.getUsername(),user.getEncodedPassword(), authorities);
        return new UserDetailsImpl(user);
    }
}
