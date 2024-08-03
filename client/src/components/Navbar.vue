<template>
    <div class="card">
        <Menubar :model="items" style="border: none!important;">
            <template #start>
            </template>
            <template #item="{ item, props, hasSubmenu, root }">
                <a v-ripple class="flex items-center" v-bind="props.action">
                    <span :class="item.icon" />
                    <span class="ml-2">{{ item.label }}</span>
                    <span v-if="item.shortcut"
                        class="ml-auto border border-surface rounded bg-emphasis text-muted-color text-xs p-1">{{
                            item.shortcut }}</span>
                    <i v-if="hasSubmenu"
                        :class="['pi pi-angle-down', { 'pi-angle-down ml-2': root, 'pi-angle-right ml-auto': !root }]"></i>
                </a>
            </template>
            <template #end>
                <div class="flex items-center gap-2" v-if="userStore.userInfo">
                    <a v-if="userStore.isAdmin" href="/admin" style="cursor: pointer;"
                        class="flex items-center gap-2 me-3"><i
                            class="fa-duotone fa-solid fa-user-tie me-2"></i>Admin</a>
                    <span @click="router.push('/gio-hang')" style="cursor: pointer;"
                        class="flex items-center gap-2 me-3"><i
                            class="fa-duotone fa-solid fa-cart-shopping me-2"></i>Giỏ
                        hàng</span>
                    <Avatar style="cursor: pointer;" :label="userStore.userInfo[0]" shape="circle" @click="toggle"
                        aria-haspopup="true" aria-controls="overlay_menu" />
                    <Menu ref="menu" id="overlay_menu" :model="options" :popup="true" />
                </div>
                <div v-else>
                    <span @click="router.push('/gio-hang')" style="cursor: pointer;"
                        class="flex items-center gap-2 me-3"><i
                            class="fa-duotone fa-solid fa-cart-shopping me-2"></i>Giỏ
                        hàng</span>
                    <Button @click="router.push('/dang-nhap')" label="Đăng nhập" class="p-button-outlined" />
                </div>
            </template>
        </Menubar>
    </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/NguoiDungStore";
const userStore = useUserStore()
const router = useRouter()
let danhMucList = ref([])
let buildItemDanhMuc = ref([])
async function loadDanhMuc() {
    let res = await axios.get('http://localhost:8080/api/danh-muc/')
    danhMucList.value = res.data
    for (const item of danhMucList.value) {
        buildItemDanhMuc.value.push({
            label: item.tenDanhMuc,
            command: () => {
                location.href = '/san-pham/' + item.id + '/' + item.tenDanhMuc
            }
        })
    }
}

loadDanhMuc()
const items = ref([
    {
        label: 'Trang chủ',
        icon: 'pi pi-home',
        command: () => {
            location.href = '/'
        }
    },
    {
        label: 'Danh mục',
        icon: 'pi pi-star',
        items: buildItemDanhMuc.value
    }
]);
const menu = ref();
const options = ref([
    {
        label: 'Tài khoản',
        items: [
            {
                label: 'Thông tin',
                icon: 'pi pi-user',
                command: () => {
                    router.push('/tai-khoan')
                }
            },
            {
                label: 'Đơn hàng',
                icon: 'pi pi-shopping-bag',
                command: () => {
                    router.push('/don-hang')
                }
            },
            {
                label: 'Đăng xuất',
                icon: 'pi pi-sign-out',
                command: () => {
                    userStore.dangXuat()
                }
            }
        ]
    }
]);

const toggle = (event) => {
    menu.value.toggle(event);
};
</script>

<!-- <style scoped>
* {
    margin: 0 !important;
    padding: 0 !important;
}
</style> -->
