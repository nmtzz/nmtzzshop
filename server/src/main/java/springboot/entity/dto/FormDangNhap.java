package springboot.entity.dto;

import springboot.entity.Users;

import java.io.Serializable;

/**
 * DTO for {@link Users}
 */
public record FormDangNhap(String tenNguoiDung, String matKhau) implements Serializable {
}