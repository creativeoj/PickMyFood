package org.launchcode.PickMyFood.service;

/**
 * Created by O.J SHIN
 */

import org.launchcode.PickMyFood.models.CustomUserDetails;
import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUsers = userDao.findByName(username);
        if(optionalUsers.isPresent()){
            return optionalUsers.map(CustomUserDetails::new).get();
        }else {
            throw new UsernameNotFoundException("Username not found");
            //throw new UsernameNotFoundException("Username" + username + "not found");
        }
    }

    public void registerNewUserAccount(User user){
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(newUser);
    }
}