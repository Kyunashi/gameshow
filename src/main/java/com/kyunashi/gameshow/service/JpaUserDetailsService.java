package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.model.SecurityUser;
import com.kyunashi.gameshow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        return userRepository.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

//    @Override
//    @Transactional(readOnly=true)
//    public UserDetails loadUserByUsername(String username) {
//
//        Optional<User> userOpt = userRepository.findByUsername(username);
//
//        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), user.isEnabled(), true, true, true, getAuthorities("USER"));
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        return Collections.singletonList(new SimpleGrantedAuthority(role));
//    }
}
