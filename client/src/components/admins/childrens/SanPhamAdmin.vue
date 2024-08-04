<template>
    <div>
        <div class="card">
            <ConfirmDialog></ConfirmDialog>
            <Toast />
            <Toolbar class="mb-6">
                <template #start>
                    <Button label="Thêm Mới" icon="pi pi-plus" severity="success" class="mr-2" @click="openNew" />
                    <Button label="Xóa" icon="pi pi-trash" severity="danger" @click="deleteMultiSp"
                        :disabled="!selectedProducts || !selectedProducts.length" />
                </template>

                <template #end>
                    <Button label="Xuất CSV" icon="pi pi-upload" severity="help" @click="exportCSV($event)" />
                </template>
            </Toolbar>

            <DataTable ref="dt" v-model:selection="selectedProducts" :value="products" dataKey="id" :paginator="true"
                :rows="10" :filters="filters"
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                :rowsPerPageOptions="[5, 10, 25]">
                <template #header>
                    <div class="d-flex flex-wrap gap-2 align-items-center justify-content-between">
                        <h4 class="m-0">Quản Lý Sản Phẩm</h4>
                        <IconField>
                            <InputIcon>
                                <i class="pi pi-search" />
                            </InputIcon>
                            <InputText v-model="filters['global'].value" placeholder="Tìm kiếm..." />
                        </IconField>
                    </div>
                </template>

                <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>
                <Column field="id" header="Mã sản phẩm" sortable style="min-width: 12rem"></Column>
                <Column field="tenSanPham" header="Tên sản phẩm" sortable style="min-width: 16rem"></Column>
                <Column field="hinhAnh" header="Ảnh">
                    <template #body="slotProps">
                        <img :src="slotProps.data.hinhAnh" alt="SP" class="rounded" style="width: 64px" />
                    </template>
                </Column>
                <Column field="tenDanhMuc" header="Danh mục" sortable style="min-width: 10rem"></Column>
                <Column field="moTa" header="Mô tả" sortable style="min-width: 12rem">
                    <template #body="slotProps">
                        <p v-if="slotProps.data.moTa">{{ slotProps.data.moTa.length > 25 ?
                            slotProps.data.moTa.substring(0, 25) + '...' :
                            slotProps.data.moTa }}</p>
                    </template>
                </Column>
                <Column field="id" :exportable="false" style="min-width: 12rem">
                    <template #body="slotProps">
                        <Button icon="pi pi-pencil" outlined rounded class="mr-2"
                            @click="getOneSp(slotProps.data.id)" />
                        <Button icon="pi pi-trash" outlined rounded class="mr-2" severity="danger"
                            @click="deleteOneSp(slotProps.data.id)" />
                        <Button icon="pi pi-cog" outlined rounded severity="info"
                            @click="showChiTietSp(slotProps.data.id)" />
                    </template>
                </Column>
            </DataTable>
        </div>

        <Dialog v-model:visible="productDialog" :style="{ width: '800px' }" header="Thêm Sản Phẩm Mới" :modal="true">
            <div class="d-flex flex-column gap-3">
                <img style="max-width: 80%;" v-if="product.hinhAnh" :src="product.hinhAnh" alt="Image"
                    class="d-block mx-auto pb-3" />
                <FileUpload name="demo[]" url="/api/upload" :multiple="false" accept="image/*" :maxFileSize="1000000"
                    @select="onSelectedFile">
                    <template #header="{ chooseCallback, uploadCallback, clearCallback, files }">
                        <div class="flex flex-wrap justify-between items-center flex-1 gap-4">
                            <div class="flex gap-2">
                                <Button @click="chooseCallback()" icon="pi pi-images" rounded outlined
                                    severity="secondary"></Button>
                                <Button @click="clearCallback()" icon="pi pi-times" rounded outlined severity="danger"
                                    :disabled="!files || files.length === 0"></Button>
                            </div>
                            <ProgressBar :value="totalSizePercent" :showValue="false"
                                class="md:w-20rem h-1 w-full md:ml-auto">
                                <span class="whitespace-nowrap">{{ totalSize }}B / 1Mb</span>
                            </ProgressBar>
                        </div>
                    </template>
                    <template #content="{ files, uploadedFiles, removeUploadedFileCallback, removeFileCallback }">
                        <div class="flex flex-col gap-8 pt-4">
                            <div v-if="files.length > 0">
                                <h5>Ảnh</h5>
                                <div class="flex flex-wrap gap-4">
                                    <div v-for="(file, index) of files.slice(0, 1)"
                                        :key="file.name + file.type + file.size"
                                        class="p-8 rounded-border flex flex-col border border-surface items-center gap-4">
                                        <div>
                                            <img role="presentation" :alt="file.name" :src="file.objectURL" width="100"
                                                height="50" />
                                        </div>
                                        <span
                                            class="font-semibold text-ellipsis max-w-60 whitespace-nowrap overflow-hidden">{{
                                                file.name }}</span>
                                        <div>{{ formatSize(file.size) }}</div>
                                        <Badge value="Đã tải" severity="warn" />
                                        <Button icon="pi pi-times"
                                            @click="onRemoveTemplatingFile(file, removeFileCallback, index)" outlined
                                            rounded severity="danger" />
                                    </div>
                                </div>
                            </div>

                            <div v-if="uploadedFiles.length > 0">
                                <h5>Completed</h5>
                                <div class="flex flex-wrap gap-4">
                                    <div v-for="(file, index) of uploadedFiles" :key="file.name + file.type + file.size"
                                        class="p-8 rounded-border flex flex-col border border-surface items-center gap-4">
                                        <div>
                                            <img role="presentation" :alt="file.name" :src="file.objectURL" width="100"
                                                height="50" />
                                        </div>
                                        <span
                                            class="font-semibold text-ellipsis max-w-60 whitespace-nowrap overflow-hidden">{{
                                                file.name }}</span>
                                        <div>{{ formatSize(file.size) }}</div>
                                        <Badge value="Completed" class="mt-4" severity="success" />
                                        <Button icon="pi pi-times" @click="removeUploadedFileCallback(index)" outlined
                                            rounded severity="danger" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                    <template #empty>
                        <div class="flex items-center justify-center flex-col">
                            <i class="pi pi-cloud-upload !border-2 !rounded-full !p-8 !text-4xl !text-muted-color" />
                            <p class="mt-6 mb-0">Kéo và thả file vào đây để tải lên.</p>
                        </div>
                    </template>
                </FileUpload>
                <div class="mb-3">
                    <label for="name" class="fw-bold mb-2">Tên sản phẩm</label>
                    <InputText id="name" v-model.trim="product.tenSanPham" required="true" autofocus
                        :invalid="submitted && !product.tenSanPham" class="form-control" />
                    <small v-if="submitted && !product.tenSanPham" class="text-danger">Name is required.</small>
                </div>
                <div class="mb-3">
                    <label for="description" class="fw-bold mb-2">Mô tả</label>
                    <Textarea id="description" v-model="product.moTa" required="true" rows="3" cols="20"
                        class="form-control" />
                </div>
                <div class="mb-3">
                    <span class="fw-bold mb-3">Danh mục</span>
                    <div class="row gx-3">
                        <div v-for="item in danhMucList" class="col-6 d-flex align-items-center gap-2">
                            <input @click="idDanhMuc = item.id; console.log(idDanhMuc)" ng-model="product.idDanhMuc"
                                :checked="product.idDanhMuc == item.id" type="radio" :id="item.id" :value="item.id"
                                v-model="product.danhMuc" />
                            <label class="form-check-label" :for="item.id">{{ item.tenDanhMuc }}</label>
                        </div>
                    </div>
                </div>
            </div>

            <template #footer>
                <Button label="Cancel" icon="pi pi-times" class="btn btn-secondary" @click="hideDialog" />
                <Button label="Save" icon="pi pi-check" class="btn btn-primary" @click="sendPayload" />
            </template>
        </Dialog>


        <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex items-center gap-4">
                <i class="pi pi-exclamation-triangle !text-3xl" />
                <span v-if="product">Are you sure you want to delete <b>{{ product.name }}</b>?</span>
            </div>
            <template #footer>
                <Button label="No" icon="pi pi-times" text @click="deleteProductDialog = false" />
                <Button label="Yes" icon="pi pi-check" @click="deleteProduct" />
            </template>
        </Dialog>

        <Dialog v-model:visible="deleteProductsDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
            <div class="flex items-center gap-4">
                <i class="pi pi-exclamation-triangle !text-3xl" />
                <span v-if="product">Are you sure you want to delete the selected products?</span>
            </div>
            <template #footer>
                <Button label="No" icon="pi pi-times" text @click="deleteProductsDialog = false" />
                <Button label="Yes" icon="pi pi-check" text @click="deleteSelectedProducts" />
            </template>
        </Dialog>
    </div>
    <DynamicDialog />
