<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { usePrimeVue } from 'primevue/config';
import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
const $primevue = usePrimeVue();
let danhMucList = ref([])
let dialogVisible = ref(false)
let danhMuc = ref({})
const totalSize = ref(0);
const totalSizePercent = ref(0);
const confirm = useConfirm();
const files = ref([]);
const toast = useToast();
const onRemoveTemplatingFile = (file, removeFileCallback, index) => {
    removeFileCallback(index);
    totalSize.value -= parseInt(formatSize(file.size));
    totalSizePercent.value = totalSize.value / 10;
};


const onSelectedFiles = (event) => {
    files.value = event.files;
    files.value.forEach((file) => {
        totalSize.value += parseInt(formatSize(file.size));
    });
};

const uploadEvent = (callback) => {
    totalSizePercent.value = totalSize.value / 10;
    callback();
};


const formatSize = (bytes) => {
    const k = 1024;
    const dm = 3;
    const sizes = $primevue.config.locale.fileSizeTypes;

    if (bytes === 0) {
        return `0 ${sizes[0]}`;
    }

    const i = Math.floor(Math.log(bytes) / Math.log(k));
    const formattedSize = parseFloat((bytes / Math.pow(k, i)).toFixed(dm));

    return `${formattedSize} ${sizes[i]}`;
};
async function getAllDanhMuc() {
    let res = await axios.get('http://localhost:8080/admin/danh-muc/')
    danhMucList.value = res.data
}
async function themDanhMuc() {

    let formData = new FormData();
    if (danhMuc.value.id !== undefined && danhMuc.value.id !== null) {
        formData.append("id", danhMuc.value.id);
    }
    formData.append("anhDanhMuc", danhMuc?.value?.anhDanhMuc ? danhMuc.value.anhDanhMuc : null);
    formData.append("tenDanhMuc", danhMuc?.value?.tenDanhMuc ? danhMuc.value.tenDanhMuc : null);
    formData.append("file", files?.value?.length > 0 ? files.value[0] : null);
    try {
        let res = await axios.post("http://localhost:8080/admin/danh-muc/them-danh-muc", formData);
        if (res.status == 201) {
            await getAllDanhMuc()
            files.value = null
            toast.add({ severity: 'success', summary: 'Thành công', detail: danhMuc.value.id ? 'Đã cập nhật danh mục' : 'Đã thêm danh mục', life: 3000 })
            dialogVisible.value = false
        }
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Thất bại, hãy thử lại', life: 3000 })
    }
}
async function getDanhMucById(id) {
    try {
        let res = await axios.get(`http://localhost:8080/admin/danh-muc/tim?id=${id}`)
        danhMuc.value = res.data
        dialogVisible.value = true
        console.log(danhMuc.value)
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Có lỗi xảy ra', life: 3000 })
    }
}
async function xoaDanhMucById(id) {
    confirm.require({
        message: 'Bạn có muốn xóa danh mục ?',
        header: 'Xoá danh mục',
        icon: 'pi pi-info-circle',
        acceptProps: {
            label: 'Xoá',
            severity: 'danger',
            outlined: true
        },
        rejectProps: {
            label: 'Hủy',
            severity: 'secondary',
        },
        accept: async () => {
            try {
                let res = await axios.delete(`http://localhost:8080/admin/danh-muc/xoa?id=${id}`)
                if (res.status == 200) {
                    await getAllDanhMuc()
                    toast.add({ severity: 'success', summary: 'Thành công', detail: 'Đã xóa danh mục', life: 3000 })
                }
            } catch (error) {
                toast.add({ severity: 'error', summary: 'Thất bại', detail: 'Thất bại, hãy thử lại', life: 3000 })
            }
        },
    })
}
onMounted(async () => {
    await getAllDanhMuc()
})
</script>

