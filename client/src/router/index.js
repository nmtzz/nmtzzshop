import { createRouter, createWebHistory } from "vue-router";
import Home from "@/components/Home.vue";
import SanPhamByDanhMuc from "@/components/SanPhamByDanhMuc.vue";
import ChiTietSanPham from "@/components/ChiTietSanPham.vue";
import DangKy from "@/components/DangKy.vue";
import DangNhap from "@/components/DangNhap.vue";
import GioHang from "@/components/GioHang.vue";
import { useUserStore } from "@/stores/NguoiDungStore";
import DatHang from "@/components/DatHang.vue";
import DonHang from "@/components/DonHang.vue";
import TaiKhoan from "@/components/TaiKhoan.vue";
import Admin from "@/components/admins/Admin.vue";
import TrangChu from "@/components/admins/childrens/TrangChu.vue";
import DonHangAdmin from "@/components/admins/childrens/DonHangAdmin.vue";
import SanPhamAdmin from "@/components/admins/childrens/SanPhamAdmin.vue";
import QuanLyUser from "@/components/admins/childrens/QuanLyUser.vue";
import DanhMucAdmin from "@/components/admins/childrens/DanhMucAdmin.vue";
import ThongKeAdmin from "@/components/admins/childrens/ThongKeAdmin.vue";
import NotFound from "@/components/NotFound.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "trang-chu",
      component: Home,
      meta: { title: "Trang chủ" },
    },
    {
      path: "/san-pham/:id/:name",
      name: "san-pham",
      component: SanPhamByDanhMuc,
      meta: { title: "Danh mục sản phẩm" },
    },
    {
      path: "/chi-tiet-san-pham/:id",
      name: "chi-tiet-san-pham",
      component: ChiTietSanPham,
      meta: { title: "Sản phẩm" },
    },
    {
      path: "/dang-ky",
      name: "dang-ky",
      component: DangKy,
      meta: { title: "Đăng ký" },
    },
    {
      path: "/dang-nhap",
      name: "dang-nhap",
      component: DangNhap,
      meta: { title: "Đăng nhập" },
    },
    {
      path: "/gio-hang",
      name: "gio-hang",
      component: GioHang,
      meta: { requiresAuth: true, title: "Giỏ hàng" },
    },
    {
      path: "/dat-hang",
      name: "dat-hang",
      component: DatHang,
      meta: { requiresAuth: true, title: "Đặt hàng" },
    },
    {
      path: "/don-hang",
      name: "don-hang",
      component: DonHang,
      meta: { requiresAuth: true, title: "Đơn hàng" },
    },
    {
      path: "/tai-khoan",
      name: "tai-khoan",
      component: TaiKhoan,
      meta: { requiresAuth: true, title: "Tài khoản" },
    },
    {
      path: "/admin",
      name: "admin",
      component: Admin,
      meta: { title: "Admin", requiredAdmin: true, title: "Trang quản trị" },
      children: [
        {
          path: "",
          component: TrangChu,
        },
        {
          path: "don-hang",
          component: DonHangAdmin,
        },
        {
          path: "san-pham",
          component: SanPhamAdmin,
        },
        {
          path: "user",
          component: QuanLyUser,
        },
        {
          path: "danh-muc",
          component: DanhMucAdmin,
        },
        {
          path: "thong-ke",
          component: ThongKeAdmin,
        },
      ],
    },
    {
      path: "/:pathMatch(.*)*",
      name: "not-found",
      component: NotFound,
      meta: { title: "Trang không tồn tại" },
    },
  ],
});
router.beforeEach(async (to, from, next) => {
  document.title = to.meta.title;
  const userStore = useUserStore();
  await userStore.getUser();
  await userStore.checkAdmin();
  if (
    to.matched.some(
      (record) => record.name === "dang-nhap" || record.name == "dang-ky"
    )
  ) {
    if (userStore.userInfo !== null) {
      next({ name: "trang-chu" });
    }
  }
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (userStore.userInfo === null) {
      next({ name: "dang-nhap" });
    }
  }
  if (to.matched.some((record) => record.meta.requiredAdmin)) {
    if (userStore.isAdmin == false || userStore.userInfo === null) {
      next({ name: "trang-chu" });
    } else {
      next();
    }
  } else {
    next();
  }
});
export default router;
