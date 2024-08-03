<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/NguoiDungStore';
import { onMounted, ref } from 'vue';
import { FilterMatchMode } from '@primevue/core/api';
import { useRoute, useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
const userStore = useUserStore()
const route = useRoute()
const router = useRouter()
const toast = useToast();
const confirm = useConfirm();
let hd = ref(route.query.hd)
let s = ref(route.query.s)
let hoaDon = ref({})
let chiTietHoaDon = ref([])
let showDialog = ref(false)
async function getInfo() {
    const resp1 = await axios.get(`http://localhost:8080/user/don-hang/hd?id=${hd.value}`)
    hoaDon.value = resp1.data
    const resp2 = await axios.get(`http://localhost:8080/user/don-hang/cthd?id=${hd.value}`)
    chiTietHoaDon.value = resp2.data
    showDialog.value = true
}
let donHangList = ref([])
async function doGetDonHang() {
    const resp = await axios.get(`http://localhost:8080/user/don-hang/?user=${userStore.userInfo}`)
    donHangList.value = resp.data

}
async function huyDonHang(id) {
    confirm.require({
        header: 'Yêu cầu hủy?',
        message: 'Đơn hàng này sẽ được admin xác nhận hủy',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Đóng',
        rejectProps: {
            label: 'Cancel',
            severity: 'secondary',
            outlined: true
        },
        acceptProps: {
            label: 'Xác nhận',
            severity: 'danger'
        },
        accept: async () => {
            const resp = await axios.patch(`http://localhost:8080/user/don-hang/huy?id=${id}`)
            if (resp.status == 200) {
                await doGetDonHang()
                showDialog = false
                confirm.require({
                    header: 'Thành công',
                    message: 'Đơn hàng đã được yêu cầu huỷ',
                    icon: 'pi pi-info-circle',
                    rejectLabel: 'Đóng',
                    rejectProps: {
                        label: 'Cancel',
                        severity: 'secondary',
                        outlined: true
                    },
                    acceptProps: {
                        label: 'Xác nhận',
                        severity: 'danger'
                    },
                    accept: () => {
                        if (s.value) {
                            location.href = '/don-hang'
                        }
                        location.href = '/don-hang'
                    },
                    reject: () => {
                        if (s.value) {
                            location.href = '/don-hang'
                        }
                        location.href = '/don-hang'
                    }
                })

            }
        }
    });
}
function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}`;
}
function isMoreThanFiveMinutesAgo(dateTimeString) {
    const date = new Date(dateTimeString);
    const now = new Date();

    const diffInMinutes = (now - date) / (1000 * 60);

    return diffInMinutes > 5;
}
const getSeverity = (status) => {
    switch (status) {
        case 'Đã đặt':
            return 'success';

        case 'Chờ hủy':
            return 'warn';

        case 'Đã hủy':
            return 'danger';

        default:
            return 'info';
    }
};
let statuses = ref(['Đã đặt', 'Chờ hủy', 'Đã hủy'])
const filter = ref({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    trangThai: { value: null, matchMode: FilterMatchMode.EQUALS }
})
const loading = ref(true);
onMounted(async () => {
    await doGetDonHang()
    loading.value = false
    if (hd.value) {
        await getInfo()
    }
})
</script>
<template>
    <ConfirmDialog></ConfirmDialog>
    <Toast />
    <Dialog modal v-model:visible="showDialog">
        <template #header>
            <div v-if="s !== null && s !== undefined"
                class="d-inline-flex align-items-center justify-content-center gap-2">
                <Avatar style="color: green;" icon="pi pi-check" shape="circle" />
                <span class="font-bold whitespace-nowrap">Đặt hàng thành công, đơn hàng {{ hoaDon.id }}</span>
            </div>
            <div v-else class="d-inline-flex align-items-center justify-content-center gap-2">
                <Avatar style="color: #9C27B0;" icon="pi pi-info-circle" shape="circle" />
                <span class="font-bold whitespace-nowrap">{{ `Đơn hàng ${hoaDon.id}` }}</span>
            </div>
        </template>
        <div class="row">
            <div class="col-6 d-flex flex-column">
                <label for="tenNguoiNhan">Người nhận hàng</label>
                <InputText disabled id="tenNguoiNhan" v-model="hoaDon.tenNguoiNhan" />
            </div>
            <div class="col-6 d-flex flex-column">
                <label for="diaChiGiaoHang">Địa chỉ giao hàng</label>
                <InputText disabled id="diaChiGiaoHang" v-model="hoaDon.diaChiGiaoHang" />
            </div>
            <div class="col-6 d-flex flex-column mt-2">
                <label for="soDienThoai">SDT</label>
                <InputText disabled id="soDienThoai" v-model="hoaDon.soDienThoai" />
            </div>
            <div class="col-6 d-flex flex-column mt-2">
                <label for="ghiChu">Ghi chú</label>
                <InputText disabled id="ghiChu" v-model="hoaDon.ghiChu" />
            </div>
        </div>
        <DataTable :value="chiTietHoaDon">
            <Column field="tenSanPham" header="Sản phẩm"></Column>
            <Column header="Ảnh">
                <template #body="slotProps">
                    <Avatar size="xlarge" :image="slotProps.data.hinhAnh"
                        style="background-color: #e72734; color: white;" />
                </template>
            </Column>
            <Column field="kichThuoc" header="Kích thước"></Column>
            <Column field="mauSac" header="Màu sắc"></Column>
            <Column field="giaTien" header="Giá"></Column>
            <Column field="soLuong" header="Số lượng"></Column>
        </DataTable>
        <template #footer>
            <Button v-if="s != null || s != undefined"
                :style="isMoreThanFiveMinutesAgo(hoaDon.ngayLapHoaDon) || hoaDon.trangThai == 'Chờ hủy' || hoaDon.trangThai == 'Đã hủy' ? 'cursor: not-allowed' : 'cursor: pointer;'"
                :disabled="isMoreThanFiveMinutesAgo(hoaDon.ngayLapHoaDon) || hoaDon.trangThai == 'Chờ hủy' || hoaDon.trangThai == 'Đã hủy'"
                class="ms-2" type="button" @click="huyDonHang(hoaDon.id)" severity="danger" label="Hủy Đơn" />
            <a v-if="s !== null && s !== undefined" href="/don-hang">
                <Button label="Đóng" outlined severity="secondary" autofocus />
            </a>
            <Button v-if="s === null || s === undefined"
                :style="isMoreThanFiveMinutesAgo(hoaDon.ngayLapHoaDon) || hoaDon.trangThai == 'Chờ hủy' || hoaDon.trangThai == 'Đã hủy' ? 'cursor: not-allowed' : 'cursor: pointer;'"
                :disabled="isMoreThanFiveMinutesAgo(hoaDon.ngayLapHoaDon) || hoaDon.trangThai == 'Chờ hủy' || hoaDon.trangThai == 'Đã hủy'"
                class="ms-2" type="button" @click="huyDonHang(hoaDon.id); showDialog = false" severity="danger"
                label="Hủy Đơn" />
            <Button v-if="s === null || s === undefined" label="Đóng" outlined severity="secondary"
                @click="showDialog = false" autofocus />
        </template>
    </Dialog>
    <div class="container-fluid">
        <DataTable removableSort v-model:filters="filter" :value="donHangList" paginator :rows="10" dataKey="id"
            filterDisplay="menu" :loading="loading"
            :globalFilterFields="['id', 'ngayLapHoaDon', 'tongTien', 'trangThai']">
            <template #header>
                <div class="d-flex justify-content-between">
                    <span class="fw-bold fs-3">Danh sách đơn hàng</span>
                    <IconField>
                        <InputIcon>
                            <i class="pi pi-search" />
                        </InputIcon>
                        <InputText v-model="filter['global'].value" placeholder="Tìm kiếm" />
                    </IconField>
                </div>
            </template>
            <template #empty> Không có đơn hàng nào. </template>
            <template #loading> Đang tải... </template>
            <Column field="id" header="Mã đơn hàng" sortable></Column>
            <Column field="hinhAnhSanPhamList" header="Sản phẩm" sortable>
                <template #body="{ data }">
                    <AvatarGroup>
                        <Avatar v-for="item in data.hinhAnhSanPhamList.slice(0, 3)" :image="item" shape="circle"
                            size="xlarge" />
                        <Avatar v-if="data.hinhAnhSanPhamList.length > 3" shape="circle" size="xlarge"
                            :label="'+' + (data.hinhAnhSanPhamList.length - 3)" />
                    </AvatarGroup>
                </template>
            </Column>
            <Column field="ngayLapHoaDon" dataType="date" header="Ngày tạo" sortable>
                <template #body="{ data }">
                    {{ formatDateTime(data.ngayLapHoaDon) }}
                </template>
            </Column>
            <Column field="tongTien" header="Tổng tiền" sortable></Column>
            <Column field="trangThai" header="Trạng thái" sortable>
                <template #body="{ data }">
                    <Tag :value="data.trangThai" :severity="getSeverity(data.trangThai)" />
                </template>
                <template #filter="{ filterModel, filterCallback }">
                    <Select v-model="filterModel.value" @change="filterCallback()" :options="statuses"
                        placeholder="Lọc trạng thái" style="min-width: 12rem" :showClear="true">
                        <template #option="slotProps">
                            <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                        </template>
                    </Select>
                </template>
            </Column>
            <Column field="['ngayLapHoaDon', 'id', 'trangThai']" dataType="date">
                <template #body="{ data }">
                    <Button @click="hd = data.id; getInfo()" type="button" severity="info" label="Chi Tiết" />
                    <Button
                        :style="isMoreThanFiveMinutesAgo(data.ngayLapHoaDon) || data.trangThai == 'Chờ hủy' || data.trangThai == 'Đã hủy' ? 'cursor: not-allowed' : 'cursor: pointer;'"
                        :disabled="isMoreThanFiveMinutesAgo(data.ngayLapHoaDon) || data.trangThai == 'Chờ hủy' || data.trangThai == 'Đã hủy'"
                        class="ms-2" type="button" @click="huyDonHang(data.id)" severity="danger" label="Hủy Đơn" />
                </template>
            </Column>

        </DataTable>
    </div>
</template>