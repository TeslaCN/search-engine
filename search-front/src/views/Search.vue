<template>
  <el-main>
    <el-row>
      <el-col :span="24">
        <finder @search="printData"/>
      </el-col>
    </el-row>
    <el-row v-for="result in results">
      <div>
        <div>
          <a :href="result.uri" target="_blank">
            <span v-if="result.title != ''" v-html="result.title"></span>
            <span v-else>{{result.uri}}</span>
          </a>
        </div>
        <div v-for="line in result.highlights"><span v-html="line"></span></div>
      </div>
    </el-row>
    <el-row>
      <el-input-number v-model="page" @change="pageChange" :min="1" :max="100" label="page"></el-input-number>
      <el-input-number v-model="size" @change="sizeChange" :min="5" :step="5" :max="100" label="size"></el-input-number>
    </el-row>
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
        results: [],
        page: 0,
        size: 10,
      }
    },
    methods: {
      printData(data) {
        this.key = data.item.value
        this.page = 0
        this.size = 10
        this.results = []
        this.load()
      },
      load() {
        this.$ajax('/like?page=' + this.page + '&size=' + this.size + '&key=' + this.key).then((response) => {
          this.results = response.data
        })
      },
      pageChange(value) {
        this.load()
      },
      sizeChange(value) {
        this.load()
      }
    }
  }
</script>

<style>
  em {
    color: red;
    font-weight: bold;
  }
</style>
