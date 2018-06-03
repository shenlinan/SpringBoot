var totalCnt;
var pageIndex = 1;
var pageSize = 10;


$("#inComeSearch").click(function(){
	loadExpenditureList();
	loadPager();
});

loadNameList("selectDiv-search","btn-default","userSelectSearch");
loadExpenditureList();
loadPager();
function submitInCome()
{
	var userId = $("#userSelect").val();
	var inCome = $("#inCome").val().trim();
	if(userId === "0")
	{
		alert("请选择用户。");
		return;
	}
	
	$.ajax({ 
	    type : "POST", 
	    url : "/saveInCome",
	    data : {"userId":userId,"inCome":inCome},
	    success : function(result) {
	    	if(result == "SUCCESS")
	    	{
	    		alert("保存成功。");
	    	}
	    } 
	});
}

function submitExpenditure()
{
	var userId = $("#userSelect").val();
	var expenditure = $("#expenditure").val().trim();
	var useage = $("#useage").val().trim();
	if(userId === "0")
	{
		alert("请选择用户。");
		return;
	}
	
	$.ajax({
	    type : "POST", 
	    url : "/saveExpenditure",
	    data : {"userId":userId,"expenditure":expenditure,"useage":useage},
	    success : function(result) {
	    	if(result == "SUCCESS")
	    	{
	    		alert("保存成功。");
	    	}
	    } 
	});
}

$("#expenditureList").click(function(){
	$("#selectDiv").addClass('hide');
	$("#expenditureDiv").addClass('hide');
	loadExpenditureList();
	$("#detail").show();
});



$("#inComeList").click(function(){
	$.ajax({
	    type : "POST", 
	    url : "/loadInComeList",
	    data : {},
	    success : function(result) {
	    	
	    	var tbContent = "<thead>" +
	    			"<tr><th>姓名</th>" +
	    			"<th>收入金额（元）</th>" +
	    			"<th>收入时间</th></tr>" +
	    			"</thead>";
	    	if(null != result)
	    	{
	    		for (var i = 0; i < result.length; i++) 
	    		{
	    			
	    			tbContent += " <tr><td>"+result[i].name+"</td><td><span class=\"glyphicon glyphicon-yen\"></span>"+result[i].inCome+"</td><td>"+result[i].incomedate+"</td></tr>";
				}
	    	}
	    	else
	    	{
	    		tbContent += " <tr><td>没有数据</td></tr>";
	    	}
	    	$(".table").empty().append(tbContent);
	    } 
	});
});

$("#expenditureRecord").click(function(){
	$("#detail").hide();
	loadNameList("selectDiv","btn-default","userSelect");
	$("#selectDiv").removeClass('hide');
	$("#expenditureDiv").removeClass('hide');
});

$("#inComeRecord").click(function(){
	
});


function loadPager()
{
	$("#page-container-static-normal").page({
	    count:totalCnt,
	    theme:"normal"
	});
	$("#page-container-static-normal").on("pageChanged",function (event,params) {
	   pageIndex = params.pageNum;
	   loadExpenditureList();
	   $(this).data("page").refresh(params);
	})
}

function loadNameList(selectDivId,dataStyle,selectId)
{
	$.ajax({ 
	    type : "GET",
	    url : "/listUsers",
	    data : {},
	    async : false,
	    success : function(result) { 
	    var selectList = "<select id = "+selectId+" class=\"selectpicker\" data-style="+dataStyle+">  <option value =\"0\" selected = \"selected\">请选择姓名</option>";
		for(var i=0,l=result.length;i<l;i++)
		{
			selectList += "<option value ="+result[i].id+">"+result[i].name+"</option>";
	    }
		selectList +="</select>";
	    $("#"+selectDivId).empty().append(selectList);
	    $('.selectpicker').selectpicker(); 
	  } 
	}); 
}
function loadExpenditureList()
{
	var useageSearch = $("#useage-search").val().trim();
	var userIdSearch = $("#userSelectSearch").val();
	$.ajax({
	    type : "POST", 
	    url : "/loadExpenditureList",
	    data : {"useageSearch":useageSearch,"userIdSearch":userIdSearch,"pageIndex":pageIndex,"pageSize":pageSize},
	    async : false,
	    success : function(result) {
	    	
	    	var tbContent = "<thead>" +
	    			"<tr><th>姓名</th>" +
	    			"<th>支出金额（元）</th>" +
	    			"<th>用途</th>" +
	    			"<th>支出时间</th></tr>" +
	    			"</thead>";
	    	if(null != result)
	    	{
	    		var resultJson = $.parseJSON(result);
	    		totalCnt = resultJson.totalCnt;
	    		var expenditureList = resultJson.Result
	    		for (var i = 0; i < expenditureList.length; i++) 
	    		{
	    			tbContent += " <tr><td>"+expenditureList[i].name+"</td><td><span class=\"glyphicon glyphicon-yen\"></span>"+expenditureList[i].expenditure+"</td><td>"+expenditureList[i].useage+"</td><td>"+expenditureList[i].paydate+"</td></tr>";
				}
	    	}
	    	else
	    	{
	    		tbContent += " <tr><td>没有数据</td></tr>";
	    	}
	    	$(".table").empty().append(tbContent);
	    } 
	});
}