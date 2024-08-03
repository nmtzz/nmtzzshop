<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';

let userList = ref([])
async function getAllUser() {
    try {
        const response = await axios.get('http://localhost:8080/admin/quan-ly-user/')
        userList.value = response.data
    } catch (error) {
        console.log(error)
    }
}
onMounted(async () => {
    await getAllUser()
})
</script>
<template>
    <DataTable :value="userList" data-key="tenNguoiDung">
        <template #header>
            <span class="pi pi-users fs-3"></span>
            <h4>Quản Lý User</h4>
        </template>
        <Column sortable field="tenNguoiDung" header="Tên người dùng"></Column>
        <Column sortable field="email" header="Email"></Column>
    </DataTable>
</template>