package fit.se2.medicarehub.security;

import fit.se2.medicarehub.model.User;
import fit.se2.medicarehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Giả sử bạn chuyển đổi role thành GrantedAuthority
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRoleID().getRoleName())),
                user.isEnabled()
        );
    }
}
