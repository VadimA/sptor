<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div id="test">
    <p>{{ message }}</p>
    <input v-model="message">
</div>

<script src="https://unpkg.com/vue"></script>
<script type="text/javascript">

var test = new Vue({
el: '#test',
data: {
message: 'It is a planning page!' + + new Date().toLocaleString()
}
})
</script>
</body>
</html>
