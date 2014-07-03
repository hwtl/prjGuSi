/*
common.js
*/

(function(){window["undefined"]=window["undefined"];String.isInstance=function(_string){return(typeof(_string)==="string");};String.isEmpty=function(str){return(typeof(str)==="undefined"||str===null||(str.length===0));};String.isNotEmpty=function(str){return(!String.isEmpty(str));};String.prototype.trim=function(){return this.replace(/(^[\s]*)|([\s]*$)/g,"");};String.prototype.getAttribute=function(name){var reg=new RegExp("(^|;|\\s)"+name+"\\s*:\\s*([^;]*)(\\s|;|$)","i");if(reg.test(this)){return RegExp.$2.replace(/[\x0f]/g,";");};return null;};String.prototype.cnLength=function(){return((this.replace(/[^x00-xFF]/g,"**")).length);};Array.isInstance=function(obj){return Object.prototype.toString.call(obj)==='[object Array]';};Array.prototype.add=function(o){this.push(o);};Array.prototype.indexOf=function(o){for(var i=0,len=this.length;i<len;i=i+1){if(this[i]!=null&&typeof(this[i].equals)=='function'&&this[i].equals(o)){return i;};if(this[i]==o){return i;}};return -1;};Array.prototype.equals=function(_array){if(this==_array){return true;};if(!Array.isInstance(_array)){return false;};if(this.length!=_array.length){return false;};for(var i=0,len=this.length;i<len;i=i+1){var o1=this[i];var o2=_array[i];if(o1!=o2){if(!(typeof(o1.equals)=='function'&&o1.equals(o2))){return false;}}};return true;};Array.prototype.remove=function(o){var index=this.indexOf(o);if(index!=-1){this.splice(index,1);return true;}else{return false;}};Array.prototype.contains=function(o){return this.indexOf(o)!=-1;};Array.prototype.containsAll=function(oArray){if(this==oArray){return true;};for(var i=0;i<oArray.length;i=i+1){var o=oArray[i];if(!this.contains(o)){return false;}};return true;};Date.isInstance=function(obj){return(Object.prototype.toString.call(obj)==='[object Date]');};Function.isInstance=function(obj){return Object.prototype.toString.call(obj)==='[object Function]';};Number.isInstance=function(obj){return Object.prototype.toString.call(obj)==='[object Number]';};Boolean.isInstance=function(obj){return Object.prototype.toString.call(obj)==='[object Boolean]';};})();



/******************************页面 JS Tree 的实现*****************************/
/*
 * 
 * Copyright (c) 2009 YaoYiLang 
 * @email  redrainyi@gmail.com
 * @datetime 2009-10-01 12:11:08
 * @version 2.0
 */

getUniqueID = function(){
	return ('_' + (new Date().getTime()) + '_' + parseInt(Math.random()*10000));
};


TreePanel = function(config){//参数初始化
	this.nodeHash = {};
	this.nodeHashT = {};
	this.root = null;//根节点
	this._id = getUniqueID();//如果数组中没有id则自动生成随机id，否则通过数组获得
	this.clickListeners = [];
	this.element = document.createElement('ul');//倒数第二层容器
	this.element.className='TreePanel_List';
	this.container = null;//包装树的容器
	this.focusNode = null;//当前节点
	this.on=this.addListener;
	this.initialize.apply(this, arguments);
	this.clickGetChkedID = null;//点击节点时选中的叶节点ID串
	this.clickGetChkedTxt = null;//点击节点时选中的叶节点文本串
};

