<script>
import { ref, onMounted } from "vue";

export default {
  name: "To-do",
  props: {},
  setup() {
    const title = ref(null);
    const desc = ref(null);

    onMounted(() => {
      title.value.focus();
    });

    return {
      title,
      desc,
    };
  },
  data() {
    return {
      todos: null,
    };
  },
  async created() {
    await fetch("http://localhost:8081/api/todo")
      .then((res) => res.json())
      .then((data) => (this.todos = data));
  },
  computed: {},
  methods: {
    async update() {
      await fetch("http://localhost:8081/api/todo")
        .then((res) => res.json())
        .then((data) => (this.todos = data));

      this.title.reset();
      this.desc.reset();
      this.title.focus();
    },
    async addTodo() {
      if (!this.$refs.title.value || !this.$refs.desc.value) {
        console.log("You need to provide a Title and Description");
        return false;
      }

      await fetch("http://localhost:8081/api/todo", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: this.$refs.title.value,
          desc: this.$refs.desc.value,
        }),
      }).then(() => this.update());
    },
    async doneTodo(id) {
      await fetch(`http://localhost:8081/api/todo/${id}`, {
        method: "PUT",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: id,
          status: "COMPLETED",
        }),
      }).then(() => this.update());
    },
    async removeTodo(id) {
      await fetch(`http://localhost:8081/api/todo/remove/${id}`, {
        method: "PUT",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }).then(() => this.update());
    },
    isCompleted(status) {
      return status === "COMPLETED";
    },
  },
};
</script>

<template>
  <div class="todoContainer">
    <div class="todoList">
      <div class="pageTitle">Spring-Boot & Vue3 Todo List</div>

      <div class="todoListItem" v-for="todo in todos" :key="todo.id">
        <div class="todo" :class="{ todoCompleted: isCompleted(todo.status) }">
          <div class="todoTitle">
            <span :class="{ completed: isCompleted(todo.status) }">{{
              todo.title
            }}</span>
          </div>
          <div class="todoDesc">
            <span :class="{ completed: isCompleted(todo.status) }">{{
              todo.desc
            }}</span>
          </div>
          <div class="todoActions">
            <v-btn
              v-if="todo.status === 'NOT_STARTED'"
              @click="doneTodo(todo.id)"
              variant="outlined"
              class="doneBtn"
              >Done</v-btn
            >
            <v-btn
              @click="removeTodo(todo.id)"
              variant="outlined"
              class="removeBtn"
              >Remove</v-btn
            >
          </div>
        </div>
      </div>
      <div class="todo addTodo">
        <div class="todoTitle">
          <v-text-field
            label="title"
            variant="outlined"
            ref="title"
            class="todoTitle"
          />
        </div>
        <div class="todoDesc">
          <v-text-field
            label="description"
            variant="outlined"
            ref="desc"
            class="todoDesc"
          ></v-text-field>
        </div>
        <div class="todoActions">
          <v-btn @click="addTodo" variant="outlined" class="addBtn">Add</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "styles.css";
</style>
