<template>
    <div class="img d-flex align-items-center justify-content-center mb-2">
        <img style="max-width: 200px; max-height: 170px;" :src="hinhAnh" alt="">
    </div>
    <div class="card">
        <DataTable v-model:editingRows="editingRows" :value="products" editMode="row" dataKey="id"
            @row-edit-save="onRowEditSave" @row-edit-cancel="onRowEditCancel" :pt="{
                table: { style: 'min-width: 50rem' },
                column: {
                    bodycell: ({ state }) => ({
                        style: state['d_editing'] && 'padding-top: 0.75rem; padding-bottom: 0.75rem'
                    })
                }
            }">
            <template #header>
                <div class="d-flex flex-wrap items-items-center justify-content-end gap-2">
                    <Button @click="newRowAdd()" icon="pi pi-plus" rounded raised />
                </div>
            </template>
            <Column field="kichThuoc" header="Kích thước" style="width: 20%">
                <template #editor="{ data, field }">
                    <InputText :style="data[field] == '' ? `border: 2px solid red` : ``" v-model="data[field]" fluid />
                </template>
            </Column>
            <Column field="mauSac" header="Màu sắc" style="width: 20%">
                <template #editor="{ data, field }">
                    <Select :style="data[field] == '' ? `border: 2px solid red` : ``" v-model="data[field]"
                        :options="mauSacList" optionLabel="label" optionValue="value" placeholder="Chọn màu" fluid>
                        <template #option="slotProps">
                            <Tag :value="slotProps.option.value" severity="secondary">
                                <div class="d-flex justify-content-center align-items-center gap-2 px-1">
                                    <Avatar :style="`background-color: ${slotProps.option.css}`" size="normal"
                                        shape="circle" />
                                    <span class="text-base">{{ slotProps.option.label }}</span>
                                </div>
                            </Tag>
                        </template>
                    </Select>
                </template>
                <template #body="slotProps">
                    <Tag severity="secondary" :value="slotProps.data.mauSac"
                        :style="`background-color: ${getMauSac(slotProps.data.mauSac)}`" />
                </template>
            </Column>
            <Column field="giaTien" header="Giá" style="width: 20%">
                <template #body="{ data, field }">
                    {{ formatCurrency(data[field]) }}
                </template>
                <template #editor="{ data, field }">
                    <InputNumber :style="data[field].toString().length <= 1 ? `border: 2px solid red` : ``"
                        v-model="data[field]" mode="currency" currency="USD" locale="en-US" fluid />
                </template>
            </Column>
            <Column field="soLuongTonKho" header="Số lượng" style="width: 20%">
                <template #editor="{ data, field }">
                    <InputText :style="data[field] == 0 ? `border: 2px solid red` : ``" v-model="data[field]" fluid />
                </template>
            </Column>
            <Column :rowEditor="true" style="width: 10%; min-width: 8rem" bodyStyle="text-align:center"></Column>
            <Column field="id" headerStyle="width: 5rem; text-align: center"
                bodyStyle="text-align: center; overflow: visible">
                <template #body="{ data }">
                    <Button @click="xoaChiTietSp(data.id)" type="button" icon="pi pi-trash" rounded />
                </template>
            </Column>
        </DataTable>
    </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import axios from 'axios';
const products = ref();
const editingRows = ref([]);
const mauSacList = ref([
    { label: 'Trắng', css: 'white', value: 'Trắng' },
    { label: 'Đen', css: 'black', value: 'Đen' },
    { label: 'Xanh', css: 'blue', value: 'Xanh' },
    { label: 'Vàng', css: 'yellow', value: 'Vàng' },
    { label: 'Lục', css: 'green', value: 'Lục' },
    { label: 'Lam', css: 'gray', value: 'Lam' },
    { label: 'Chàm', css: 'orange', value: 'Chàm' },
    { label: 'Tím', css: 'purple', value: 'Tím' },
]);
const dialogRef = inject('dialogRef');
let maSanPham = ref();
let hinhAnh = ref();
async function getChiTIetSp() {
    let res = await axios.get('http://localhost:8080/admin/chi-tiet-san-pham/ma-sp?maSanPham=' + maSanPham.value);
    products.value = res.data;
}
onMounted(async () => {
    maSanPham.value = dialogRef?.value?.data?.maSanPham
    hinhAnh.value = dialogRef?.value?.data?.hinhAnh
    console.log(maSanPham.value)
    await getChiTIetSp();
    console.log(products.value)
});
function newRowAdd() {
    products.value.unshift({
        id: products?.value?.length + 1,
        // maSanPham: { id: maSanPham.value },
        kichThuoc: '',
        mauSac: '',
        giaTien: 0,
        soLuongTonKho: 0
    })

}
async function xoaChiTietSp(id) {
    if (id == 0 || id == null) {
        await getChiTIetSp()
        return
    }
    let res = await axios.delete('http://localhost:8080/admin/chi-tiet-san-pham/xoa?id=' + id);
    await getChiTIetSp()
    if (res == 200) {
        await getChiTIetSp()
    }
}
const onRowEditSave = async (event) => {
    let { newData, index } = event;
    let newChiTietSp = {
        id: newData.id,
        maSanPham: { id: maSanPham.value },
        kichThuoc: newData.kichThuoc,
        mauSac: newData.mauSac,
        giaTien: newData.giaTien,
        soLuongTonKho: newData.soLuongTonKho
    }
    let res = await axios.post('http://localhost:8080/admin/chi-tiet-san-pham/them', newChiTietSp)
    await getChiTIetSp()
    if (res == 201) {
        await getChiTIetSp()
        console.log(res)
    }
};
const onRowEditCancel = async (event) => {
    await getChiTIetSp()
}
const getMauSac = (mauSac) => {
    switch (mauSac) {
        case 'Trắng':
            return 'white';

        case 'Đen':
            return 'black';

        case 'Xanh':
            return 'blue';

        case 'Vàng':
            return 'yellow';
        case 'Lục':
            return 'green';
        case 'Lam':
            return 'gray';

        case 'Chàm':
            return 'orange';

        case 'Tím':
            return 'purple';

        default:
            return null;
    }
};
const formatCurrency = (value) => {
    return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value);
}

</script>