TreePanel.prototype={
	initialize : function(config){//初始化树
		var renderTo = config['renderTo'];//最外层容器的id
		this.container = (String.isInstance(renderTo) ? document.getElementById(renderTo) : renderTo ) || document.body;
		this.config = config;
		var handler= config['handler'];//自定义事件绑定？
		if(Function.isInstance(handler)){
			this.addListener('click',handler);
		}

		var nodeUrl = config['nodeUrl'] || null;//设置nodeUrl，则为链接树(有链接，无checkbox)
		var isChk = config['isChk'] || null;//设置isChk则为选项树(无链接，有checkbox)
		var isOnlyShow = config['isOnlyShow'] || null;//isChk配合isOnlyShow使用则为展示树（无链接，无checkbox）
		var canDept = config['canDept'] || false; //设置可否选组织
		var canStaff = config['canStaff'] || false; //设置可否选员工
		var node = new TreeNode(config.root, nodeUrl, isChk, isOnlyShow, canDept, canStaff, this);
		this.setRootNode(node);
		
	},
	pathSeparator: "/",
	getRootNode : function(){//获取根节点
		return this.root;
	},
	setRootNode : function(node){//设置根节点
		this.root = node;
		node.ownerTree = this;
		//this.root.setOwnerTree(this);
		this.registerNode(node);
		node.cascade((function(node){
			this.registerNode(node);
		}),this);
	},
	getNodeById : function(id){//根据id获得节点
		return this.nodeHash[id];
	},
	getNodeByTxt : function(text){//根据text获得节点
		return this.nodeHashT[text];
	},
	//根据ID设置默认状态
	setFormat : function(id){
		this.getNodeBy(id).setFormat();
	},
	registerNode : function(node){//通过id或text注册节点
		this.nodeHash[node.id] = node;
		this.nodeHashT[node.text] = node;
	},
	unregisterNode : function(node){//注销节点
		delete this.nodeHash[node.id];
		delete this.nodeHashT[node.text];
	},
	render : function(){//绘制树，并绑定事件
		this.element.innerHTML = '';
		this.root.render();
		if(this.container){
			this.container.appendChild(this.element);
		}
		this.initEvent();
	},
	initEvent : function(){//事件绑定
		var _this = this;
		this.element.onclick=function(event){
			var event = event || window.event;
			var elem=(event.srcElement || event.target);
			var _type = elem['_type_'];
			if(typeof(_type) === undefined){
				return;
			}
			elem = elem.parentNode || elem.parentElement;
			if(_type == 'switch'){//伸缩手柄
				if(elem.indexId!=null){
					var node = _this.nodeHash[ elem.indexId ];
					if(node.isExpand){
						node.collapse();
					}else{
						node.expand();
					}
				}
			}else if(_type == 'text'){//文本
				elem = elem.parentNode || elem.parentElement;
				var node = _this.nodeHash[elem.indexId];
				for(var i=0; i < _this.clickListeners.length; i++){
					_this.clickListeners[i](node);
				}
				_this.setFocusNode(node);
			}else if(_type == 'link'){//链接
				var node = _this.nodeHash[elem.indexId];
				for(var i=0; i < _this.clickListeners.length; i++){
					_this.clickListeners[i](node);
				}
				_this.setFocusNode(node);
			}else if(_type == 'checkbox'){//checkbox
				elem = elem.parentNode || elem.parentElement;
				var node = _this.nodeHash[elem.indexId];
				node.onCheck();
				for(var i=0; i < _this.clickListeners.length; i++){
					_this.clickListeners[i](node);
				}
			}
		};
	},
	
	getChecked : function(name){//获取选中项，返回id数组
		var checkeds = [];
		name = name||'id';
		for(var k in this.nodeHash){
			var node = this.nodeHash[k];
			if(node.checked==1 && node.isLeaf()){//只取叶节点
				var value = node.attributes[name]
				if(value != null){
					checkeds.push(value);
				}
			}
		}
		return checkeds;
	},
	getCheckedTxt : function(name){//获取选中项，返回id数组
		var checkeds = [];
		name = name||'text';
		for(var k in this.nodeHashT){
			var node = this.nodeHashT[k];
			if(node.checked==1 && node.isLeaf()){//只取叶节点
				var value = node.attributes[name]
				if(value != null){
					checkeds.push(value);
				}
			}
		}
		return checkeds;
	},
	getCheckedAll : function(name){//获取选中项，返回id数组（含选中的父节点，从上到下所有子孙节点）
		var checkeds = [];
		name = name||'id';
		for(var k in this.nodeHash){
			var node = this.nodeHash[k];
			if(node.checked==1 && node.checked!=2){
				var value = node.attributes[name]
				if(value != null){
					checkeds.push(value);
				}
			}
		}
		return checkeds;
	},
	getCheckedAllTxt : function(name){//获取选中项，返回id数组（含选中的父节点，从上到下所有子孙节点）
		var checkeds = [];
		name = name||'text';
		for(var k in this.nodeHashT){
			var node = this.nodeHashT[k];
			if(node.checked==1 && node.checked!=2){
				var value = node.attributes[name]
				if(value != null){
					checkeds.push(value);
				}
			}
		}
		return checkeds;
	},
	
	addListener : function(type,handler){
		if(Function.isInstance(type)){
			handler=type;
			type === 'click'
		}
		this.clickListeners.push(handler);	
	},
	setFocusNode : function(node){//当前选中项设置高亮显示
		if(this.focusNode){
			this.focusNode.unselect();
		}
		this.focusNode = node;
		if(node.parentNode){
			node.parentNode.expand();
		}
		node.select();
	},
	toString : function(){
		return "[Tree"+(this.id?" "+this.id:"")+"]";
	}
};

TreeNode=function(attributes, nodeUrl, isChk, isOnlyShow, canDept, canStaff, tree) {
	this['attributes'] = attributes || {};
	this['html-element'] = false;//null
	if(!attributes.id){
		attributes.id = getUniqueID();
	}
	this.id = attributes.id;
	this.text = attributes.text;
	this.parentNode = null;
	this.childNodes = [];
	this.lastChild = null;
	this.firstChild = null;
	this.previousSibling = null;
	this.nextSibling = null;
	this.childrenRendered = false;
	this.StaffRendered = false;
	this.isExpand = false;
	this.nodeUrl = nodeUrl;
	this.isChk = isChk;
	this.isOnlyShow = isOnlyShow;
	this.canDept = canDept;
	this.canStaff = canStaff;
	this.isStaff = attributes.isStaff || null;
	this.pid = attributes.pid || null;
	this.tree = tree;

	this.leaf = this.attributes.leaf;
	var children = attributes.children || [];
	var config = this.tree.config;
	for(var i=0,j=children.length;i<j;i++){
		if(this.isNeedShow(children[i], config)){
			this.appendChild(new TreeNode(children[i], this.nodeUrl, this.isChk, this.isOnlyShow, this.canDept, this.canStaff, this.tree));
		}
	}
}

