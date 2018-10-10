<template>
  <el-row>
    <el-col :span="24">
      <el-autocomplete
        v-model="input"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入内容"
        @select="handleSelect"
        :select-when-unmatched="true"
        clearable
      ></el-autocomplete>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    data() {
      return {
        suggestions: [],
        input: '',
        timeout: null
      }
    },
    methods: {
      querySearchAsync(queryString, cb) {
        this.$ajax('/prefix?key=' + queryString).then((response) => {
          this.suggestions = response.data
          cb(this.suggestions)
        })
      },
      handleSelect(item) {
        this.$emit('search', {
          item: item
        })
      }
    },
    mounted() {
    }
  }
</script>

<style scoped>
  em {

  }
</style>
