<template>
  文章列表
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta>
          <template #description>
            <p v-html="item.content"></p>
          </template>
          <template #title>
            <p v-html="item.title"></p>
          </template>
          <template #avatar>
            <a-avatar :src="tu" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import tu from "../assets/2.jpg";

interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), { postList: () => [] });
</script>
<style scoped>
.tu {
  width: 200px;
}
</style>