TreeNode.prototype={
	isNeedShow: function(node, config){
		if( !config.status ){
			return true;
		}
		
		if(config.status.indexOf(node.status) >= 0 ){
			return true;
		}
		
		return false;
	},
	initEl : function(level){//生成节点元素
		this['html-element']={};
		this['html-element']['element'] = document.createElement('li');
		$(this['html-element']['element']).attr('status', this['attributes']['status']);
		this['html-element']['element'].className="L"+level;;
		this['html-element']['switch']	 = document.createElement('span');
		this['html-element']['switch'].className="in_block switch";
		//if(this.canDept !=null && this.canStaff == null || this.canDept !=null && this.canStaff != null){
		if(this.canDept && !this.canStaff || this.canDept && this.canStaff){
			//只能选组织，或组织和员工同时可选
			if(this.nodeUrl != null){//链接树
				this.tree_link();
			}
			else if(this.isChk != null){//选项树
				this.tree_chk();
			}
		}
		else{//只能选员工，不能选组织
			if(this.isStaff !=null){//员工
				if(this.nodeUrl != null){//链接树
					this.tree_link();
				}
				else if(this.isChk != null){//选项树
					this.tree_chk();
				}
			}
			else{//非员工
				this.tree_chk(true);//展示树
			}
		}
		this['html-element']['child'].style.display='none';
	},
	tree_link : function(){//绘制链接树
		this['html-element']['link']	 = document.createElement('a');
		this['html-element']['link'].className='TreeNode_new in_block';
		if(this.isStaff != null){
			this['html-element']['link'].className='TreeNode_new in_block isStaff';
			this['html-element']['link'].name=this.pid;
		}
		this['html-element']['child'] = document.createElement('ul');
		this['html-element']['element'].appendChild(this['html-element']['switch']);
		this['html-element']['element'].appendChild(this['html-element']['link']);
		this['html-element']['element'].appendChild(this['html-element']['child']);
		this['html-element']['element'].noWrap='true';
		this['html-element']['switch']['_type_'] ='switch';
		this['html-element']['link']['_type_'] ='link';
		//this['html-element']['text']['_type_'] ='text';
	},
	tree_chk : function(mustBe){//绘制选项树
		var mustBe = mustBe || null;
		this['html-element']['wrap'] = document.createElement('span');
		this['html-element']['wrap'].className = 'TreeNode_new in_block';
		this['html-element']['checkbox'] = document.createElement('span');
		this['html-element']['checkbox'].className = "t_checkbox in_block";
		this['html-element']['text'] = document.createElement('span');
		if(this.isOnlyShow == true || mustBe){//展示树，无checkbox
			this['html-element']['checkbox'].style.display = "none";
		}
		this['html-element']['child'] = document.createElement('ul');
		this['html-element']['element'].appendChild(this['html-element']['switch']);
		this['html-element']['element'].appendChild(this['html-element']['wrap']);
		this['html-element']['wrap'].appendChild(this['html-element']['checkbox']);	
		this['html-element']['wrap'].appendChild(this['html-element']['text']);	
		this['html-element']['element'].appendChild(this['html-element']['child']);

		this['html-element']['element'].noWrap='true';
		this['html-element']['switch']['_type_'] ='switch';
		this['html-element']['wrap']['_type_'] ='wrap';
		this['html-element']['text']['_type_'] ='text';
		this['html-element']['checkbox']['_type_'] ='checkbox';
	},
	render : function(){
		if(!this['html-element']){
			this.initEl(this.getDepth());
		}
		if(this.isRoot()){
			this.ownerTree.element.appendChild(this['html-element']['element']);
			this.expand();
		}else{
			this.parentNode['html-element']['child'].appendChild(this['html-element']['element']);
		}
		this['html-element']['element'].indexId = this.id;
		this.paintPrefix();
	},
	paintPrefix : function(){//绘制节点内容（伸缩手柄、复选框、节点文本等）
		this.paintClipIcoImg();
		if(this.isChk == true){
			this.paintCheckboxImg();
		}
		this.paintText();
	},
	paintClipIcoImg : function(){//绘制伸缩手柄
		if(this.isRoot()){
			this['html-element']['switch'].style.display='none';//不显示根节点的clip
			return;
		}
		//var ownerTree = this.getOwnerTree();
		var icon = '';
		if(this.isRoot()){
			icon = this.isExpand ? 'switch open':'switch';
		}else{
			if(this.isLeaf()){//叶节点
				if(this.canStaff){//显示员工（非末节点）
					if(this.isStaff != true){
						if(this.isExpand){ //展开
							icon = "switch open"
						}else{ //折叠
							icon = "switch"
						}
					}
					else{//员工节点
						icon = "";
					}
				}
				else{//不显示员工（末节点）
					icon = "";
				}
			}else{ //非叶节点
				if(this.isExpand){ //展开
					icon = "switch open"
				}else{ //折叠
					icon = "switch"
				}
			}
		};
		this['html-element']['switch'].className = icon;
	},
	paintCheckboxImg : function(){//绘制复选框
		var ownerTree = this.getOwnerTree();
		var checked = this.checked;
		var type = this['attributes']['type'];
		if(this['html-element']){
			this['html-element']['checkbox'].className = ((checked==2)?'t_checkbox in_block t_chk2':(checked==1)?'t_checkbox in_block t_chk1':'t_checkbox in_block t_chk0');
			this['html-element']['checkbox'].id = this['html-element']['element'].indexId;
			this['html-element']['text'].className = (checked==2 || checked==1)?'in_block chkedG':'in_block';
			if(this.isStaff == null){//非员工节点，标注节点类型：部门、区域、门店、分行
				this['html-element']['checkbox'].common = type;
			}
		}
	},
	paintText : function(){//绘制节点文本
		var text = this['attributes']['text'];
		var type = this['attributes']['type'];
		if(this.nodeUrl != null){//链接树
			if(this['html-element']['link']){
				this['html-element']['link'].href=this.nodeUrl;
				this['html-element']['link'].title = text;
				this['html-element']['link'].id = this['html-element']['element'].indexId;
				this['html-element']['link'].innerText = text;
				this['html-element']['link'].textContent = text;
				if(this.isStaff == null){//非员工节点，标注节点类型：部门、区域、门店、分行
					this['html-element']['link'].common = type;
				}
			}
			if(this['html-element']['text']){
				this['html-element']['text'].id = this['html-element']['element'].indexId;
				this['html-element']['text'].innerText = text;
				this['html-element']['text'].textContent = text;
				if(this.isStaff == null){//非员工节点，标注节点类型：部门、区域、门店、分行
					this['html-element']['text'].common = type;
				}
			}
		}
		else if(this.isChk != null){//选项树
			this['html-element']['text'].title = text;
			this['html-element']['text'].innerText = text;
			this['html-element']['text'].textContent = text;
		}
		else{//展示树
			this['html-element']['text'].id = this['html-element']['element'].indexId;
			this['html-element']['text'].title = text;
			this['html-element']['text'].innerText = text;
			this['html-element']['text'].textContent = text;
		}
	},
	paintChildren : function(){//绘制子节点
		//this['html-element']['child'].innerHTML = '';
		this.childrenRendered = true;
		var childNodes = this.childNodes;
		for(var i=0;i < childNodes.length;i++){
			childNodes[i].render();
		}
	},

	paintStaff : function(focusNodeID){
		var _this = this;
		var URL = "http://192.168.0.120/oms/api/findEmpByOrgId?orgId="+_this.id;
		URL +=  (_this.getOwnerTree().config.loadEmployeesConfig === 'filterPublic') ? '&normal=true' : '';

		jQuery.getJSON(URL + "&jsoncallback=?", function(data){//Ajax跨域访问
			for(var i=data.emps.length-1; i>=0; i--){
				data.emps[i].pid = _this.id;
				var node = new TreeNode(data.emps[i], _this.nodeUrl, _this.isChk, _this.isOnlyShow, _this.canDept, _this.canStaff);
				_this.insertBefore(node, _this.childNodes[0]);//加在子部门前面
				//_this.appendChild(node); //加在子部门后面
				
			};
			for(var i=0;i < _this.childNodes.length;i++){
				_this.childNodes[i].render();
			}
			_this.staffRendered = true;
			_this.isExpand=true;

			_this['html-element']['child'].style.display='block';
			if(focusNodeID != null  && $("#"+focusNodeID).length != 0){
				$("ul.TreePanel_List a.current").removeClass("current");
				$("#"+focusNodeID).addClass("current");//当前选项高亮
				setPos(focusNodeID);//移动到可见位置
			}
			
			_this.paintClipIcoImg();
		});
	},
	collapse : function(){//收起
		this.isExpand=false;
		this['html-element']['child'].style.display='none';
		//this.paintIconImg();
		this.paintClipIcoImg();
	},
	expand : function(focusNodeID){//展开
		if(!this.childrenRendered && !this.isLeaf() && this.childNodes.length>0){//绘制子节点（部门）
			this.paintChildren();
		}
		if(!this.staffRendered && this.canStaff){//绘制子节点（员工）
			this.paintStaff(focusNodeID);
		}
		else{
			this['html-element']['child'].style.display='block';
			if(focusNodeID != null && $("#"+focusNodeID).length != 0){
				$("ul.TreePanel_List a.current").removeClass("current");
				$("#"+focusNodeID).addClass("current");//当前选项高亮
				setPos(focusNodeID);//移动到可见位置
			}
			this.isExpand=true;
			
			this.paintClipIcoImg();
		}
	},
	select : function(){//选中
		this.isSelect = true;
		$("ul.TreePanel_List a.current").removeClass("current");
		if(this['html-element']['link']){
			this['html-element']['link'].className = this['html-element']['link'].className + " current";
		}
	},
	unselect : function(){//撤销选中
		this.isSelect = false;
		if(this['html-element']['link']){
			this['html-element']['link'].className = this['html-element']['link'].className.replace(" current", "");
		}
	},
	getEl :  function(){
		return this['html-element'];
	},
	setCheck : function(checked){//复选框状态切换
		if(checked==2||checked==3){
			var childNodes = this.childNodes;
			var count = childNodes.length;
			if(count==0){
				this.checked=checked==2?0:1;
			}else{
				var checked1 = 0;
				var checked2 = 0;
				for(var i=0;i<count;i++){
				var checked = childNodes[i].checked;
					if(checked==1){
						checked1++;
					}else if(checked==2){
						checked2++;
					}
				}
				this.checked = (childNodes.length==checked1) ? 1 : (checked1>0||checked2>0) ? 2 : 0;
			}
		}else{
			this.checked=checked;
		}
		this.paintCheckboxImg();
		
	},
	onCheck : function(){
		if(this.checked!==false){
			if(this.checked==1){
				this.cascade((function(checked){
					this.setCheck(checked);
					//search(); //检索数据；
				}),null,0);
				this.bubble((function(checked){
					this.setCheck(checked);
				}),null,2);
			}else{
				this.cascade((function(checked){
					this.setCheck(checked);
					//search();//检索数据；
				}),null,1);
				this.bubble((function(checked){
					this.setCheck(checked);
				}),null,3);
			}
		}
		this.getOwnerTree().clickGetChkedID =  this.getOwnerTree().getChecked();
		this.getOwnerTree().clickGetChkedTxt =  this.getOwnerTree().getCheckedTxt();
	},

	isRoot : function(){//是否根节点
		return (this.ownerTree!=null) && (this.ownerTree.root === this);
	},
	isLeaf : function(){//是否叶节点
		return this.childNodes.length===0;
		//return this.leaf === true;
	},
	isLast : function(){//是否末节点
		return (!this.parentNode ? true : this.parentNode.lastChild == this);
	},
	isFirst : function(){//是否首节点
		return (!this.parentNode ? true : this.parentNode.firstChild == this);
	},
	hasChildNodes : function(){//是否有子节点
		return !this.isLeaf() && this.childNodes.length > 0;
	},
	// private
	setFirstChild : function(node){//设置首节点
		this.firstChild = node;
	},
	//private
	setLastChild : function(node){//设置末节点
		this.lastChild = node;
	},
	appendChild : function(node){//添加子节点
		var multi = false;
		if(Array.isInstance(node)){
			multi = node;
		}else if(arguments.length > 1){
			multi = arguments;
		}
		if(multi){
			for(var i = 0, len = multi.length; i < len; i++) {
				this.appendChild(multi[i]);
			}
		}else{
			//>>beforeappend
			var oldParent = node.parentNode;
			//>>beforemove
			if(oldParent){
				oldParent.removeChild(node);
			}
			var index = this.childNodes.length;
			if(index == 0){
				this.setFirstChild(node);
			}
			this.childNodes.push(node);
			node.parentNode = this;
			//
			var ps = this.childNodes[index-1];
			if(ps){
				node.previousSibling = ps;
				ps.nextSibling = node;
			}else{
				node.previousSibling = null;
			}
			node.nextSibling = null;
			this.setLastChild(node);
			node.setOwnerTree(this.getOwnerTree());
			//>>append
			//if(oldParent) >>move

			if(node && this.childrenRendered){
				node.render();
				if(node.previousSibling){
					node.previousSibling.paintPrefix();
					//paintLine();
				}
			}
			if(this['html-element']){
				this.paintPrefix();
			}
			return node;//true
		}
	},
	removeChild : function(node){//移除子节点
		var index = this.childNodes.indexOf(node);
		if(index == -1){
			return false;
		}
		//>>beforeremove
		this.childNodes.splice(index, 1);
		if(node.previousSibling){
	  		node.previousSibling.nextSibling = node.nextSibling;
		}
		if(node.nextSibling){
	  		node.nextSibling.previousSibling = node.previousSibling;
		}
		if(this.firstChild == node){
	  		this.setFirstChild(node.nextSibling);
		}
		if(this.lastChild == node){
	  		this.setLastChild(node.previousSibling);
		}
		node.setOwnerTree(null);
		//clear
		node.parentNode = null;
		node.previousSibling = null;
		node.nextSibling = null;
		//>>remove UI
		if(this.childrenRendered){
			if(node['html-element']&&node['html-element']['element']){
				this['html-element']['child'].removeChild(node['html-element']['element'])	
			}
			if(this.childNodes.length==0){
				this.collapse();
			}
		}
		if(this['html-element']){
    		this.paintPrefix();
		}
		return node;
	},
	insertBefore : function(node, refNode){//插入节点
		if(!refNode){
			return this.appendChild(node);
		}
		//移动位置是自身位置(不需要移动)
		if(node == refNode){
			return false;
		}
		var index = this.childNodes.indexOf(refNode);
		var oldParent = node.parentNode;
		var refIndex = index;
		//是子节点，并且是向后移动
		if(oldParent == this && this.childNodes.indexOf(node) < index){
			refIndex--;
		}
		if(oldParent){
			oldParent.removeChild(node);
		}
		//设置节点间关系
		if(refIndex == 0){
			this.setFirstChild(node);
		}
		this.childNodes.splice(refIndex, 0, node);
		node.parentNode = this;
		var ps = this.childNodes[refIndex-1];
		if(ps){
			node.previousSibling = ps;
			ps.nextSibling = node;
		}else{
			node.previousSibling = null;
		}
		node.nextSibling = refNode;
		refNode.previousSibling = node;
		node.setOwnerTree(this.getOwnerTree());
		return node;
	},
	replaceChild : function(newChild, oldChild){//替换子节点
		this.insertBefore(newChild, oldChild);
		this.removeChild(oldChild);
		return oldChild;
	},
	indexOf : function(child){//子节点索引值
		return this.childNodes.indexOf(child);
	},
	getOwnerTree : function(){
		if(!this.ownerTree){
			var p = this;
			while(p){
				if(p.ownerTree){
					this.ownerTree = p.ownerTree;
					break;
				}
				p = p.parentNode;
			}
		}
		return this.ownerTree;
	},
	//获得节点深度
	getDepth : function(){
  	var depth = 0;
		var p = this;
		while(p.parentNode){
			depth++;
			p = p.parentNode;
		}
		return depth;
	},
  //private
	setOwnerTree : function(tree){
		if(tree != this.ownerTree){
			if(this.ownerTree){
				this.ownerTree.unregisterNode(this);
			}
			this.ownerTree = tree;
			var cs = this.childNodes;
			for(var i = 0, len = cs.length; i < len; i++) {
				cs[i].setOwnerTree(tree);
			}
			if(tree){
				tree.registerNode(this);
			}
		}
	},
	getPathNodes : function(){//返回节点路径
		var nodes = [];
		for(var parent=this; parent!=null; parent=parent.parentNode){nodes.push(parent);};
		return nodes.reverse();
	},
	//根据ID设置默认状态
	setFormat : function(){
		this.setCheck(true);
		var parent  =  this.parentNode;
		while(parent!=null)
		{
			parent.setCheck(2);
			parent.expand();
			parent = parent.parentNode;
		}
	},
	getPath : function(attr){
		attr = attr || "id";
		var p = this.parentNode;
		var b = [this['attributes'][attr]];
		while(p){
			b.unshift(p.attributes[attr]);
			p = p.parentNode;
    }
		var sep = this.getOwnerTree().pathSeparator;
		return sep + b.join(sep);
	},
	//冒泡(遍历所有父节点)
	bubble : function(fn, scope, args){
  	var p = this;
		while(p){
			if(fn.call(scope || p, args || p) === false){
	    	break;
			}
	    p = p.parentNode;
		}
	},
	//瀑布(遍历所有子节点)
	cascade : function(fn, scope, args){
		if(fn.call(scope || this, args || this) !== false){
			var cs = this.childNodes;
			for(var i = 0, len = cs.length; i < len; i++) {
				cs[i].cascade(fn, scope, args);
			}
    }
	},
	//查找
	findChild : function(attribute, value){
		var cs = this.childNodes;
    for(var i = 0, len = cs.length; i < len; i++) {
			if(cs[i].attributes[attribute] == value){
      return cs[i];
     }
		}
		return null;
	},
  findChildBy : function(fn, scope){
      var cs = this.childNodes;
      for(var i = 0, len = cs.length; i < len; i++) {
      	if(fn.call(scope||cs[i], cs[i]) === true){
      	    return cs[i];
      	}
      }
      return null;
  },
	sort : function(fn, scope){
		var cs = this.childNodes;
		var len = cs.length;
    if(len > 0){
			var sortFn = scope ? function(){fn.apply(scope, arguments);} : fn;
			cs.sort(sortFn);
			for(var i = 0; i < len; i++){
				var n = cs[i];
        n.previousSibling = cs[i-1];
        n.nextSibling = cs[i+1];
        if(i == 0){
        	this.setFirstChild(n);
         }
        if(i == len-1){
         	this.setLastChild(n);
				}
			}
		}
	},
  contains : function(node){
     var p = node.parentNode;
      while(p){
          if(p == this){
              return true;
          }
          p = p.parentNode;
      }
      return false;
  },
  toString : function(){
      return "[Node"+(this.id?" "+this.id:"")+"]";
  }
};

