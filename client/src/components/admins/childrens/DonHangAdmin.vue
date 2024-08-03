<template>
    <ConfirmDialog></ConfirmDialog>
    <Toast />
    <div v-if="hoaDonChoHuyList.length > 0" class="card mb-4">
        <DataTable v-model:filters="filters" v-model:selection="selectedCustomers" :value="hoaDonChoHuyList"
            dataKey="id" filterDisplay="menu" :globalFilterFields="['id', 'ngayLapHoaDon', 'tongTien', 'trangThai']">
            <template #header>
                <div class="d-flex justify-content-between">
                    <span class="fs-4 font-bold">Đơn Hàng Chờ Hủy</span>
                    <Button type="button" icon="pi pi-filter-slash" label="Clear" outlined @click="clearFilter()" />
                    <IconField>
                        <InputIcon>
                            <i class="pi pi-search" />
                        </InputIcon>
                        <InputText v-model="filters['global'].value" placeholder="Tìm kiếm" />
                    </IconField>
                </div>
            </template>
            <template #empty> No customers found. </template>
            <Column field="id" header="Mã đơn" sortable style="min-width: 14rem">
                <template #body="{ data }">
                    {{ data.id }}
                </template>
                <template #filter="{ filterModel }">
                    <InputText v-model="filterModel.value" type="text" placeholder="Search by name" />
                </template>
            </Column>
            <Column field="ngayLapHoaDon" header="Ngày tạo" sortable filterField="ngayLapHoaDon" dataType="date"
                style="min-width: 10rem">
                <template #body="{ data }">
                    {{ formatDate(data.ngayLapHoaDon) }}
                </template>
                <template #filter="{ filterModel }">
                    <DatePicker v-model="filterModel.value" dateFormat="mm/dd/yy" placeholder="mm/dd/yyyy" />
                </template>
            </Column>
            <Column field="tongTien" header="Tổng tiền" sortable filterField="tongTien" dataType="numeric"
                style="min-width: 10rem">
                <template #body="{ data }">
                    {{ formatCurrency(data.tongTien) }}
                </template>
                <template #filter="{ filterModel }">
                    <InputNumber v-model="filterModel.value" mode="currency" currency="USD" locale="en-US" />
                </template>
            </Column>
            <Column header="Trang thái" field="trangThai" sortable :filterMenuStyle="{ width: '14rem' }"
                style="min-width: 12rem">
                <template #body="{ data }">
                    <Tag :value="data.trangThai" :severity="getSeverity(data.trangThai)" />
                </template>
                <template #filter="{ filterModel }">
                    <Select v-model="filterModel.value" :options="statuses" placeholder="Select One" showClear>
                        <template #option="slotProps">
                            <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                        </template>
                    </Select>
                </template>
            </Column>
            <Column field="id" headerStyle="width: 5rem; text-align: center"
                bodyStyle="text-align: center; overflow: visible">
                <template #body="{ data }">
                    <Button @click="updateHoaDon(data.id)" type="button" icon="pi pi-check" rounded />
                </template>
            </Column>
        </DataTable>
    </div>
    <div class="card">
        <DataTable v-model:filters="filters" v-model:selection="selectedCustomers" :value="customers" paginator
            :rows="10" dataKey="id" filterDisplay="menu"
            :globalFilterFields="['id', 'ngayLapHoaDon', 'tongTien', 'trangThai']">
            <template #header>
                <div class="d-flex justify-content-between">
                    <span class="fs-4 font-bold">Tất Cả Đơn Hàng</span>
                    <Button type="button" icon="pi pi-filter-slash" label="Clear" outlined @click="clearFilter()" />
                    <IconField>
                        <InputIcon>
                            <i class="pi pi-search" />
                        </InputIcon>
                        <InputText v-model="filters['global'].value" placeholder="Tìm kiếm" />
                    </IconField>
                </div>
            </template>
            <template #empty> No customers found. </template>
            <Column field="id" header="Mã đơn" sortable style="min-width: 14rem">
                <template #body="{ data }">
                    {{ data.id }}
                </template>
                <template #filter="{ filterModel }">
                    <InputText v-model="filterModel.value" type="text" placeholder="Search by name" />
                </template>
            </Column>
            <Column field="ngayLapHoaDon" header="Ngày tạo" sortable filterField="ngayLapHoaDon" dataType="date"
                style="min-width: 10rem">
                <template #body="{ data }">
                    {{ formatDate(data.ngayLapHoaDon) }}
                </template>
                <template #filter="{ filterModel }">
                    <DatePicker v-model="filterModel.value" dateFormat="mm/dd/yy" placeholder="mm/dd/yyyy" />
                </template>
            </Column>
            <Column field="tongTien" header="Tổng tiền" sortable filterField="tongTien" dataType="numeric"
                style="min-width: 10rem">
                <template #body="{ data }">
                    {{ formatCurrency(data.tongTien) }}
                </template>
                <template #filter="{ filterModel }">
                    <InputNumber v-model="filterModel.value" mode="currency" currency="USD" locale="en-US" />
                </template>
            </Column>
            <Column header="Trang thái" field="trangThai" sortable :filterMenuStyle="{ width: '14rem' }"
                style="min-width: 12rem">
                <template #body="{ data }">
                    <Tag :value="data.trangThai" :severity="getSeverity(data.trangThai)" />
                </template>
                <template #filter="{ filterModel }">
                    <Select v-model="filterModel.value" :options="statuses" placeholder="Select One" showClear>
                        <template #option="slotProps">
                            <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                        </template>
                    </Select>
                </template>
            </Column>
        </DataTable>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { FilterMatchMode, FilterOperator } from '@primevue/core/api';
