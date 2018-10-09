<template>
  <el-col :span="24">
    <el-autocomplete
      v-model="state4"
      :fetch-suggestions="querySearchAsync"
      placeholder="请输入内容"
      @select="handleSelect"
      value-key="title"
    ></el-autocomplete>
  </el-col>
</template>

<script>
  export default {
    data() {
      return {
        searchResults: [],
        state4: '',
        timeout: null
      };
    },
    methods: {
      load(key) {
        this.$ajax('/search?key=' + key).then((response) => {
          this.searchResults = response.data
        })
      },
      querySearchAsync(queryString, cb) {
        if (!queryString) {
          return
        }
        this.load(queryString)
        let searchResults = this.searchResults;
        // let results = queryString ? searchResults.filter(this.createStateFilter(queryString)) : searchResults;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(searchResults);
        }, 1000);
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item.uri);
        console.log(item.title)
        console.log(item.highlights)
      }
    },
    mounted() {
      console.log('mounted()')
    }
  };
</script>

<style scoped>
</style>