//当一个页面加载多颗树时， 给不同树的数据源取别名
var globleTreeConfig = {};
function aliasTreeDataSource(treeAlias, currentTreeJsonData){
	var resultTreeJsonData = {};
	$.extend(true, resultTreeJsonData, currentTreeJsonData);
	globleTreeConfig[treeAlias] = resultTreeJsonData;
}


/*************** 组织选择控件（模拟下拉框） *******************************************
/*
 * &param options:对象参数
 * &param options.canDept:    部门是否可选（带链接），true或false（缺省:false）
 * &param options.canStaff:   员工是否可选（带链接），true或false（缺省:false）
 * &param options.selBtn:     按钮对象，缺省为:$("#selectDept")
 * &param options.dataSource: 数据源，缺省为dooioo
 *                            可选值(别名，用于区分多数据源)：
 *                                globleTreeConfig["dooiooAll"]--全公司（正常、暂停）
 *                                globleTreeConfig["dooiooSales"]--营运中心（正常、暂停）
 *                                globleTreeConfig["dooiooSalesNoDept"]--营运中心不含门店（正常、暂停）
 *                                globleTreeConfig["dooiooSalesNoGroup"]--营运中心不含分行（正常、暂停）
 *                                globleTreeConfig["dooiooSupport"]--后台部门（除营运中心之外的所有部门）（正常、暂停）
 *                                globleTreeConfig["dooiooFull"]--全公司（正常、暂停、停用）
 *                                globleTreeConfig["dooiooNormall"]--全公司（正常）
 * &param options.callbacksel:    点选节点的回调函数(可缺省)
 * &param options.callbackunsel： 点击取消选中的回调函数(可缺省)--通过unSelect样式获取该对象
 * &param options.showStill:      点选节点后是否关闭弹层，true或false，（缺省:true）
****************************************************************************************/


