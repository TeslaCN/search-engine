<template>
  <el-container>
    <el-header>
      <el-row>
        <producer/>
      </el-row>
      <el-row>
        <finder @select="onTimeSelect"/>
        <el-button type="primary" @click="queryMessage">Query</el-button>
      </el-row>
    </el-header>
    <el-main>
      <el-row v-for="m in messages" :key="m.msgId">
        {{m.msgId}}
        <br/>
        {{m.messageBody}}
        <!--<el-button @click="deleteMessage(m.msgId)" type="danger" icon="el-icon-delete" circle></el-button>-->
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
  import producer from '@/components/mq/Producer'
  import finder from '@/components/mq/MessageFinder'

  export default {
    components: {producer, finder},
    data() {
      return {
        begin: 0,
        end: 0,
        pattern: '',
        messages: [],
      }
    },
    methods: {
      onTimeSelect(values) {
        this.begin = values[0].getTime()
        this.end = values[1].getTime()
        console.log(this.begin + ' -> ' + this.end)
      },
      queryMessage() {
        this.messages = []
        let p = new URLSearchParams()
        p.append('begin', this.begin)
        p.append('end', this.end)
        this.$ajax.get('/missions?' + p).then((response) => {
          this.messages = response.data.data
        })
      },
      deleteMessage(msgId) {
        let p = new URLSearchParams()
        p.append('msgId', msgId)
        this.$ajax.delete('/mission?' + p).then((response) => {
          this.messages = response.data.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
