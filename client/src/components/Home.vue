<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router';

const PRODUCTS_API = 'http://localhost:8080/api/san-pham/'
const CATEGORIES_API = 'http://localhost:8080/api/danh-muc/'
const categories = ref([])
const products = ref([])
const selectedCategoryId = ref(0)
const selectedCategoryName = ref('')
const rowsPerPage = ref(8)
const router = useRouter()

onMounted(async () => {
  await fetchProducts()
  await fetchCategories()
})

async function fetchProducts() {
  const { data } = await axios.get(`${PRODUCTS_API}?rownum=${rowsPerPage.value}`)
  products.value = data
}

async function fetchCategories() {
  const { data } = await axios.get(CATEGORIES_API)
  categories.value = data
}

watch(rowsPerPage, fetchProducts)

async function getProductsForCategory(categoryId) {
  const api = `${PRODUCTS_API}?maDanhMuc=${categoryId}`
  const { data } = await axios.get(api)
  products.value = data
  selectedCategoryId.value = categoryId
  selectedCategoryName.value = categories.value.find(category => category.id === categoryId).tenDanhMuc
}
async function routerPush(id) {
  await router.push(`/chi-tiet-san-pham/${id}`)
}
</script>

<template>
  <div class="container-fluid fruite py-5">
    <div class="container py-5">
      <div class="tab-class text-center">
        <div class="row g-4">
          <div class="col-lg-4 text-start">
            <h1>Sản Phẩm</h1>
          </div>
          <div class="col-lg-8 text-end">
            <ul class="nav nav-pills d-inline-flex text-center mb-5">
              <li class="nav-item" @click.prevent="fetchProducts">
                <a class="d-flex m-2 py-2 bg-light rounded-pill" data-bs-toggle="pill" href=""
                  :class="{ active: selectedCategoryId === 0 }"
                  @click="selectedCategoryName = ''; selectedCategoryId = 0">
                  <span class="text-dark" style="width: 130px;">Tất Cả</span>
                </a>
              </li>
              <li v-for="category in categories" :key="category.id" class="nav-item"
                @click="getProductsForCategory(category.id)">
                <a style="cursor: pointer;" class="d-flex py-2 m-2 bg-light rounded-pill" data-bs-toggle="pill"
                  :class="{ active: selectedCategoryId === category.id }">
                  <span class="text-dark" style="width: 130px;">{{ category.tenDanhMuc }}</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="tab-content">
          <div class="tab-pane fade show p-0 active">
            <div class="row g-4">
              <div class="col-lg-12">
                <div class="row g-4">
                  <a v-for="product in products" :key="product.id" :href="`/chi-tiet-san-pham/${product.id}`"
                    class="col-md-6 col-lg-4 col-xl-3">
                    <div class="rounded position-relative fruite-item">
                      <div style="max-height: 205px; min-height: 205px;" class="fruite-img">
                        <img :src="product.hinhAnh" class="img-fluid w-100 rounded-top" alt="" />
                      </div>
                      <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                        <h4>{{ product.tenSanPham }}</h4>
                        <p>
                          {{ product?.moTa?.length > 25 ? product.moTa.substring(0, 25) + '...' : product.moTa }}
                        </p>
                        <div class="d-flex justify-content-between flex-lg-wrap">
                          <p class="text-dark fs-5 fw-bold mb-0">{{ product.giaChung }}&#8363;</p>
                          <a :href="`/chi-tiet-san-pham/${product.id}`"
                            class="btn border border-secondary rounded-pill px-3 text-primary w-50"><i
                              class="fa fa-shopping-bag me-2 text-primary"></i>
                            Mua</a>
                        </div>
                      </div>
                    </div>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="text-center d-sm-flex justify-content-sm-center" v-if="selectedCategoryName">
      <a :href="`/san-pham/${selectedCategoryId}/${selectedCategoryName}`"
        class="btn btn-primary w-25 rounded-pill py-3 px-5 flex-sm-grow-1 flex-lg-grow-0"
        style="background-color: #81c408!important; border-color: #81c408!important;">Xem Tất Cả {{
          selectedCategoryName }}</a>
    </div>
    <div @click="products.length < rowsPerPage ? null : rowsPerPage += 8"
      class="text-center d-sm-flex justify-content-sm-center" v-else>
      <button style="background-color: #81c408!important; border-color: #81c408!important;"
        :disabled="products.length < rowsPerPage"
        class="btn btn-primary w-25 rounded-pill py-3 px-5 flex-sm-grow-1 flex-lg-grow-0">{{ products.length <
          rowsPerPage ? 'Đã hết sản phẩm' : 'Xem Thêm Sản Phẩm' }}</button>
    </div>
  </div>
</template>
