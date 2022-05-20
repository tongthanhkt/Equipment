<template>
  <div><h1>{{tasks}}</h1></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: "App",
 data() {
   return {
     tasks: "Hello Rever"
   };
 },
})
</script>


<style >
h1 { 
  color: red;
}
</style>