/**--------------------初始化弹层组织架构树方法-----------------**/
var tree = null;
function selectDeptInit(params){
	var selBtn = params.selBtn || $("#selectDept");
	
	//初始化组织架构树
	selectPopTreeInit(params);
	
	//初始化自动完成搜索框
	var searchCtrl = $('#autoCByTree');
	if( searchCtrl.length > 0 ){
		initTreeSearch(params, searchCtrl);
	}

	//选择组织按钮--显示弹层
	selBtn.click(function(e){
		if($('#popup_tree').css("display") == "none"){
			salesTeamInit(params.dataSource || dooioo);//如果根节点是营运中心，则展开下面一层
			
			removeIE6Mask();
			resetSearch(searchCtrl);
			fnShowLayer(selBtn, $('#popup_tree'), 'right');
			showIE6Mask($('#popup_tree'));
			
			//当前节点定位
			setCurrentNode($(this));
		}
		stopBubble(e);//阻止冒泡
	});

	//点击弹层事件（选中节点）
	$("#popup_tree").click(function(e){
		deptPick(e.target, params)
		stopBubble(e);//阻止冒泡
	});

	//点击body隐藏组织架构选择器弹层
	$("body").click(function(){
		hideTreePopup();
	});
}

//初始化弹层中的树
function selectPopTreeInit(params){
	var canDept = params.canDept || false,
	    canStaff =params.canStaff || false,
		dataSource = params.dataSource || dooioo;
	tree = new TreePanel({
		renderTo:'treeWrap',
		nodeUrl:"#this",
		canDept:canDept,      //可选部门
		canStaff:canStaff,     //可选员工
		root:dataSource,
		loadEmployeesConfig: params.loadEmployeesConfig
	});
	tree.render();
}

