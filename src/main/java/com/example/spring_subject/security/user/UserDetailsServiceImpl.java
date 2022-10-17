package com.example.spring_subject.security.user;

import com.example.spring_subject.entity.Account;
import com.example.spring_subject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    //userDetails안에 Account를 넣어주는 Service
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail ( email ).orElseThrow (
                () -> new RuntimeException ("Not Found Account")
        );

        UserDetailsImpl userDetails = new UserDetailsImpl ();
        userDetails.setAccount ( account );

        return userDetails;
    }
}
