<script setup>
import { useRoute } from 'vue-router';
import { RouterLink } from 'vue-router';
const route = useRoute()
</script>
<template>
    <div class="wrapper d-flex align-items-stretch">
        <nav id="sidebar" class="active">
            <h1><a href="/" class="logo"><i class="fa-regular fa-circle-left"></i></a></h1>
            <ul class="list-unstyled components mb-5">
                <li class="active" :class="{ 'active-link': route.path === '/admin' }">
                    <router-link to="/admin"><span class="fa fa-home"></span> Trang Chủ</router-link>
                </li>
                <li :class="{ 'active-link': route.path === '/admin/don-hang' }">
                    <router-link to="/admin/don-hang"><span class="fa-solid fa-file-invoice"></span> Đơn
                        Hàng</router-link>
                </li>
                <li :class="{ 'active-link': route.path === '/admin/san-pham' }">
                    <router-link to="/admin/san-pham"><span class="pi pi-credit-card"></span> Sản Phẩm</router-link>
                </li>
                <li :class="{ 'active-link': route.path === '/admin/danh-muc' }">
                    <router-link to="/admin/danh-muc"><span class="fa-solid fa-list"></span> Danh Mục</router-link>
                </li>
                <li :class="{ 'active-link': route.path === '/admin/user' }">
                    <router-link to="/admin/user"><span class="fa-solid fa-users"></span> User</router-link>
                </li>
                <li :class="{ 'active-link': route.path === '/admin/thong-ke' }">
                    <router-link to="/admin/thong-ke"><span class="fa-solid fa-chart-simple"></span> Thống
                        Kê</router-link>
                </li>
            </ul>
        </nav>

        <!-- Page Content  -->
        <div id="content" class="p-4 p-md-3">

            <router-view></router-view>
        </div>
    </div>
</template>
<style scoped>
.active-link {
    background-color: #8dbcd4 !important;
}
</style>