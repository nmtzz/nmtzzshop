<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
let doanhThuList = ref([])
let donHangList = ref([])
let sanPhamList = ref([])
let chartDoanhThu = ref()
let chartDonHang = ref()
let chartSanPham = ref()
let colorList = ref(['rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)', 'rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)', 'rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)'])
async function getThongKe() {
    let res = await axios.get('http://localhost:8080/admin/thong-ke')
    doanhThuList.value = res.data.doanhThuHangThang
    donHangList.value = res.data.donHangTheoThang
    sanPhamList.value = res.data.sanPhamBanDuocTheoThang
    console.log(doanhThuList.value)
}
function setChartDoanhThu() {
    return {
        labels: doanhThuList.value.map((item) => `Tháng ${item.thang}`),
        datasets: [
            {
                label: 'Doanh thu',
                data: doanhThuList.value.map((item) => item.doanhThu),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1,
                backgroundColor: doanhThuList.value.map((item) => colorList.value[item.thang - 1]),
                borderColor: doanhThuList.value.map((item) => colorList.value[item.thang - 1]),
                borderWidth: 1
            }
        ]
    }
}
function setChartDonHang() {
    return {
        labels: donHangList.value.map((item) => `Tháng ${item.thang}`),
        datasets: [
            {
                label: 'Đơn hàng',
                data: donHangList.value.map((item) => item.soLuong),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1,
                backgroundColor: donHangList.value.map((item) => colorList.value[item.thang - 1]),
                borderColor: donHangList.value.map((item) => colorList.value[item.thang - 1]),
                borderWidth: 1
            }
        ]
    }
}
function setChartSanPham() {
    return {
        labels: sanPhamList.value.map((item) => `Tháng ${item.thang}`),
        datasets: [
            {
                label: 'Sản phẩm',
                data: sanPhamList.value.map((item) => item.soLuong),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1,
                backgroundColor: sanPhamList.value.map((item) => colorList.value[item.thang - 1]),
                borderColor: sanPhamList.value.map((item) => colorList.value[item.thang - 1]),
                borderWidth: 1
            }
        ]
    }
}
onMounted(async () => {
    await getThongKe()
    chartDoanhThu.value = setChartDoanhThu()
    chartDonHang.value = setChartDonHang()
    chartSanPham.value = setChartSanPham()
    console.log(chartDoanhThu.value)
    console.log(chartDonHang.value)
    console.log(chartSanPham.value)
})
</script>
<template>
    <main>
        <div class="row mb-4">
            <div class="col-5 card px-5 mx-5">
                <h3 class="text-center mt-2">Doanh Thu Hàng Tháng</h3>
                <Chart type="bar" :data="chartDoanhThu" />
            </div>
            <div class="col-5 card px-5 mx-5">
                <h3 class="text-center mt-2">Đơn Hàng Hàng Tháng</h3>
                <Chart type="bar" :data="chartDonHang" />
            </div>
        </div>
        <div class="row">
            <div class="col-10 card px-5 mx-5">
                <h3 class="text-center mt-2">Sản Phẩm Bán Được Hàng Tháng</h3>
                <Chart type="bar" :data="chartSanPham" />
            </div>
        </div>
    </main>
</template>