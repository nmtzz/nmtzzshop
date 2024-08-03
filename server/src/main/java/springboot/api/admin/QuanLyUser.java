package springboot.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.entity.Users;
import springboot.repository.UserRepo;

import java.util.List;

@RequestMapping("/admin/quan-ly-user")
@RestController
public class QuanLyUser {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/")
    public List<Users> getAllUser() {
        return userRepo.findAll();
    }
}
