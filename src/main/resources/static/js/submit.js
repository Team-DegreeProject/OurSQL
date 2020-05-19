var Record = document.getElementById("record");
var Command = document.getElementById("command");
var Command_sub = document.getElementById("command_sub");
window.onload = function() {
    Command_sub.onclick = function () {
        //定义空字符串
        var str = "";
        if (Command.value == "") {
            // 消息为空时弹窗
            alert("massage can not be empty");
            return;
        }
        str = '<div class="guest"><span>' + Command.value + '</span></div>';
        Record.innerHTML = Record.innerHTML + str;
        $.post("/Guest/command",
            {
                command: Command.value
            },
            function(result){
                if(result===""){
                    str = '<div class="system"><span>' + "Error!" + '</span></div>';
                    Record.innerHTML = Record.innerHTML + str;
                }else if(typeof (result)=="number"){
                    if(result==0){
                        str = '<div class="system"><span>' + "Done!" + '</span></div>';
                        Record.innerHTML = Record.innerHTML + str;
                    }else if(result==-1){
                        str = '<div class="system"><span>' + "Error!" + '</span></div>';
                        Record.innerHTML = Record.innerHTML + str;
                    }else{
                        str = '<div class="system"><span>' + result + '</span></div>';
                        Record.innerHTML = Record.innerHTML + str;
                    }
                }else if(typeof (result)=="string"){
                    str = '<div class="system"><span>' + result + '</span></div>';
                    Record.innerHTML = Record.innerHTML + str;
                }else{
                    str = '<div class="system"><span><table border="1">'
                    for (var i = 0; i < result.length; i++) {
                        str=str+"<tr>";
                        for (var k in result[i]) {
                            str=str+"<td>"+result[i][k]+"</td>";
                        }
                        str=str+'<tr>';
                    }
                    str=str+'</table></span></div>';
                    Record.innerHTML = Record.innerHTML + str;
                }
            });

    }
};
