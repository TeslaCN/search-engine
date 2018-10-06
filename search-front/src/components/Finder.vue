<template>
  <input v-model="input">
</template>

<script>
  export default {
    name: "Finder",
    data() {
      return {
        input: '',
        results: {},
      }
    },
    watch: {
      input: (newVal, oldVal) => {
        console.log(oldVal + ' -> ' + newVal)
      }
    }
  }

  function get() {

  }
</script>

<style scoped>

</style>
