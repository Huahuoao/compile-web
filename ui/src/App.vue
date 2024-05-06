<template>
  <v-app>
    <div class="main">
      <div class="left" style="border: lightblue 5px solid; padding: 10px; margin: 20px;">
        <v-textarea v-model="input" label="语法" style="width: 30vw;" variant="solo"></v-textarea>
        <div class="button-group">
          <v-btn class="card" @click="generate">
            生成
          </v-btn>
          <v-btn class="card" @click="reset">
            重置
          </v-btn>
          <v-btn class="card" @click="input = ''">
            清空
          </v-btn>
        </div>
        <div class="card-group">
          <v-card :text="first" class="card" style="height: 200px; width: 15vw" title="first集"></v-card>
          <v-card :text="follow" class="card" style="height: 200px; width: 15vw" title="follow集"></v-card>
        </div>

      </div>
      <div class="right-2" style="display:flex; border: lightblue 5px solid; padding: 10px; margin: 20px;">
        <div class="right" style="padding: 10px; margin: 20px;">

          <highlightjs v-if="!editType" :code="code" language="cpp" style="width: 30vw;">
            <v-textarea v-model="code" label="要解析的程序" variant="solo">


            </v-textarea>
          </highlightjs>
          <div style="width: 30vw;">
            <v-textarea v-if="editType" v-model="code" label="要解析的程序" variant="solo">
            </v-textarea>
          </div>
          <div calss="button-group">
            <v-btn class="card" @click="edit">
              {{ editType ? '完成' : '编辑' }}
            </v-btn>
            <v-btn class="card" @click="generateCode">
              生成
            </v-btn>
            <v-btn class="card" @click="example">
              示例
            </v-btn>
            <v-btn class="card" @click="code = ''">
              清空
            </v-btn>
          </div>


        </div>
        <div class="card-group" style="margin-bottom: 20px;">
          <v-data-table
            :items="desserts"
            :items-per-page="10"
            class="elevation-1"
            style="margin-top: 50px;"
          ></v-data-table>
        </div>
      </div>
    </div>
  </v-app>
</template>


<script setup>
import {onMounted, ref} from 'vue'
import axios from "axios";


const
  first = ref('')
const follow = ref('')
const input = ref('')
const editType = ref(false)
const edit = () => {
  editType.value = !editType.value
}
const example = () => {
  axios.post('http://localhost:8080/basic/code/example').then(res => {
    code.value = res.data
  });
}
const reset = () => {
  axios.post('http://localhost:8080/basic/reset').then(res => {
    input.value = res.data
  });
}
onMounted(() => {

})
const generate = () => {
  console.log(input.value);
  axios
    .post('http://localhost:8080/basic/generate', {
      input: input.value.replace(/\n/g, "\\n"),
    })
    .then(res => {
      console.log(res.data);
      first.value = res.data.first;
      follow.value = res.data.follow;
    })
    .catch(error => {
      console.error('请求错误:', error);
    });
};
const desserts = ref();
const code = ref('')
const generateCode = () => {
  axios
    .post('http://localhost:8080/basic/code', {
      input: code.value.replace(/\n/g, "\\n"),
    })
    .then(res => {
      console.log(res.data);
      desserts.value = res.data

    })
    .catch(error => {
      console.error('请求错误:', error);
    });
};


</script>

<style scoped>
.main {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  margin: 10px;
}

.button-group {
  display: flex;
  margin-bottom: 10px; /* 添加底部间距 */
}

.card-group {
  display: flex;
  margin-bottom: 20px; /* 添加底部间距 */
}

.left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.right {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

</style>