//如果是营运中心，则展开下面一层
function salesTeamInit(root){
	if(root.id == "20495"){
		tree.getNodeById("22").expand();//展开三级市场事业部
		tree.getNodeById("20445").expand();//展开商业事业部
	}
}

//点选部门/员工｜取消选中
function deptPick(eHandle, params){
	var selBtn = params.selBtn || $("#selectDept"),
		callbacksel = params.callbacksel || null, 
		callbackunsel = params.callbackunsel || null,
		showStill = params.showStill || true;
	
	if($(eHandle).hasClass("TreeNode_new")){//点选部门/员工
		//获取文本
		var deptName = $.trim($(eHandle).text());
		selBtn.val(deptName);
		//获取ID
		var deptID = $.trim($(eHandle).attr("id"));
		selBtn.attr("deptID", deptID);
		//获取名称
		var deptTxt = $.trim($(eHandle).text());
		selBtn.attr("deptTxt", deptTxt);
		//获取节点类型(部门、区域、门店、分行、员工)
		var nType = $.trim($(eHandle).attr("common"));
		selBtn.attr("nType", nType=="" ? "员工" : nType);
		//获取所属部门ID
		if($(eHandle).hasClass("isStaff")){//如果是员工，记录所属部门ID
			var pid = $(eHandle).attr("name");
			selBtn.attr("pid", pid);
		}
		else{//如果非员工，则不需记录所属部门ID
			selBtn.attr("pid", "");
		}
		
		//执行回调函数
		if(callbacksel != null){
			eval(callbacksel+"()");
		}

		//隐藏弹层
		if(showStill){
			hideTreePopup();
		}
		//$(eHandle).attr("href", "javascript:void(0)");
		return false;
	}
	if($(eHandle).hasClass("unSelect")){//取消选中
		selBtn.val("请选择").attr("deptID", "").attr("pid", "").attr("deptTxt", "").attr("nType", "");
		$("#treeWrap").scrollTop(0);

		//执行回调函数
		if(callbackunsel != null) eval(callbackunsel+"()");
		
		if(showStill){
			hideTreePopup();
		}
		//$(eHandle).attr("href", "javascript:void(0)");
		return false;
	}
}

