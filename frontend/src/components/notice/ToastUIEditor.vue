<template>
    <div ref="refEditor">Loading...</div>
</template>
<script setup>
import "@toast-ui/editor/dist/toastui-editor.css"; // Editor's Style

import Editor from "@toast-ui/editor";
import { ref, onMounted } from "vue";

const refEditor = ref(null); // template의 ref의 값과 동일한 변수 선언

onMounted( () => { 
    console.log("onMounted")
    
    console.log("====" + refEditor.value)
    const xeditor = new Editor({
        el: refEditor.value,
        height: "500px",
        initialEditType: "markdown",
        previewStyle: "vertical",
    });
})
</script>