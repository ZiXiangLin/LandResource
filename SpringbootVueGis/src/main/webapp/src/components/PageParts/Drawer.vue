<template>
    <div class="Drawer">
      <div class="user-avatar" style="height: 150px;margin-top: 20px">
        <img class="logo" style="max-height: 95%;" src="../../assets/avatar.jpg">
      </div>
      <p>{{this.$store.state.user.username}}</p>
      <p>{{value}}</p>
      <div>

      </div>
    </div>
</template>

<script>
    export default {
      name: "Drawer",
      data() {
        return {
          value: new Date()
        }
      }
    }
</script>

<style scoped>
  .Drawer {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
  }
</style>