//分支逐级展开
function partNodeExpend(id, pid){
	var pid = pid || null;
	var opid = id;
	if(pid != "" && pid != null){
		opid = pid;
	}
	if(tree.getNodeById(opid)!=null){
		var arrPath = tree.getNodeById(opid).getPath("id").substring(1).split("/");
		if(pid == "" || pid == null){//部门，不含当前节点
			arrPath.pop();
		}
		$.each(arrPath, function(idx, node){//逐级展开
			tree.getNodeById(node).expand(id);
		});
	}
}

//定位到节点
function setDeptPos(currentNodeId, pid){
	if(currentNodeId != ""){
		partNodeExpend(currentNodeId, pid);
	}
}

function setPos(currentNodeId){
	var node = $("#"+currentNodeId);
	if(node.length != 0){
		node.parents("ul.TreePanel_List").eq(0).find("a.current").removeClass("current");
		node.addClass("current");
		$("#treeWrap").scrollTop(0);
		var scrollT = node.offset().top-$("#treeWrap").offset().top-100;
		$("#treeWrap").scrollTop(scrollT);
	}
}

function setCurrentNode(inputObj){
	var deptID = $.trim(inputObj.attr("deptID"));
	if(deptID != ""){//己有默认节点（部门或员工）
		if($("#"+deptID).length == 0 || !$("#"+deptID).parents("li").eq(1).find("span.switch").hasClass("open")){//未展开，先展开再定位
			setDeptPos(deptID, $.trim(inputObj.attr("pid")));
		}
		else{//已展开，直接定位
			setPos(deptID);
		}
	}
}

//隐藏弹层
function hideTreePopup(isEmpty){
	var isEmpty = isEmpty || null;
	$("#popup_tree").hide();
	removeIE6Mask();
	if(isEmpty !=null){
		$("#treeWrap").empty();
	}
}

/**--------------------组织架构搜索自动完成---------------------------**/

