<script setup>
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import { ref, watch } from 'vue';
import { useUserStore } from '@/stores/NguoiDungStore';
import { useToast } from "primevue/usetoast";
const toast = useToast();
const userStore = useUserStore()
const route = useRoute();
const router = useRouter()
const idSanPham = route.params.id
let sanPham = ref({})
let mauSacSpList = ref([])
let kichThuocSpList = ref([])
let selectedMauSac = ref('')
let selectedKichThuoc = ref('')
let soLuong = ref(1)
async function loadThongTinSanPham() {
    const response = await axios.get(`http://localhost:8080/api/ctsp/?maSanPham=${idSanPham}`)
    sanPham.value = response.data
    selectedMauSac.value = sanPham.value.mauSac
    selectedKichThuoc.value = sanPham.value.kichThuoc
}
async function loadMauSac() {
    const response = await axios.get(`http://localhost:8080/api/ctsp/mau-sac?maSanPham=${idSanPham}`)
    mauSacSpList.value = response.data
}
async function loadKichThuoc() {
    const response = await axios.get(`http://localhost:8080/api/ctsp/kich-thuoc?maSanPham=${idSanPham}`)
    kichThuocSpList.value = response.data
}
async function updateSanPham() {
    try {
        const api = `http://localhost:8080/api/ctsp/chi-tiet?maSanPham=${idSanPham}&mauSac=${selectedMauSac.value}&kichThuoc=${selectedKichThuoc.value}`
        const response = await axios.get(api)
        sanPham.value.id = response.data.id
        sanPham.value.giaTien = response.data.giaTien
        sanPham.value.soLuongTonKho = response.data.soLuongTonKho
    } catch (error) {
        return;
    }
}
let nhanXetList = ref([])
let pageNhanXet = ref(0)
async function loadNhanXet() {
    const resp = await axios.get(`http://localhost:8080/api/nhan-xet/?maSanPham=${idSanPham}&page=${pageNhanXet.value}`)
    nhanXetList.value = resp.data
}
let formNhanXet = ref({});
async function dangNhanXet() {
    formNhanXet.value.tenUsers = { tenNguoiDung: userStore.userInfo }
    formNhanXet.value.maSanPham = { id: idSanPham }
    const resp = await axios.post('http://localhost:8080/api/nhan-xet/', formNhanXet.value)
    if (resp.status === 201) {
        toast.add({ severity: 'success', summary: 'Thành công', detail: 'Đã nhận xét', life: 3000 })
        formNhanXet.value.noiDungNhanXet = ''
        await loadNhanXet()
    }
}

