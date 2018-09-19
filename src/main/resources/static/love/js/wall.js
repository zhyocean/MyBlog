/**************************************************
* * 祝福墙模块
* * 2008-7-18
* **************************************************
* * msn:danxinju@hotmail.com
* * author:淡新举
***************************************************/
//创建对象
var wall = {
	backObj : null ,	//存储前一个对象
	backOrder : 0 ,	//存储前一个对象序列
	objs : new Array() //对象数组
};

//根据class属性返回对象
wall.getElementsByClassName = function(className){
	var alls = document.getElementsByTagName("*");
	var rets = new Array();

	for (var i=0;i<alls.length ; i++)
	{
		if (alls[i].className == className)
		{
			rets.push(alls[i]);
		}
	}

	return rets;
};

//使小窗口可拖动
wall.makeDrag = function(){
	for (var i=0;i<this.objs.length ; i++)
	{
		Drag.init(this.objs[i]);
		this.objs[i].onDragStart = function(x ,y){
			wall.sort(this);
		};
		this.objs[i].getElementsByTagName("a")[0].onclick = function(){
			wall.close(this);
		};
	}
};

//存储对象
wall.saveObjs = function(){
	this.objs = this.getElementsByClassName("wall");
}

//小窗口排序
wall.sort = function(obj){
	if (this.backObj)
	{
		this.backObj.style.zIndex = this.backOrder;
	}
	this.backObj = obj;
	this.backOrder = obj.style.zIndex;
	obj.style.zIndex = (this.objs.length + 1);
};

//关闭小窗口
wall.close = function(obj){
	//移除dom接点
	document.body.removeChild(obj.parentNode.parentNode);
}

//装载wall方法
wall.loadMethod = function(){
	this.saveObjs();	//存储对象
	this.makeDrag();	//使小窗口拖动
};

//查询显示
wall.search = function(){
	var txt = document.getElementById("txt").value;
	if (txt == "")
	{
		return false;
	}
	var spans = this.getElementsByClassName("inline");
	for (var i=0;i<spans.length ;i++)
	{
		if (spans[i].innerHTML != txt)
		{
			spans[i].parentNode.parentNode.style.display = "none";
		}
		else
		{
			spans[i].parentNode.parentNode.style.display = "";
		}
	}
};

//调用wall方法
window.onload = function(){
	wall.loadMethod();
};