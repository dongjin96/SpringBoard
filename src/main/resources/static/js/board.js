function bdelete(b_num){

$.ajax({
    url:"/board/boarddelete",
    data:{"b_num":b_num},
    success: function(result){
    if(result==1){
    location.href="/board/boardlsit";
    }else{
    alert("이미삭제거나 오류발생")
    }
    }
});
}