<template>
    <Toast />
    <ConfirmDialog></ConfirmDialog>
    <Dialog @after-hide="function () { danhMuc = {} }" header="Danh Mục" maximizable modal
        v-model:visible="dialogVisible" style="width: 60vw;">
        <main>
            <div v-if="danhMuc.anhDanhMuc" class="d-flex justify-content-center mb-3">
                <img class="rounded-circle" style="max-width: 300px; max-height: 200px;" :src="danhMuc.anhDanhMuc"
                    alt="Image">
            </div>
            <div class="d-flex flex-column">
                <div class="input mb-2">
                    <div class="row">
                        <div class="col-3 mt-2">
                            <p>Tên Danh Mục</p>
                        </div>
                        <div class="col-9">
                            <InputText style="width: 100%;" v-model="danhMuc.tenDanhMuc" type="text"
                                placeholder="Tên danh mục" />
                        </div>
                    </div>
                </div>
                <div class="file-upload">
                    <FileUpload :multiple="false" accept="image/*" :maxFileSize="1000000" @select="onSelectedFiles">
                        <template #header="{ chooseCallback, uploadCallback, clearCallback, files }">
                            <div class="d-flex flex-wrap justify-content-between align-items-center flex-1 gap-4">
                                <div class="d-flex gap-2">
                                    <Button @click="chooseCallback()" icon="pi pi-images"
                                        class="btn btn-outline-secondary rounded-pill"></Button>
                                    <Button @click="clearCallback()" icon="pi pi-times"
                                        class="btn btn-outline-danger rounded-pill"
                                        :disabled="!files || files.length === 0"></Button>
                                </div>
                                <ProgressBar :value="totalSizePercent" :showValue="false" class="w-100 h-1 ml-md-auto">
                                    <span class="text-nowrap">{{ totalSize }}B / 1Mb</span>
                                </ProgressBar>
                            </div>
                        </template>
                        <template #content="{ files, uploadedFiles, removeUploadedFileCallback, removeFileCallback }">
                            <div class="d-flex flex-column gap-4 pt-4">
                                <div v-if="files.length > 0">
                                    <div class="d-flex flex-wrap gap-4">
                                        <div v-for="(file, index) of files" :key="file.name + file.type + file.size"
                                            class="p-3 border rounded d-flex flex-column align-items-center gap-3">
                                            <div>
                                                <img role="presentation" :alt="file.name" :src="file.objectURL"
                                                    width="100" height="50" />
                                            </div>
                                            <span class="font-weight-bold text-truncate" style="max-width: 60px;">{{
                                                file.name }}</span>
                                            <div>{{ formatSize(file.size) }}</div>
                                            <Button icon="pi pi-times"
                                                @click="onRemoveTemplatingFile(file, removeFileCallback, index)"
                                                class="btn btn-outline-danger rounded-pill" />
                                        </div>
                                    </div>
                                </div>

                                <div v-if="uploadedFiles.length > 0">
                                    <div class="d-flex flex-wrap gap-4">
                                        <div v-for="(file, index) of uploadedFiles"
                                            :key="file.name + file.type + file.size"
                                            class="p-3 border rounded d-flex flex-column align-items-center gap-3">
                                            <div>
                                                <img role="presentation" :alt="file.name" :src="file.objectURL"
                                                    width="100" height="50" />
                                            </div>
                                            <span class="font-weight-bold text-truncate" style="max-width: 60px;">{{
                                                file.name }}</span>
                                            <div>{{ formatSize(file.size) }}</div>
                                            <Badge value="Completed" class="badge-success mt-4" />
                                            <Button icon="pi pi-times" @click="removeUploadedFileCallback(index)"
                                                class="btn btn-outline-danger rounded-pill" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </template>
                        <template #empty>
                            <div class="d-flex align-items-center justify-content-center flex-column">
                                <i class="pi pi-cloud-upload border-2 rounded-circle p-3 text-muted display-4" />
                                <p class="mt-3 mb-0">Kéo và thả file vào đây để tải lên.</p>
                            </div>
                        </template>
                    </FileUpload>

                </div>
            </div>
        </main>
        <template #footer>
            <Button label="Hủy" text severity="secondary" @click="dialogVisible = false" autofocus />
            <Button label="Lưu" outlined severity="secondary" @click="themDanhMuc" autofocus />
        </template>
    </Dialog>
    <DataTable :value="danhMucList" data-key="id">
        <template #header>
            <span class="pi pi-list-check fs-3"></span>
            <div class="d-flex justify-content-between">
                <h4>Quản Lý Danh Mục</h4>
                <Button @click="dialogVisible = true" label="Thêm Danh Mục" icon="pi pi-plus" severity="success" />
            </div>
        </template>
        <Column field="id" header="Mã danh mục"></Column>
        <Column field="tenDanhMuc" header="Tên danh mục"></Column>
        <Column field="anhDanhMuc" header="Ảnh">
            <template #body="{ data }">
                <Avatar shape="circle" size="xlarge" :image="data.anhDanhMuc"
                    style="background-color: #e72734; color: white;" />
            </template>
        </Column>
        <Column field="id">
            <template #body="{ data }">
                <Button @click="getDanhMucById(data.id)" severity="info" type="button" icon="pi pi-info" rounded />
                <Button @click="xoaDanhMucById(data.id)" severity="danger" class="ms-2" type="button" icon="pi pi-trash"
                    rounded />
            </template>
        </Column>
    </DataTable>
</template>