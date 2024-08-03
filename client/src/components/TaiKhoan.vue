<script setup>
import { useUserStore } from '@/stores/NguoiDungStore';
import axios from 'axios';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
import { onMounted, ref } from 'vue';
const userStore = useUserStore()
const confirm = useConfirm();
const toast = useToast();
let profile = ref({})
const avtLabel = ref('')
let oldPassword = ref('')
let newPassword = ref('')
let confirmPassword = ref('')
async function getUserProfile() {
    let res = await axios.get('http://localhost:8080/user/info/?username=' + userStore.userInfo)
    profile.value = res.data
    avtLabel.value = profile.value.tenNguoiDung[0]
}
async function doiMatKhau() {
    confirm.require({
        header: 'Đổi mật khẩu',
        message: 'Bạn có chắc chắn muốn thay đổi mật khẩu không?',
        icon: 'pi pi-exclamation-triangle',
        rejectProps: {
            label: 'Hủy',
            icon: 'pi pi-times',
        },
        acceptProps: {
            label: 'Đổi',
            icon: 'pi pi-check',
        },
        accept: async () => {
            if (newPassword.value != confirmPassword.value) {
                toast.add({ severity: 'error', summary: 'Lỗi', detail: 'Mật khẩu mới không trùng nhau', life: 3000 })
                return
            }
            if (newPassword.value != '' && oldPassword.value != '' && confirmPassword.value != '') {
                // let updatedProfile = {
                //     username: userStore.userInfo,
                //     matKhauCu: oldPassword.value,
                //     matKhauMoi: newPassword.value
                // }
                try {
                    const res = await axios.patch(`http://localhost:8080/user/info/doi-mat-khau?username=${userStore.userInfo}&matKhauCu=${oldPassword.value}&matKhauMoi=${newPassword.value}`)
                    if (res.status == 200) {
                        toast.add({ severity: 'success', summary: 'Thành công', detail: 'Đổi mật khẩu thành công', life: 3000 })
                        oldPassword.value = ''
                        newPassword.value = ''
                        confirmPassword.value = ''
                    } else {
                        toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Đổi mật khẩu thất bại, hãy kiểm tra lại', life: 3000 })
                    }
                } catch (error) {
                    toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Đổi mật khẩu thất bại, hãy kiểm tra lại', life: 3000 })
                }

            } else {
                toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Vui lý điền đầy đủ thông tin', life: 3000 })
            }
        },
        reject: () => {
            oldPassword.value = ''
            newPassword.value = ''
            confirmPassword.value = ''
        }
    })

}
onMounted(async () => {
    await getUserProfile()
})
</script>
<template>
    <Toast />
    <ConfirmDialog></ConfirmDialog>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="my-5">
                    <h3>Thông Tin Tài Khoản</h3>
                    <hr />
                </div>

                <form class="file-upload">
                    <div class="row mb-5 gx-5">
                        <div class="col-xxl-8 mb-5 mb-xxl-0">
                            <div class="bg-secondary-soft px-4 py-5 rounded">
                                <div class="row g-3">
                                    <h4 class="mb-4 mt-0">Thông tin chung</h4>

                                    <div class="col-md-6">
                                        <label class="form-label">Tên người dùng</label>
                                        <input v-model="profile.tenNguoiDung" disabled type="text"
                                            class="form-control fs-5" placeholder aria-label="Tên người dùng" />
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Email</label>
                                        <input v-model="profile.email" disabled type="text" class="form-control fs-5"
                                            placeholder aria-label="Email" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xxl-4">
                            <div class="bg-secondary-soft px-4 py-5 rounded">
                                <div class="row g-3 ">
                                    <div class="text-center">
                                        <div class="square position-relative display-2 mb-3">
                                            <Avatar style="width: 16rem; height: 16rem; font-size: 11rem;"
                                                :label="avtLabel" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row mb-2 gx-5">
                        <div class="col-xxl-12 mb-5 mb-xxl-0">
                            <div class="bg-secondary-soft px-4 py-5 rounded">
                                <div class="row g-3">
                                    <h4 class="my-4">Đổi Mật Khẩu</h4>

                                    <div class="col-md-6">
                                        <label for="exampleInputPassword1" class="form-label">Mật khẩu cũ *</label>
                                        <input v-model="oldPassword" type="password" class="form-control"
                                            id="exampleInputPassword1" />
                                    </div>

                                    <div class="col-md-6">
                                        <label for="exampleInputPassword2" class="form-label">Mật khẩu mới *</label>
                                        <input v-model="newPassword" type="password" class="form-control"
                                            id="exampleInputPassword2" />
                                    </div>

                                    <div class="col-md-12">
                                        <label for="exampleInputPassword3" class="form-label">Xác nhận mật khẩu
                                            mới *</label>
                                        <input v-model="confirmPassword" type="password" class="form-control"
                                            id="exampleInputPassword3" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="gap-3 d-md-flex justify-content-md-end text-center">
                        <button @click="doiMatKhau()" type="button" class="btn btn-primary btn-lg">
                            Đổi mật khẩu
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>
<style scoped>
body {
    margin-top: 20px;
    color: #9b9ca1;
}

.bg-secondary-soft {
    background-color: rgba(208, 212, 217, 0.1) !important;
}

.rounded {
    border-radius: 5px !important;
}

.py-5 {
    padding-top: 3rem !important;
    padding-bottom: 3rem !important;
}

.px-4 {
    padding-right: 1.5rem !important;
    padding-left: 1.5rem !important;
}

.file-upload .square {
    height: 250px;
    width: 250px;
    margin: auto;
    vertical-align: middle;
    border: 1px solid #e5dfe4;
    background-color: #fff;
    border-radius: 5px;
}

.text-secondary {
    --bs-text-opacity: 1;
    color: rgba(208, 212, 217, 0.5) !important;
}

.btn-success-soft {
    color: #28a745;
    background-color: rgba(40, 167, 69, 0.1);
}

.btn-danger-soft {
    color: #dc3545;
    background-color: rgba(220, 53, 69, 0.1);
}

.form-control {
    display: block;
    width: 100%;
    padding: 0.5rem 1rem;
    font-size: 0.9375rem;
    font-weight: 400;
    line-height: 1.6;
    color: #29292e;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #e5dfe4;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border-radius: 5px;
    -webkit-transition: border-color 0.15s ease-in-out,
        -webkit-box-shadow 0.15s ease-in-out;
    transition: border-color 0.15s ease-in-out,
        -webkit-box-shadow 0.15s ease-in-out;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out,
        -webkit-box-shadow 0.15s ease-in-out;
}
</style>
