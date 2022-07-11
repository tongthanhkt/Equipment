<template>
  <Datepicker v-model="date"></Datepicker>
</template>

<script>
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";

export default {
  components: { Datepicker },
  data() {
    return {
      date: null,
    };
  },
};
</script>
