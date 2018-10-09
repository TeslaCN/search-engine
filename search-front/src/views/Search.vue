<template>
  <el-row>
    <el-col :span="24">
      <finder/>
    </el-col>
  </el-row>
</template>

<script>
  import finder from '@/components/Finder';

  export default {
    name: "search",
    components: {finder},
    data() {
      return {}
    }
  }
</script>

<style scoped>
</style>
