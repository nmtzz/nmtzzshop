<script setup>
import { useUserStore } from '@/stores/NguoiDungStore';
import axios from 'axios';
import { ref } from 'vue';
import { useToast } from "primevue/usetoast";
const toast = useToast();
const userStore = useUserStore()
let gioHangList = ref([])
async function doGetGioHang() {
    const resp = await axios.get('http://localhost:8080/api/gio-hang/?tenNguoiDung=' + userStore.userInfo)
    gioHangList.value = resp.data
}
async function updateSoLuong(soLuong, id) {
    await axios.patch('http://localhost:8080/api/gio-hang/?soLuong=' + soLuong + '&maGioHang=' + id)
}
async function xoaGioHang(id) {
    const resp = await axios.delete('http://localhost:8080/api/gio-hang/?id=' + id)
    if (resp.status === 200) {
        toast.add({ severity: 'success', summary: 'Thành công', detail: 'Đã xóa', life: 3000 })
        await doGetGioHang()
    }
}
doGetGioHang()
</script>
<template>
    <Toast />
    <div class="container-fluid py-5">
        <div class="container pb-5">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Ảnh</th>
                            <th scope="col">Tên</th>
                            <th scope="col">Màu sắc</th>
                            <th scope="col">Kích thước</th>
                            <th scope="col">Giá</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Tổng</th>
                            <th scope="col">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="gioHangList.length === 0" class="text-center">
                            <td colspan="8">
                                <h1>GIỎ HÀNG TRỐNG</h1>
                            </td>
                        </tr>
                        <tr v-for="gioHang in gioHangList" :key="gioHang.id">
                            <th scope="row">
                                <div class="d-flex align-items-center">
                                    <img :src="gioHang.hinhAnh" class="img-fluid me-5 rounded-circle"
                                        style="width: 80px; height: 80px;" alt="">
                                </div>
                            </th>
                            <td>
                                <p class="mb-0 mt-4">{{ gioHang.tenSanPham }}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">{{ gioHang.mauSac }}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">{{ gioHang.kichThuoc }}</p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">{{ gioHang.giaTien }}</p>
                            </td>
                            <td>
                                <div class="input-group quantity mt-4" style="width: 100px;">
                                    <div class="input-group-btn">
                                        <button
                                            @click="gioHang.soLuong > 1 ? gioHang.soLuong-- : null; updateSoLuong(gioHang.soLuong, gioHang.id)"
                                            class="btn btn-sm btn-minus rounded-circle bg-light border">
                                            <i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                    <input type="text" class="form-control form-control-sm text-center border-0"
                                        :value="gioHang.soLuong">
                                    <div class="input-group-btn">
                                        <button @click="gioHang.soLuong++; updateSoLuong(gioHang.soLuong, gioHang.id)"
                                            class="btn btn-sm btn-plus rounded-circle bg-light border">
                                            <i class="fa fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="mb-0 mt-4 tongMoiSanPham">{{ gioHang.giaTien * gioHang.soLuong }}</p>
                            </td>
                            <td>
                                <button @click="xoaGioHang(gioHang.id)"
                                    class="btn btn-md rounded-circle bg-light border mt-4">
                                    <i class="fa fa-times text-danger"></i>
                                </button>
                            </td>

                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="row g-4 justify-content-end">
                <div class="col-8"></div>
                <div class="col-sm-8 col-md-7 col-lg-6 col-xl-4">
                    <div class="bg-light rounded">
                        <div class="p-4">
                            <h1 class="display-6 mb-4">Giỏ hàng</h1>
                        </div>
                        <div class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
                            <h5 class="mb-0 ps-4 me-4">Tổng</h5>
                            <p class="mb-0 pe-4">{{ gioHangList.reduce((sum, gioHang) => sum + gioHang.giaTien *
                                gioHang.soLuong, 0) }}</p>
                        </div>
                        <a :href="'/dat-hang'"
                            class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4 ms-4"
                            type="button">Thanh toán & Đặt hàng</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>