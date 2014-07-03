//重置
function reset(){
	$(".js_searchBox").find("input[type='text']").val("");
	$(".js_searchBox").find("input[type='checkbox']").removeAttr("checked");
	$(".js_searchBox").find("select option[value="+0+"]").attr("selected", true);
}
$(".js_reset").bind("click",function(){
	reset();
});