</template>

<script setup>
import { FilterMatchMode } from '@primevue/core/api';
import axios from 'axios';
import { usePrimeVue } from 'primevue/config';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
import { defineAsyncComponent, onMounted, ref, markRaw } from 'vue';
import { useDialog } from 'primevue/usedialog';
const $primevue = usePrimeVue();
const chiTietSpComp = defineAsyncComponent(() => import('./ChiTietSanPhamAdmin.vue'));
let danhMucList = ref([]);
const confirm = useConfirm();
const dialog = useDialog();
async function showChiTietSp(id) {
    await getOneSp(id);
    productDialog.value = false;
    dialog.open(chiTietSpComp, {
        data: {
            maSanPham: id,
            hinhAnh: product.value.hinhAnh
        },
        props: {
            header: 'Quản lý chi tiết sản phẩm ' + id,
            modal: true,
            maximizable: true,
            style: {
                width: '65vw',
            },
        }
    })
}
onMounted(async () => {
    await getAllSp();
    await getDanhMuc();
});
async function getAllSp() {
    // /admin/san-pham/
    let res = await axios.get('http://localhost:8080/admin/san-pham/');
    products.value = res.data;
}
const toast = useToast();
const dt = ref();
const products = ref();
const productDialog = ref(false);
const deleteProductDialog = ref(false);
const deleteProductsDialog = ref(false);
const product = ref({});
const selectedProducts = ref();
const filters = ref({
    'global': { value: null, matchMode: FilterMatchMode.CONTAINS },
});
const submitted = ref(false);
let fileValue = ref();
function onSelectedFile(event) {
    fileValue.value = event.files;
}
async function sendPayload() {
    if (product.value.hinhAnh == null && fileValue.value == null) {
        toast.add({ severity: 'error', summary: 'Lỗi', detail: 'Hãy nhập đầy đủ thông tin', life: 3000 });
        return;
    }
    let formData = new FormData();
    if (product.value.hinhAnh != null) {
        formData.append("hinhAnh", product.value.hinhAnh);
    } else {
        formData.append("file", fileValue.value[0]);
    }
    // formData.append("id", product.value.id ? product.value.id : null);
    if (product.value.id) {
        formData.append("id", product.value.id);
    }
    formData.append("file", fileValue.value[0]);
    formData.append("tenSanPham", product.value.tenSanPham);
    formData.append("moTa", product.value.moTa);
    formData.append("danhMuc", idDanhMuc.value);
    console.log(product.value);
    let res = await axios.post("http://localhost:8080/admin/san-pham/add", formData);
    console.log(res);
    if (res.status == 201) {
        toast.add({ severity: 'success', summary: 'Thành công', detail: 'Đã thêm sản phẩm', life: 3000 });
        productDialog.value = false;
        await getAllSp();
        await getDanhMuc();
    }
}
let idDanhMuc = ref();
async function getDanhMuc() {
    let res = await axios.get('http://localhost:8080/api/danh-muc/');
    danhMucList.value = res.data;
    console.log(danhMucList.value);
}
async function deleteMultiSp() {
    const deleteList = selectedProducts.value.map(item => {
        return {
            id: item.id
        }
    });
    confirm.require({
        message: `Bạn có muốn xoá những sản phẩm này không ?`,
        header: 'Xác nhận xóa?',
        icon: 'pi pi-info-circle',
        rejectProps: {
            label: 'Hủy',
            severity: 'secondary',
            outlined: true
        },
        acceptProps: {
            label: 'Xác nhận',
        },
        accept: async () => {
            let res = await axios.post('http://localhost:8080/admin/san-pham/delete', deleteList);
            if (res.status == 200) {
                toast.add({ severity: 'success', summary: 'Đã xóa', detail: 'Đã xoá những sản phẩm', life: 3000 });
                selectedProducts.value = null;
                await getAllSp();
            } else {
                toast.add({ severity: 'error', summary: 'Lỗi', detail: 'Có lỗi xảy ra, hãy kiểm tra lại', life: 3000 });
            }
        }
    })
}
async function deleteOneSp(id) {
    confirm.require({
        message: `Bạn có muốn xoá sản phẩm ${id} ?`,
        header: 'Xác nhận xóa?',
        icon: 'pi pi-info-circle',
        rejectProps: {
            label: 'Hủy',
            severity: 'secondary',
            outlined: true
        },
        acceptProps: {
            label: 'Xác nhận',
        },
        accept: async () => {
            let res = await axios.delete(`http://localhost:8080/admin/san-pham/delete-one?id=${id}`);
            if (res.status == 200) {
                toast.add({ severity: 'success', summary: 'Đã xóa', detail: 'Đã xoá sản phẩm', life: 3000 });
                await getAllSp();
            } else {
                toast.add({ severity: 'error', summary: 'Lỗi', detail: 'Có lỗi xảy ra, hãy kiểm tra lại', life: 3000 });
            }
        }
    })
}
const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
};
const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};
async function getOneSp(id) {
    // http://localhost:8080/admin/san-pham/sp?id = 2
    let res = await axios.get('http://localhost:8080/admin/san-pham/sp?id=' + id);
    product.value = res.data;
    productDialog.value = true;
    idDanhMuc.value = product.value.idDanhMuc;
    console.log(product.value)
}
const deleteProduct = () => {
    products.value = products.value.filter(val => val.id !== product.value.id);
    deleteProductDialog.value = false;
    product.value = {};
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
};
const exportCSV = () => {
    dt.value.exportCSV();
};
const totalSize = ref(0);
const totalSizePercent = ref(0);

const onRemoveTemplatingFile = (file, removeFileCallback, index) => {
    removeFileCallback(index);
    totalSize.value -= parseInt(formatSize(file.size));
    totalSizePercent.value = totalSize.value / 10;
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
const deleteSelectedProducts = () => {
    products.value = products.value.filter(val => !selectedProducts.value.includes(val));
    deleteProductsDialog.value = false;
    selectedProducts.value = null;
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
};


</script>