//初始化组织架构树的搜索
function initTreeSearch(params, searchCtrl){
	
	var canDept = params.canDept || false;
	var canStaff = params.canStaff || false;
	var autocompConfig = {
		searchConfig: params.searchConfig,				//搜索配置参数
		toggleTreePanel: toggleTreePanel,
		resetSearch: resetSearch
	}
	
	searchCtrl.eq(0).autocomplete({
		urlOrData: 'http://120.dooioo.com/oms/api/queryOrgTree',
		itemTextKey: 'branchName',
		dataKey: 'tree',
		autocompleteOnfocus: false,
		hasMask: false,
		setRequestParams: function(requestParams){//ajax额外请求参数
			requestParams.keysWord = $.trim(searchCtrl.eq(0).val());
			requestParams.treeType = autocompConfig.searchConfig.treeType || 'dooiooAll';			//可选值为：dooiooAll,dooiooSales,dooiooSalesNoDept,dooiooSalesNoGroup,dooiooSupport,dooiooFull
			requestParams.roleType = canDept && canStaff == true ? '' : (canDept ? '1' : '2');		//'1'组织，	'2'员工	''组织和员工
			var userStatus = autocompConfig.searchConfig.userStatus===undefined ? '1' : autocompConfig.searchConfig.userStatus;
			requestParams.userStatus = userStatus;				//员工状态：'1'在职， '2'离职， ''在职离职
		},
		selectCallback: function(selectedItemData){			//选中项时回调函数
			var selBtn = params.selBtn || $("#selectDept");
			selBtn.val(selectedItemData.branchName);
			selBtn.attr('deptID', selectedItemData.branchCode);
			selBtn.attr('deptTxt', selectedItemData.branchName);
			selBtn.attr("nType", selectedItemData.orgType=="" ? "员工" : selectedItemData.orgType);
			if( selectedItemData.pId != '0' ){//选择人
				selBtn.attr('pid', selectedItemData.pId);
			}else{//选择部门
				selBtn.attr("pid", '');
			}
			hideTreePopup();
		}
	}).keyup(function(){
		autocompConfig.toggleTreePanel($(this));
	});
}

//重置搜索内容
function resetSearch(searchCtrl){
	searchCtrl.val('');
	toggleTreePanel(searchCtrl);
}

//显示或隐藏树
function toggleTreePanel(searchCtrl){
	if( $.trim( searchCtrl.val() ) != '' ){//输入搜索
		$('#treeWrap').addClass('hideit');
		$('#'+searchCtrl.attr('popdiv')).show();
	}else{//树上选择
		$('#'+searchCtrl.attr('popdiv')).empty().hide();
		$('#treeWrap').removeClass('hideit');
	}
}


/*******************************************
 功能:选项树-往树上置值
 参数：&param: tree:tree对象
       &param: objChked: 隐含域，用于存放选中项id
********************************************/
function setCheckedToTree(tree, strChked){
	if($.trim(strChked.toString()) != ""){
		var arrChked = $.trim(strChked).split(",");
		$.each(arrChked, function(i,n){
			var arrPath = tree.getNodeById(n).getPath("id").substring(1).split("/");
			arrPath.pop();
			$.each(arrPath, function(idx,node){
				tree.getNodeById(node).expand();
			});
			tree.getNodeById(n).onCheck();
		});
	}
}

//通过节点id获取节点文本
function getNodeText(id){
	if(tree.getNodeById(id)!=null) return tree.getNodeById(id).text;
}



/******************************************************************
以下方法为：根据实际业务需要对树上节点进行局部调整
特别说明：目前仅对初始化时就展开的节点起作用，不可见的节点不能处理
*******************************************************************/

/******************************************************************
 功能:链接树--使某些节点不可点击（链接变普通文本）
 参数：&param: nodes，要处理的节点对象，如$("#20495, #22, #20445")
*******************************************************************/
function nodeLinkToText(nodes){
	window.setTimeout(function(){
		nodes.each(function(){
			var id = $(this).attr("id");
			$('<span class="'+$(this).attr("class")+'" id="'+$(this).attr("id")+'">'+$(this).html()+'</span>').insertAfter($(this));
			$(this).remove();
			$("#"+id).click(function(e){
				stopBubble(e);
			});
		});
	},50);
}

/*******************************************************************
 功能:链接树-移除树上某些可见节点
 参数：&param: nodes，要处理的节点对象，如$("#20495, #22, #20445")
*********************************************************************/
function removeLinkNode(nodes){
	window.setTimeout(function(){
		nodes.each(function(){
			$(this).prev("span").remove();
			$(this).remove();
		});
	},50);
}

/*******************************************************************
 功能:选项树-移除树上某些可见节点前面的checkbox
 参数：&param: nodes，要处理的节点对象，如$("#20495, #22, #20445")
*********************************************************************/
function removeChkbox(nodes){
	window.setTimeout(function(){
		nodes.each(function(){
			$(this).remove();
		});
	},50);
}

/*******************************************************************
 功能:选项树-移除树上某些可见节点，但不包含下级子孙节点
 参数：&param: nodes，要处理的节点对象，如$("#20495, #22, #20445")
*********************************************************************/
function removeChkNode(nodes){
	window.setTimeout(function(){
		nodes.each(function(){
			$(this).parent("span.TreeNode_new").prev("span.switch").remove();
			$(this).parent("span.TreeNode_new").remove();
		});
	},50);
}

/*******************************************************************
 功能:选项树-移除树上某些可见节点，且包含下级子孙节点
 参数：&param: nodes，要处理的节点对象，如$("#20520")
*********************************************************************/
function removeChkNodeAndChildren(nodes){
	window.setTimeout(function(){
		nodes.each(function(){
			$(this).parents("li").eq(0).remove();
		});
	},50);
}