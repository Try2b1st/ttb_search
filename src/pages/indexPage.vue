<template>
  <div class="index-page">
    <a-auto-complete
      v-model:value="searchText"
      :dropdown-match-select-width="252"
      style="width: 85%"
      placeholder="input here"
      :options="suggests"
      @select="onSearch"
      @search="getSuggests"
    />
    <a-button type="primary" style="width: 15%" @click="onSearch(searchText)">
      Search
    </a-button>

    <MyDivider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostList :post-list="postList" />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <Picture :picture-list="pictureList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <User :user-list="userList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostList from "@/components/PostList.vue";
import Picture from "@/components/PictureList.vue";
import User from "@/components/UserList.vue";
import MyDivider from "@/components/MyDivider.vue";
import { useRoute, useRouter } from "vue-router";
import MyAxios from "@/plugins/MyAxios";

const postList = ref([]);

const userList = ref([]);

const pictureList = ref([]);

const router = useRouter(); //操作使url改变
const route = useRoute(); //url改变页面
const activeKey = route.params.category;

const initSearchParam = {
  text: "",
  searchType: activeKey,
  pageSize: 10,
  pageNumber: 1,
};

const searchText = ref(route.query.text || "");

const searchParams = ref(initSearchParam);

const loadAllData = (params: any) => {
  const query = {
    ...params,
    searchText: params.text,
    searchType: "all",
  };
  MyAxios.post("/search/all", query).then((res: any) => {
    postList.value = res.postList;
    userList.value = res.userList;
    pictureList.value = res.pictureList;
  });
};

/**
 * 加载数据
 * @param params 参数
 */
const loadData = (params: any) => {
  const query = {
    ...params,
    searchText: params.text,
  };
  MyAxios.post("/search/all", query).then((res: any) => {
    if (query.searchType === "post") {
      postList.value = res.dataList;
    } else if (query.searchType === "user") {
      userList.value = res.dataList;
    } else if (query.searchType === "picture") {
      pictureList.value = res.dataList;
    }
  });
};

loadAllData(initSearchParam);

watchEffect(() => {
  searchParams.value = {
    ...searchParams.value,
    text: route.query.text,
    searchType: route.params.category,
  } as any;
  loadData(searchParams.value);
});

const suggests = ref<any[]>([]);

const getSuggests = (value: string) => {
  suggests.value = [];
  if (value) {
    MyAxios.get("/post/search/select?keyword=" + value).then((res: any) => {
      for (let i = 0; i < res.length; i++) {
        const tempMap = {
          value: res[i],
        };
        suggests.value.push(tempMap);
      }
    });
  }
};

const onSearch = (value: any) => {
  router.push({
    query: {
      ...searchParams.value,
      text: value,
    },
  });
};

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: {
      ...searchParams.value,
      searchType: key,
    },
  });
};
</script>
