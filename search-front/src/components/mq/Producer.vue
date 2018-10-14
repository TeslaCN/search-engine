<template>
  <el-row>
    <el-select
      v-model="uris"
      multiple
      filterable
      allow-create
      default-first-option
      placeholder="输入URI，回车添加">
    </el-select>
    <el-button type="primary" @click="submit">提交任务</el-button>
  </el-row>
</template>

<script>
  export default {
    data() {
      return {
        options5: [{
          value: 'HTML',
          label: 'HTML'
        }, {
          value: 'CSS',
          label: 'CSS'
        }, {
          value: 'JavaScript',
          label: 'JavaScript'
        }],
        uris: []
      }
    },
    methods: {
      submit() {
        let params = new URLSearchParams()
        params.append('uris', this.uris.join("|"))
        this.$ajax.post('/mission', params).then((response) => {
          let data = response.data
          if (data.succeed) {
            alert(data.succeed)
            this.uris = []
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
