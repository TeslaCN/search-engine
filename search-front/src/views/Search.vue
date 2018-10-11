<template>
  <el-container>
    <el-header>
      <el-row>
        <el-col :span="24">
          <finder @search="printData"/>
        </el-col>
      </el-row>
    </el-header>
    <el-main v-loading="loading">
      <el-row v-for="result in results" :key="result.uri">
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
        <el-pagination
          background
          @size-change="sizeChange"
          @current-change="pageChange"
          :current-page="page"
          :page-sizes="sizes"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-row>
    </el-main>
  </el-container>
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
        page: 1,
        size: 0,
        total: 0,
        sizes: [],
        loading: false,
      }
    },
    mounted() {
      for (let i = 1; i <= this.randInt(3, 6); i++) {
        this.sizes.push(this.randInt(i * 10, i * 10))
      }
      this.size = this.sizes[0]
    },
    methods: {
      randInt(floor, offset) {
        return Math.floor(Math.random() * offset) + floor
      },
      printData(data) {
        this.key = data.item.value
        this.total = 0
        this.page = 1
        this.results = []
        this.load()
      },
      load() {
        this.loading = true
        this.$ajax('/like?page=' + (this.page - 1) + '&size=' + this.size + '&key=' + this.key).then((response) => {
          let hits = response.data.data
          this.results = hits.entities
          this.total = hits.totalHits
          this.loading = false
        })
      },
      pageChange(value) {
        this.page = value
        this.load()
      },
      sizeChange(value) {
        this.size = value
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
