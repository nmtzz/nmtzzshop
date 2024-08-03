import { defineStore, acceptHMRUpdate } from "pinia";
import axios from "axios";
axios.defaults.withCredentials = true;
export const useUserStore = defineStore("user", {
  state: () => ({ userInfo: null, isAdmin: false }),
  actions: {
    async getUser() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/nguoi-dung/info"
        );
        if (response.status === 200) {
          this.userInfo = response.data.tenNguoiDung;
        } else {
          this.userInfo = null;
        }
      } catch (error) {
        this.userInfo = null;
      }
    },
    async dangXuat() {
      await axios.get("http://localhost:8080/api/nguoi-dung/dang-xuat");
      this.userInfo = null;
      window.location.reload();
    },
    async checkAdmin() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/nguoi-dung/info"
        );
        if (response.status === 200) {
          let role = response.data.vaiTro;
          if (role == "USER,ADMIN") {
            this.isAdmin = true;
          }
        } else {
          this.isAdmin = false;
        }
      } catch (error) {
        this.isAdmin = false;
      }
    },
  },
});