import axios from 'axios';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
let hoaDonChoHuyList = ref([])
const confirm = useConfirm();
const customers = ref();
const selectedCustomers = ref();
const filters = ref();
const toast = useToast();
const statuses = ref(['Đã đặt', 'Chờ hủy', 'Đã hủy']);
async function getAllHoaDon() {
    try {
        let res = await axios.get('http://localhost:8080/admin/hoa-don')
        customers.value = res.data
    } catch (error) {
        console.log(error)
    }
}
async function getHoaDonChoHuy() {
    let res = await axios.get('http://localhost:8080/admin/cho-huy')
    hoaDonChoHuyList.value = res.data
}
async function updateHoaDon(id) {
    // http://localhost:8080/admin/update-trang-thai-hoa-don?id=1
    // let res = await axios.patch(`http://localhost:8080/admin/update-trang-thai-hoa-don?id=${id}`)
    // if (res.status == 200) { }
    confirm.require({
        message: `Bạn có muốn xác nhận hủy đơn hàng ${id}?`,
        header: 'Xác nhận hủy đơn hàng',
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
            let res = await axios.patch(`http://localhost:8080/admin/update-trang-thai-hoa-don?id=${id}`)
            if (res.status == 200) {
                await getAllHoaDon()
                await getHoaDonChoHuy()
                toast.add({ severity: 'success', summary: 'Thành công', detail: 'Hủy đơn hàng thành công', life: 3000 })
            }
        }
    })
}
onMounted(async () => {
    await getAllHoaDon()
    await getHoaDonChoHuy()
});


const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
        id: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        ngayLapHoaDon: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
        tongTien: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        trangThai: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
    };
};

initFilters();

const formatDate = (dateTimeString) => {
    const date = new Date(dateTimeString);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}`;
};
const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};
const clearFilter = () => {
    initFilters();
};
const getSeverity = (status) => {
    switch (status) {
        case 'Đã hủy':
            return 'danger';
        case 'Đã đặt':
            return 'success';
        case 'Chờ hủy':
            return 'warn';
    }
};
</script>
