<template>
  <v-app>
    <v-main>
      <Todo />
    </v-main>
  </v-app>
</template>

<script>
import Todo from "./components/Todo.vue";

export default {
  name: "App",

  components: {
    Todo,
  },

  data: () => ({
    //
  }),
};
</script>