let postGioHang = ref({})
async function doPostGioHang() {
    if (!userStore.userInfo) {
        await router.push('/dang-nhap')
    }
    postGioHang.value.tenUsers = { tenNguoiDung: userStore.userInfo }
    postGioHang.value.chiTietSanPham = { id: sanPham.value.id }
    postGioHang.value.soLuong = soLuong.value
    const resp = await axios.post('http://localhost:8080/api/gio-hang/', postGioHang.value)
    if (resp.status === 201) {
        toast.add({ severity: 'success', summary: 'Thành công', detail: 'Đã thêm vào giỏ hàng', life: 3000 })
    }
}
loadNhanXet()
watch(selectedMauSac, updateSanPham)
watch(selectedKichThuoc, updateSanPham)
loadThongTinSanPham()
loadMauSac()
loadKichThuoc()
</script>
<template>
    <Toast />
    <div class="container-fluid py-5 mt-5">
        <div class="container py-5">
            <div class="row g-4 mb-5">
                <div class="">
                    <div class="row g-4">
                        <div class="col-lg-6">
                            <div class="border rounded me-5">
                                <a href="#">
                                    <img :src="sanPham.hinhAnh" width="100%" class="img-fluid rounded" alt="Image">
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="mt-md-5 mt-sm-2"></div>
                            <h4 class="fw-bold mb-3">{{ sanPham.tenSanPham }}</h4>
                            <p class="mb-3">Danh mục: {{ sanPham.tenDanhMuc }}</p>
                            <h5 class="fw-bold mb-3" v-if="sanPham.giaTien">{{ sanPham.giaTien }}&#8363;</h5>
                            <h5 class="fw-bold mb-3" v-else>Chưa có giá</h5>
                            <h5 class="">Màu</h5>
                            <div class="list-mau-sac d-flex flex-row gap-3 mb-2">
                                <button v-for="mauSac in mauSacSpList"
                                    class="btn border border-secondary rounded-pill px-4 py-2 mb-3 text-primary"
                                    :class="{ active: selectedMauSac == mauSac }" @click="selectedMauSac = mauSac">{{
                                        mauSac
                                    }}</button>
                            </div>
                            <h5>Kích thước</h5>
                            <div class="list-kich-thuoc d-flex flex-row gap-3 mb-2">
                                <button v-for="kichThuoc in kichThuocSpList"
                                    class="btn border border-secondary rounded-pill px-4 py-2 mb-3 text-primary"
                                    :class="{ active: selectedKichThuoc == kichThuoc }"
                                    @click="selectedKichThuoc = kichThuoc">{{
                                        kichThuoc
                                    }}
                                </button>
                            </div>
                            <div class="input-group quantity mb-2" style="width: 100px;">
                                <div class="input-group-btn" @click="soLuong > 1 ? soLuong-- : null">
                                    <button class="btn btn-sm btn-minus rounded-circle bg-light border">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                                <input type="text" class="form-control form-control-sm text-center border-0"
                                    v-model="soLuong">
                                <div class="input-group-btn" @click="soLuong++">
                                    <button class="btn btn-sm btn-plus rounded-circle bg-light border">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="hien-thi-ton-kho mb-5">
                                <p class="mb-0 text-secondary">Hiện còn: <span class="fw-bold text-danger">{{
                                    sanPham.soLuongTonKho || 0 }}</span>
                                    sản phẩm</p>
                            </div>
                            <button v-if="sanPham.soLuongTonKho && sanPham.giaTien && sanPham.soLuongTonKho > 0"
                                class="btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary"
                                @click="doPostGioHang()"><i class="fa fa-shopping-bag me-2 text-primary"></i> Thêm vào
                                giỏ hàng</button>
                            <a v-if="sanPham.soLuongTonKho && sanPham.giaTien && sanPham.soLuongTonKho > 0"
                                class="btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary ms-2"
                                :href="`/dat-hang?sp=${sanPham.id}&soLuong=${soLuong}`"><i
                                    class="fa fa-shopping-bag me-2 text-primary"></i> Mua ngay</a>
                            <div v-if="!sanPham.soLuongTonKho || !sanPham.giaTien" class="mb-5 text-secondary fw-bold">
                                <p class="text-danger">Hãy chọn màu khác hoặc kich thước khác</p>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="card">
                                <Tabs value="0" :lazy="true">
                                    <TabList>
                                        <Tab value="0">Mô tả sản phẩm</Tab>
                                        <Tab value="1">Nhận xét, đánh giá</Tab>
                                    </TabList>
                                    <TabPanels>
                                        <TabPanel value="0">
                                            <p class="m-0">
                                                {{ sanPham.moTa }}
                                            </p>
                                        </TabPanel>
                                        <TabPanel value="1">
                                            <div class="d-flex" v-for="nhanXet in nhanXetList">
                                                <Avatar class="me-3" size="large" shape="circle"
                                                    :label="nhanXet.nguoiNhanXet[0].toUpperCase()" />
                                                <div class="">
                                                    <p class="mb-2" style="font-size: 14px;">{{ nhanXet.thoiGianNhanXet
                                                        }}</p>
                                                    <div class="d-flex justify-content-between">
                                                        <h5>{{ nhanXet.nguoiNhanXet }}</h5>
                                                    </div>
                                                    <p>
                                                        {{ nhanXet.noiDungNhanXet }}
                                                    </p>
                                                </div>
                                            </div>
                                            <div>
                                                <Paginator :rows="1" :totalRecords="nhanXetList.length / 5"
                                                    v-model:first="pageNhanXet">
                                                </Paginator>
                                            </div>
                                        </TabPanel>
                                    </TabPanels>
                                </Tabs>
                            </div>
                        </div>
                        <form action="#">
                            <h4 class="mb-2 fw-bold">Để lại nhận xét</h4>
                            <div class="row g-4">
                                <div class="col-lg-12">
                                    <div class="border-bottom rounded my-4">
                                        <textarea v-model="formNhanXet.noiDungNhanXet"
                                            :disabled="userStore.userInfo == null" name="" id=""
                                            class="form-control border-0" cols="30" rows="8"
                                            placeholder="Nhập nhận xét của bạn *"></textarea>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="d-flex justify-content-between pb-3 mb-5">
                                        <a href="/dang-nhap" v-if="userStore.userInfo == null"
                                            class="btn border border-secondary text-primary rounded-pill px-4 py-3">
                                            Đăng nhập để nhận xét
                                        </a>
                                        <button @click.prevent="dangNhanXet" v-if="userStore.userInfo != null"
                                            class="btn border border-secondary text-primary rounded-pill px-4 py-3">Đăng
                                            nhận xét</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.active {
    background-color: #ffb524 !important;
    color: white !important;
}
</style>