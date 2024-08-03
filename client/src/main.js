import "primeicons/primeicons.css";
import { createApp } from "vue";
import Toast from "primevue/toast";
import ToastService from "primevue/toastservice";
import App from "./App.vue";
import router from "./router";
import PrimeVue from "primevue/config";
import Aura from "@primevue/themes/aura";
import Paginator from "primevue/paginator";
import Tabs from "primevue/tabs";
import TabList from "primevue/tablist";
import Tab from "primevue/tab";
import TabPanels from "primevue/tabpanels";
import TabPanel from "primevue/tabpanel";
import Avatar from "primevue/avatar";
import Menu from "primevue/menu";
import axios from "axios";
import Menubar from "primevue/menubar";
import Button from "primevue/button";
import Ripple from "primevue/ripple";
import ConfirmDialog from "primevue/confirmdialog";
import ConfirmationService from "primevue/confirmationservice";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import ColumnGroup from "primevue/columngroup"; // optional
import Row from "primevue/row"; // optional
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import InputText from "primevue/inputtext";
import Tag from "primevue/tag";
import Select from "primevue/select";
import AvatarGroup from "primevue/avatargroup";
import Dialog from "primevue/dialog";
import Carousel from "primevue/carousel";
import Chart from "primevue/chart";
import Toolbar from "primevue/toolbar";
import MultiSelect from "primevue/multiselect";
import DatePicker from "primevue/datepicker";
import InputNumber from "primevue/inputnumber";
import ProgressBar from "primevue/progressbar";
import Tooltip from "primevue/tooltip";
import Slider from "primevue/slider";
import Textarea from "primevue/textarea";
import FileUpload from "primevue/fileupload";
import RadioButton from "primevue/radiobutton";
import DialogService from "primevue/dialogservice";
import DynamicDialog from "primevue/dynamicdialog";
import Badge from "primevue/badge";
import { createPinia } from "pinia";

axios.defaults.withCredentials = true;
const pinia = createPinia();
const app = createApp(App);
app.use(ConfirmationService);
app.component("AvatarGroup", AvatarGroup);
app.component("Badge", Badge);
app.component("DynamicDialog", DynamicDialog);
app.component("RadioButton", RadioButton);
app.component("DatePicker", DatePicker);
app.component("Textarea", Textarea);
app.component("Toolbar", Toolbar);
app.component("Slider", Slider);
app.component("FileUpload", FileUpload);
app.component("Tooltip", Tooltip);
app.component("ProgressBar", ProgressBar);
app.component("InputNumber", InputNumber);
app.component("MultiSelect", MultiSelect);
app.component("Chart", Chart);
app.component("ConfirmDialog", ConfirmDialog);
app.component("Paginator", Paginator);
app.component("Button", Button);
app.component("Menu", Menu);
app.component("Avatar", Avatar);
app.component("Tabs", Tabs);
app.component("TabList", TabList);
app.component("Tab", Tab);
app.component("TabPanels", TabPanels);
app.component("TabPanel", TabPanel);
app.component("Menubar", Menubar);
app.component("Toast", Toast);
app.component("Column", Column);
app.component("ColumnGroup", ColumnGroup);
app.component("Row", Row);
app.component("DataTable", DataTable);
app.component("IconField", IconField);
app.component("InputIcon", InputIcon);
app.component("InputText", InputText);
app.component("Select", Select);
app.component("Tag", Tag);
app.component("Dialog", Dialog);
app.component("Carousel", Carousel);
app.use(ToastService);
app.use(pinia);
app.use(router);
app.directive("ripple", Ripple);
app.use(PrimeVue, {
  ripple: true,
  theme: {
    preset: Aura,
    options: {
      prefix: "p",
      darkModeSelector: "data-theme",
      cssLayer: false,
    },
  },
});
app.use(DialogService);
app.mount("#app");
