package springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.entity.Users;
import springboot.repository.UsersRepo;

import java.util.Optional;

@Service
public class NguoiDungDetailService implements UserDetailsService {
    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> nguoiDung = usersRepo.findNguoiDungByTenNguoiDung(username);
        if (nguoiDung.isPresent()) {
            Users users1 = nguoiDung.get();
            return User.builder()
                    .username(users1.getTenNguoiDung())
                    .password(users1.getMatKhau())
                    .roles(getArrVaiTro(users1))
                    .build();
        } else {
            return null;
        }
    }

    public String[] getArrVaiTro(Users users) {
        if (users.getVaiTro() == null) {
            return new String[]{"USER"};
        }
        return users.getVaiTro().split(",");
    }
}
