<template>
  用户列表
  <a-list
    item-layout="horizontal"
    :data-source="props.userList"
    :grid="{ gutter: 16, column: 4 }"
  >
    <template #renderItem="{ item }">
      <a-card hoverable style="width: 200px">
        <template #cover>
          <img alt="example" :src="tu" />
        </template>
        <a-card-meta :title="item.userName">
          <template #description>{{ item.userProfile }}</template>
        </a-card-meta>
      </a-card>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import tu from "../assets/dingzhen.jpg";

interface Props {
  userList: any[];
}

const props = withDefaults(defineProps<Props>(), { userList: () => [] });
</script>
<style scoped></style>
