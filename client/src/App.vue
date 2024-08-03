<script setup>

import { RouterView } from 'vue-router';
import Navbar from './components/Navbar.vue';
import { useRoute } from 'vue-router';
const route = useRoute()
</script>

<template>
    <Navbar v-if="route.path !== '/dang-nhap' && route.path !== '/dang-ky' && !route.path.includes('/admin')" />
    <div v-if="route.path !== '/dang-nhap' && route.path !== '/dang-ky' && !route.path.includes('/admin')" class="mb-5">
    </div>
    <RouterView />
</template>