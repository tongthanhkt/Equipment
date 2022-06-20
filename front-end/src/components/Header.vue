<template>
  <header
    class="place-content-center flex items-center px-6 py-2 bg-white border-b-4 border-indigo-600"
  >
    <div class="place-content-center">
      <h1 class="place-content-center w-96 text-2xl font-bold">EQUIPMENT MANAGEMENT</h1>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useSidebar } from "../hooks/useSidebar";

const dropdownOpen = ref(false);
const { isOpen } = useSidebar();
const notificationOpen = ref(false);
</script>
