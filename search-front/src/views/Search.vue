<template>
  <el-main>
    <el-row>
      <el-col :span="24">
        <finder @search="printData"/>
      </el-col>
    </el-row>
    <div v-for="result in results">
      <div>
        <a :href="result.uri" target="_blank">
          <span v-html="result.title"></span>
        </a>
      </div>
      <div v-for="line in result.highlights"><span v-html="line"></span></div>
    </div>
  </el-main>
</template>

<script>
  import finder from '@/components/Finder'

  export default {
    name: "search",
    components: {finder},
    data() {
      return {
        key: '',
        results: []
      }
    },
    methods: {
      printData(data) {
        this.key = data
        console.log(data)
        this.$ajax('/search?key=' + data.item.value).then((response) => {
          this.results = response.data
        })
      }
    }
  }
</script>

<style scoped>
  em {
    color: yellow;
    font-weight: bold;
  }
</style>
