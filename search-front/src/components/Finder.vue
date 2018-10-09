<template>
  <el-row>
    <el-col :span="24">
      <el-autocomplete
        v-model="input"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入内容"
        @select="handleSelect"
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
      load(key) {
        let data = [];
        this.$ajax('/prefix?key=' + key).then((response) => {
          this.suggestions = response.data
        })
      },
      querySearchAsync(queryString, cb) {
        let suggestions = this.suggestions;
        // let results = queryString ? searchResults.filter(this.createStateFilter(queryString)) : searchResults

        clearTimeout(this.timeout)
        this.timeout = setTimeout(() => {
          cb(suggestions)
        }, 1000)
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
        }
      },
      handleSelect(item) {
        this.$emit('search', {
          item: item
        })
      }
    },
    mounted() {
      this.load('')
    }
  }
</script>

<style scoped>
  .el-input {
    min-width: 50%;
  }
</style>
