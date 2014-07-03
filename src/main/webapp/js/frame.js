var ORG = ORG || {};
$(function(){
	
	ORG.TREE = (function(){
		var _switchFn = function(self, flag, txt1, txt2){
			self.attr('open', flag).attr('title', txt1).text(txt2);			
		},
		switchEvent = function(self){
			var flag = self.attr('open'),
				cNode = self.closest('li').find('ul:first');
			if(flag === 'false'){
				_switchFn(self, 'true', '关闭', '-');	
				cNode.removeClass('hideit');
			}else{
				_switchFn(self, 'false', '展开', '+');
				cNode.addClass('hideit');				
			}		
		};
		return {
			switchEvent: switchEvent
		}
	})();
	
	ORG.TAB = (function(){
		var frameWrap = $('#orgFrame'),
			tabNav = $('div.js_nav'),
			tabWrap = $('.js_tabwrap'),
			node = frameWrap.find('.js_nodes'),
			_getAttr = function(o, attr){
				return $(o).attr(attr);
			},
			_animate = function(){
				if($('div.js_rightcontent:hidden').length === 1){
					$('h2.js_welcome').slideUp();
					$('div.js_rightcontent').slideDown();
				}
				if(tabWrap.find('a.js_tab').length === 0){
					$('h2.js_welcome').slideDown();
					$('div.js_rightcontent').slideUp();			
				}
			},
			_showCurrentContent = function(id){
				$('div.js_incontainer').hide();
				$('#incontainer_'+id).show();
			},
			_setCurrent = function(me){
				var curent = tabWrap.find('a.current');
					curent.find('span.js_del').removeClass('active');
					curent.removeClass('current');
				me.addClass('current');
				me.find('span.js_del').addClass('active');
							
			},
			_isFilledForm = function (elNames){
				if(elNames !== undefined){
					for(var i=0, len=elNames.length; i<len; i++){
						if( _validate($('[name=' + elNames[i] +']'))  === true ){
							return true;
						}
					}
					return false;
				}
			},
			_validate = function($element){
				var ctrlType = _getCtrlTypeConfig($element).ctrlType;
				if( ctrlType == 'radio' || ctrlType === 'checkbox'){
					return $element.filter(':checked').length > 0;
				}
				var firstEl = $element.eq(0);
				if( ctrlType == 'select' ){
					return (firstEl.val() !== '');
				}		
				//文本框有默认值
				if( firstEl.attr('defaultText') != undefined ){
					if(firstEl.attr('defaultText') == $.trim(firstEl.val())){
						return false;
					}
				}
				return $.trim( firstEl.val() ) !== '';
			},
			_getCtrlTypeConfig = function ($element){
				if( $element.is(':text') || $element.is(':password') || $element.is(':file') || $element.is('textarea') || $element.is('input[type=hidden]') ){
					return {ctrlType: 'text'};
				}
				if( $element.is(':radio') ){
					return {ctrlType: 'radio'};
				}
				if( $element.is(':checkbox') ){
					return {ctrlType: 'checkbox'};
				}
				if( $element.is('select') ){
					return {ctrlType: 'select'};
				}
				throw 'name为 "'+$element.attr('name')+'"的控件类型无法识别，请联系管理员.';
			},
			_findForm = function(form){
				if(form.find('[rule]').length > 0 ){
					return $.unique(form.find('[rule]').map(function(){
						return $.trim($(this).attr('name'));
					}).get());
				}else{
					return false;
				}
			},
			_chkFormWrite = function(form){
				return _isFilledForm(_findForm(form)) === false;
			},
			_buildTab = function(cid, pid, t, url){
				var currentTab = tabWrap.find('a.current'),
					newTab = null;
				if(currentTab.length > 0){
					newTab = currentTab.clone();
					newTab.attr('url', url).attr('nodeid', cid).attr('pnodeid', pid);
					newTab.find('span.js_label').text(t);
					currentTab.find('span.js_del').removeClass('active');
					currentTab.removeClass('current');
				}else{
					currentTab = null;
					newTab = $('<a href="javascript:;" url='+url+' class="js_tab current" pnodeid='+pid+' nodeid='+cid+'>\
										<span class="js_label">'+t+'</span>\
										<span class="bg_del active in_block js_del" title="删除"></span>\
								</a>');
				}
				return newTab;			
			
			},
			_inTab = function(me, id){
				var flag = false,
					tabNum = tabWrap.find('a.js_tab');
				if(tabNum.length > 0){
					tabNum.each(function(){
						if(_getAttr($(this), 'nodeid') === _getAttr(me, id)){
							flag = true;
							return false;			
						}			
					});	
				}
				return flag;
			},
			_tabLen = function(){
				if(tabWrap.find('a.js_tab').length > 6){
					return false;
				}
				return true;			
            },
            _iniframe = function(self){
                self.height=self.contentWindow.window.document.body.scrollHeight + 50;
                var iframe = $(self.contentWindow.window.document),
                    href = iframe.find(".js_hrefs");
                if(href.attr('href') !== 'javascript:;'){
                    href.attr('href', 'javascript:;');
                }
                iframe.find('.js_globleSuggestWrapper').remove();
                href.click(ORG.TAB.hrefEvent);
            },
            _iframe = function(url, id){
                return '<iframe src='+url+' id="iframe'+id+'" width="100%" onload="ORG.TAB._iniframe(this)" scrolling="no"  frameborder="0"></iframe>';
            };
			function treeNodeEvent(me,obj){
                var url = _getAttr(me, 'url'),
					cid = _getAttr(me, 'nodeid'),
					pid = _getAttr(me.closest('ul.js_sections').siblings('div').children('a.js_nodes'), 'nodeid'),
					t = me.text();
				if(_getAttr(me, 'staticnode') === 'false'){
					if(_inTab(me, 'nodeid') === true){
						var a = tabWrap.find('[nodeid='+cid+']'),
							content = $('#incontainer_'+cid);
						_setCurrent(a);
						_showCurrentContent(cid);
						if(_getAttr(content.find('.js_refresh'), 'refresh') === 'false'){
							if(_chkFormWrite(content) === false){
								if(window.confirm('你是想重新打开'+t+'么？重新打开已编辑的信息会清空！')){
                                    content.html(iframe(url, cid));
								}
							}						
						}else{
							a.click();
						}						
					}else{
						if(_tabLen() === true){
							tabWrap.prepend(_buildTab(cid, pid, t, me.attr('url')));
                            $('#mainContainer').append('<div class="js_incontainer" id="incontainer_'+cid+'" >'+_iframe(url, cid)+'</div>');
                            _showCurrentContent(cid);
						}else{
							alert('请关闭一个再打开，最多同时打开6个！')
						}
						_animate();
					}
				}
			
			}
			function tabEvent(me, obj){
				var cid = _getAttr(me, 'nodeid');
				_setCurrent(me);
				_showCurrentContent(_getAttr(me, 'nodeid'));
				if(_getAttr($('#incontainer_'+cid).find('.js_refresh'), 'refresh') === 'true'){
                    $('#incontainer_'+cid).html(_iframe(url, cid));
				}				
			}
			function tabDel(me){
				var	direction = me.next().length === 0 ? me.prev() : me.next(),
                    confirmStr = _chkFormWrite($('#incontainer_'+_getAttr(me, 'nodeid'))) === false ? (me.text()+'有未保存的信息，确认删除？') : ('确认删除'+me.text()+'？');
                if(window.confirm(confirmStr)){
                    me.remove();
                    $('#incontainer_'+_getAttr(me, 'nodeid')).remove();
                    if(direction.length !== 0){
                       direction.click(tabEvent(direction));
                    }
                }
				_animate();				
			}

			function hrefEvent(me){
                me = $(this);
            setTimeout(function(){
                    var idRdm = Math.round(Math.random()*100),
                        id = _getAttr(me, 'id') * idRdm;
                    if(_tabLen() === true){
                        if(me.attr('target') === '_blank'){
                            var t = me.attr('tabname') || me.text();
                            tabWrap.prepend(_buildTab(id, id, t, me.attr('url')));
                            $('#mainContainer').append('<div class="js_incontainer" id="incontainer_'+id+'" >'+_iframe(me.attr('url'), id)+'</div>');
                            _showCurrentContent(id);
                        }else{
                            var currentTab = tabWrap.find('a.current'),
                                cid= _getAttr(currentTab, 'nodeid');
                            if(_getAttr(me, 'tabname') !== undefined){
                                currentTab.find('span.js_label').text(_getAttr(me, 'tabname'));
                            }
                           // console.log(_getAttr(me, 'url'));
                            $('#incontainer_'+cid).html(_iframe(me.attr('url'), cid));
                        }

                    }else{
                        alert('请关闭一个再打开，最多同时打开6个！')
                    }
                }, 0);
			}

			return {
				treeNodeEvent: treeNodeEvent,
				tabEvent: tabEvent,
				tabDel: tabDel,
				hrefEvent: hrefEvent,
                _iniframe: _iniframe
			
			}
	})();
});