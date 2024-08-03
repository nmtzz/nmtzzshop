<script setup>
import axios from 'axios';
import { useConfirm } from 'primevue/useconfirm';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
let dataThongKe = ref({
    "donHangTheoThang": [
        {
            "thang": 7
        }
    ],
    "sanPhamBanDuocTheoThang": [
        {
            "thang": 7,
            "soLuong": 11
        }
    ],
    "tongKhachHang": {
        "soLuong": 9
    },
    "sanPhamBanDuocTrongNgay": {
        "soLuong": 0
    },
    "doanhThuHangThang": [],
    "doanhThuThangHienTai": { "doanhThu": 0 },
    "donHangTrongNgay": {
        "soLuong": 2
    }
})
let colorList = ref(['rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)', 'rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)', 'rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)'])
function setDoanhThuTheoThangChartData() {
    return {
        labels: dataThongKe.value.doanhThuHangThang.map((item) => `Tháng ${item.thang}`),
        datasets: [
            {
                label: 'Doanh thu',
                data: dataThongKe.value.doanhThuHangThang.map((item) => item.doanhThu),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1,
                backgroundColor: dataThongKe.value.doanhThuHangThang.map((item) => colorList.value[item.thang - 1]),
                borderColor: dataThongKe.value.doanhThuHangThang.map((item) => colorList.value[item.thang - 1]),
                borderWidth: 1
            }
        ]
    }
}
let chartData = ref()
let chartOptions = ref()
const confirm = useConfirm();
const router = useRouter();
async function getThongKe() {
    let res = await axios.get('http://localhost:8080/admin/thong-ke')
    dataThongKe.value = res.data
}
let hoaDonChoHuyList = ref([])
async function getHoaDonChoHuy() {
    let res = await axios.get('http://localhost:8080/admin/cho-huy')
    hoaDonChoHuyList.value = res.data
    console.log(hoaDonChoHuyList.value)
}
onMounted(async () => {
    await getThongKe()
    chartData.value = setDoanhThuTheoThangChartData()
    await getHoaDonChoHuy()
    if (hoaDonChoHuyList.value.length > 0) {
        confirm.require({
            message: `Bạn có ${hoaDonChoHuyList.value.length} đơn hàng đang chờ hủy`,
            header: 'Thông Báo',
            icon: 'pi pi-info-circle',
            rejectProps: {
                label: 'Đóng',
                severity: 'secondary',
                outlined: true
            },
            acceptProps: {
                label: 'Xem'
            },
            accept: () => {
                router.push('/admin/don-hang')
            }
        });
    }
})
</script>
<template>
    <ConfirmDialog></ConfirmDialog>
    <div class="bg-light px-4 py-5 md:px-6 lg:px-8">
        <div class="row">
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm p-3 rounded">
                    <div class="d-flex justify-content-between mb-3">
                        <div>
                            <span class="d-block text-muted mb-3">Đơn Hàng</span>
                            <div class="text-dark font-weight-medium h4">{{ dataThongKe.donHangTrongNgay.soLuong }}
                            </div>
                        </div>
                        <div class="d-flex align-items-center justify-content-center bg-primary rounded-circle"
                            style="width:2.5rem;height:2.5rem">
                            <i class="pi pi-shopping-cart text-primary h4 m-0"></i>
                        </div>
                    </div>
                    <span class="text-muted">Trong ngày</span>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm p-3 rounded">
                    <div class="d-flex justify-content-between mb-3">
                        <div>
                            <span class="d-block text-muted mb-3">Tổng Doanh Thu</span>
                            <div class="text-dark font-weight-medium h4">${{ dataThongKe.doanhThuThangHienTai.doanhThu
                                }}</div>
                        </div>
                        <div class="d-flex align-items-center justify-content-center bg-warning rounded-circle"
                            style="width:2.5rem;height:2.5rem">
                            <i class="pi pi-map-marker text-warning h4 m-0"></i>
                        </div>
                    </div>
                    <span class="text-muted">Trong tháng</span>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm p-3 rounded">
                    <div class="d-flex justify-content-between mb-3">
                        <div>
                            <span class="d-block text-muted mb-3">Khách Hàng</span>
                            <div class="text-dark font-weight-medium h4">{{ dataThongKe.tongKhachHang.soLuong }}</div>
                        </div>
                        <div class="d-flex align-items-center justify-content-center bg-info rounded-circle"
                            style="width:2.5rem;height:2.5rem">
                            <i class="pi pi-inbox text-info h4 m-0"></i>
                        </div>
                    </div>
                    <span class="text-muted">Đã đăng ký</span>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm p-3 rounded">
                    <div class="d-flex justify-content-between mb-3">
                        <div>
                            <span class="d-block text-muted mb-3">Sản Phẩm Bán Được</span>
                            <div class="text-dark font-weight-medium h4">{{
                                dataThongKe?.sanPhamBanDuocTrongNgay?.soLuong || 0
                            }}</div>
                        </div>
                        <div class="d-flex align-items-center justify-content-center bg-secondary rounded-circle"
                            style="width:2.5rem;height:2.5rem">
                            <i class="pi pi-comment text-secondary h4 m-0"></i>
                        </div>
                    </div>
                    <span class="text-muted">Trong ngày</span>
                </div>
            </div>
        </div>
    </div>
    <Chart style="max-width: 1405px;" type="bar" :data="chartData" :chartOptions="chartOptions" />
</template>