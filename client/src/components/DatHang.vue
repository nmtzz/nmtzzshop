<script setup>
import { useUserStore } from '@/stores/NguoiDungStore';
import axios from 'axios';
import { ref } from 'vue';
import { useToast } from "primevue/usetoast";
import { useConfirm } from "primevue/useconfirm";
import { useRoute, useRouter } from 'vue-router';
const router = useRouter()
const route = useRoute()
const idChiTietSp = route.query.sp ? route.query.sp : null
const soLuongSp = route.query.soLuong ? route.query.soLuong : null
const confirm = useConfirm();
const toast = useToast();
const userStore = useUserStore()
let gioHangList = ref([])
async function doGetGioHang() {
    const resp = await axios.get('http://localhost:8080/api/gio-hang/?tenNguoiDung=' + userStore.userInfo)
    gioHangList.value = resp.data
}
// doGetGioHang()
if (idChiTietSp == null && soLuongSp == null) {
    doGetGioHang()
} else {
    async function doGetChiTietSanPham() {
        const resp = await axios.get(`http://localhost:8080/api/ctsp/ct?id=${idChiTietSp}`)
        gioHangList.value.push({
            idChiTiet: idChiTietSp,
            soLuong: soLuongSp,
            giaTien: resp.data.giaTien,
            mauSac: resp.data.mauSac,
            kichThuoc: resp.data.kichThuoc,
            hinhAnh: resp.data.hinhAnh,
            tenSanPham: resp.data.tenSanPham
        })
    }
    doGetChiTietSanPham()
}
let tenNguoiNhan = ref('')
let diaChiGiaoHang = ref('')
let soDienThoai = ref('')
let ghiChu = ref('')
function showXacNhan() {
    confirm.require({
        group: 'templating',
        header: 'Xác nhận đặt hàng',
        message: 'Bạn có thể hủy đơn hàng này trong 5 phút kể từ lúc đặt.',
        icon: 'pi pi-info-circle',
        rejectProps: {
            label: 'Hủy',
            icon: 'pi pi-times',
            outlined: true,
            size: 'small'
        },
        acceptProps: {
            label: 'Đặt',
            icon: 'pi pi-check',
            size: 'small'
        },
        accept: async () => {
            let dsChiTietHoaDon = ref([]);
            gioHangList.value.forEach(gioHang => {
                dsChiTietHoaDon.value.push({
                    maHoaDon: null,
                    maChiTiet: { id: gioHang.idChiTiet },
                    soLuong: gioHang.soLuong
                })
            })
            let newHoaDon = ref({
                tenUsers: { tenNguoiDung: userStore.userInfo },
                tongTien: parseInt(document.getElementById('tong').innerText),
                trangThai: "Đã đặt",
                tenNguoiNhan: tenNguoiNhan.value,
                diaChiGiaoHang: diaChiGiaoHang.value,
                soDienThoai: soDienThoai.value,
                ghiChu: ghiChu.value
            })
            try {
                const payload = {
                    hoaDon: newHoaDon.value,
                    chiTietHoaDonList: dsChiTietHoaDon.value
                }
                const resp = await axios.post('http://localhost:8080/user/dat-hang/tao-don-hang', payload)
                if (resp.status === 201) {
                    confirm.require({
                        group: 'templating',
                        header: 'Đặt hàng thành công',
                        message: 'Mã đơn hàng của bạn là: ' + resp.data,
                        icon: 'fa-solid fa-check',
                        rejectProps: {
                            label: 'Đóng',
                            icon: 'pi pi-times',
                            outlined: true,
                            size: 'small'
                        },
                        acceptProps: {
                            label: 'Xem đơn hàng',
                            icon: 'pi pi-check',
                            size: 'small'
                        }
                    });
                    setTimeout(() => {
                        router.push(`/don-hang?s=true&hd=${resp.data}`)
                    }, 600);
                }

            } catch (error) {
                toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Đã xảy ra lỗi', life: 3000 })
            }
        }
    });
}
</script>
<template>
    <Toast />
    <ConfirmDialog group="templating">
        <template #message="slotProps">
            <div class="fs d-flex flex-column align-items-center w-100 gap-4">
                <i :class="slotProps.message.icon" style="font-size: 4rem;"></i>
                <p>{{ slotProps.message.message }}</p>
            </div>
        </template>
    </ConfirmDialog>
    <div class="container-fluid py-5">
        <div class="container py-5">
            <h1 class="mb-4">Thông Tin Giao Hàng</h1>
            <form action="#">
                <div class="row g-5">
                    <div class="col-md-12 col-lg-6 col-xl-6 text-capitalize">
                        <div class="form-item">
                            <label class="form-label my-3">Tên người nhận hàng<sup>*</sup></label>
                            <input v-model="tenNguoiNhan" type="text" class="form-control">
                        </div>
                        <div class="form-item">
                            <label class="form-label my-3">Địa chỉ giao hàng<sup>*</sup></label>
                            <input v-model="diaChiGiaoHang" type="text" class="form-control">
                        </div>
                        <div class="form-item">
                            <label class="form-label my-3">Số điện thoại liên hệ<sup>*</sup></label>
                            <input v-model="soDienThoai" type="text" class="form-control">
                        </div>
                        <div class="form-check my-3">
                        </div>
                        <div class="form-item">
                            <textarea v-model="ghiChu" class="form-control" spellcheck="false" cols="30" rows="11"
                                placeholder="Ghi Chú (Tùy Chọn)"></textarea>
                        </div>
                    </div>
                    <div class="col-md-12 col-lg-6 col-xl-6">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Ảnh</th>
                                        <th scope="col">Tên</th>
                                        <th scope="col">Giá</th>
                                        <th scope="col">Số lượng</th>
                                        <th scope="col">Tổng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="gioHang in gioHangList">
                                        <th scope="row">
                                            <div class="d-flex align-items-center mt-2">
                                                <img :src="gioHang.hinhAnh" class="img-fluid rounded-circle"
                                                    style="width: 90px; height: 90px;" alt="">
                                            </div>
                                        </th>
                                        <td class="py-5">{{ `${gioHang.tenSanPham}, ${gioHang.mauSac},
                                            ${gioHang.kichThuoc}` }}</td>
                                        <td class="py-5">{{ gioHang.giaTien }}</td>
                                        <td class="py-5">{{ gioHang.soLuong }}</td>
                                        <td class="py-5">{{ gioHang.giaTien * gioHang.soLuong }}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">
                                        </th>
                                        <td class="py-5"></td>
                                        <td class="py-5"></td>
                                        <td class="py-5">
                                            <p class="mb-0 text-dark py-3">Tổng tiền</p>
                                        </td>
                                        <td class="py-5">
                                            <div class="py-3 border-bottom border-top">
                                                <p class="mb-0 text-dark" id="tong">{{ gioHangList.reduce((acc, cur) =>
                                                    acc
                                                    +
                                                    cur.giaTien * cur.soLuong, 0) }}</p>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="row g-4 text-center align-items-center justify-content-center pt-4">
                            <button type="button"
                                class="btn border-secondary py-3 px-4 text-uppercase w-100 text-primary"
                                @click="showXacNhan()">Đặt
                                Hàng</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</template>