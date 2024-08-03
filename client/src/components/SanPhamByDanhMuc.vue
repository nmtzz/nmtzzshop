<script setup>
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';
const router = useRouter()
const route = useRoute()
const PRODUCTS_API = 'http://localhost:8080/api/san-pham/page'
const products = ref([])
let idDanhMuc = ref(route.params.id)
let tenDanhMuc = ref(route.params.name)
let currentPage = route.query.page ? ref(parseInt(route.query.page)) : ref(0)
let totalRecords = ref(0)
let giaCaoNhat = ref(0)
let sapXep = ref(0)
async function getGiaCaoNhat() {
    const api = "http://localhost:8080/api/san-pham/gia-cao-nhat"
    const response = await axios.get(`${api}?maDanhMuc=${idDanhMuc.value}`)
    giaCaoNhat.value = response.data
}
let danhMuc = ref({})
let tenDanhMuc2 = ref([])
async function getDanhMuc(idDanhMuc) {
    let res = await axios.get(`http://localhost:8080/api/danh-muc/get?id=${idDanhMuc}`)
    danhMuc.value = res.data
    tenDanhMuc2.value = danhMuc.value.tenDanhMuc.split(" ")
}
getGiaCaoNhat()
async function fetchProducts() {
    const response = await axios.get(`${PRODUCTS_API}?maDanhMuc=${idDanhMuc.value}&page=${currentPage.value}`)
    products.value = response.data.content
    totalRecords.value = response.data.totalElements
}
let keyword = ref(route.query.keyword)
async function timKiemSanPham() {
    if (keyword.value == "") {
        router.push("/san-pham/" + idDanhMuc.value + "/" + tenDanhMuc.value)
        fetchProducts()
        return
    }
    const api = "http://localhost:8080/api/san-pham/tim-kiem"
    const response = await axios.get(`${api}?keyword=${keyword.value}&maDanhMuc=${idDanhMuc.value}&page=${currentPage.value}`)
    products.value = response.data.content
    totalRecords.value = response.data.totalElements
    router.push("/san-pham/" + idDanhMuc.value + "/" + tenDanhMuc.value + "?page=" + currentPage.value + "&keyword=" + keyword.value)
}
// fetchProducts()
onMounted(async () => {
    if (keyword.value) {
        timKiemSanPham()
    } else {
        fetchProducts()
    }
    await getDanhMuc(idDanhMuc.value)
})
watch(currentPage, () => {
    if (keyword.value) {
        router.push("/san-pham/" + idDanhMuc.value + "/" + tenDanhMuc.value + "?page=" + currentPage.value + "&keyword=" + keyword.value)
        timKiemSanPham()
    } else {
        router.push("/san-pham/" + idDanhMuc.value + "/" + tenDanhMuc.value + "?page=" + currentPage.value)
        fetchProducts()
    }
    // router.push("/san-pham/" + idDanhMuc.value + "/" + tenDanhMuc.value + "?page=" + currentPage.value)
    // fetchProducts()
})
let sortedProducts = computed(() => {
    if (sapXep.value == 0) {
        return products.value
    } else if (sapXep.value == 1) {
        return products.value.sort((a, b) => a.giaChung - b.giaChung)
    } else if (sapXep.value == 2) {
        return products.value.sort((a, b) => b.giaChung - a.giaChung)
    }
})
</script>

<template>
    <!-- Fruits Shop Start-->
    <div class="container-fluid fruite py-5">
        <div class="container py-5">
            <h1 class="mb-4">{{ tenDanhMuc }}</h1>
            <div class="row g-4">
                <div class="col-lg-12">
                    <div class="row g-4">
                        <div class="col-xl-3">
                            <div class="input-group w-100 mx-auto d-flex">
                                <input v-model="keyword" type="search" class="form-control p-3" placeholder="Tìm kiếm"
                                    aria-describedby="search-icon-1">
                                <span @click="timKiemSanPham" id="search-icon-1" class="input-group-text p-3"><i
                                        class="fa fa-search"></i></span>
                            </div>
                        </div>
                        <div class="col-6"></div>
                    </div>
                    <div class="row g-4 mt-2">
                        <div class="col-lg-3">
                            <div class="row g-4">
                                <div class="col-lg-12">
                                    <div class="mb-3">
                                        <h4>Sắp xếp</h4>
                                        <div class="mb-2" @click="keyword ? timKiemSanPham() : fetchProducts()">
                                            <input v-model="sapXep" type="radio" class="me-2" id="sapXep-0" value="0"
                                                checked>
                                            <label for="sapXep-0"> Mặc định</label>
                                        </div>
                                        <div class="mb-2">
                                            <input v-model="sapXep" type="radio" class="me-2" id="sapXep-1" value="1">
                                            <label for="sapXep-1"> Giá tăng dần</label>
                                        </div>
                                        <div class="mb-2">
                                            <input v-model="sapXep" type="radio" class="me-2" id="sapXep-2" value="2">
                                            <label for="sapXep-2"> Giá giảm dần</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="position-relative">
                                        <img :src="danhMuc.anhDanhMuc" class="img-fluid w-100 rounded" alt="">
                                        <div class="position-absolute"
                                            style="top: 50%; right: 10px; transform: translateY(-50%);">
                                            <div class="d-flex flex-column text-uppercase">
                                                <h3 v-for="word in tenDanhMuc2" class="text-secondary fw-bold fs-1">{{
                                                    word
                                                }}</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-9">
                            <div class="row g-4">
                                <a style="cursor: pointer;" class="col-md-6 col-lg-6 col-xl-4"
                                    v-for="product in sortedProducts" :href="`/chi-tiet-san-pham/${product.id}`">
                                    <div class="rounded position-relative fruite-item">
                                        <div class="fruite-img">
                                            <img :src="product.hinhAnh" class="img-fluid w-100 rounded-top" alt="">
                                        </div>
                                        <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                            <h4>{{ product.tenSanPham }}</h4>
                                            <p>{{ product?.moTa?.length > 25 ? product.moTa.substring(0, 25) + '...' :
                                                product.moTa }}</p>
                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                <p class="text-dark fs-5 fw-bold mb-0">{{ product.giaChung }}&#8363;</p>
                                                <a :href="`/chi-tiet-san-pham/${product.id}`"
                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                        class="fa fa-shopping-bag me-2 text-primary"></i> Mua</a>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div>
                            <Paginator :rows="1" :totalRecords="totalRecords / 6" v-model:first="currentPage">
                            </Paginator>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Fruits Shop End-->
</template>