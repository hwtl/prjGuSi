(function($){
	var linkAttachLayerHtml = '<div class="popLayer" id="jygj_layer_link_file">\
									<div class="popTitle clearfix">\
										<h1>请选择“<span class="js_linktitle"></span>”</h1>\
										<div class="cls"><a href="javascript:;" class="xx js_hidepop"></a></div>\
									</div>\
									<div class="popCon">\
										<div class="clearfix js_linkAttachList"></div>\
									</div>\
									<div class="popBtn mt_20">\
										<a href="javascript:;" class="btnOpH24 h24Silver in_block js_hidepop">关闭</a>\
										<a href="javascript:;" class="btnOpH24 h24Blue in_block js_confirmLink">确定</a>\
									</div>\
								</div>',
		uploadHtml = '<div class="js_uploadContainer clearfix">\
							<div class="js_uploadLinkWrapper">\
								<input type="file" multiple class="js_trueFile hideit"/>\
								<a href="javascript:;" class="js_uploadFile"><img src="http://dui.dooioo.com/public/images/btn_upload_blue.gif" alt="上传文件"/></a>\
							</div>\
							<div class="js_attachViewList"></div>\
						</div>',
		uploadSingleHtml = '<div class="js_uploadContainer clearfix">\
							<div class="js_uploadLinkWrapper">\
								<input type="file" class="js_trueFile hideit"/>\
								<a href="javascript:;" class="js_uploadFile"><img src="http://dui.dooioo.com/public/images/btn_upload_blue.gif" alt="上传文件"/></a>\
							</div>\
							<div class="js_attachViewList"></div>\
						</div>';						

    $.fn.extend({
        uploadFileNew: function(opts){
            opts = $.extend({
                addDirection: 'append',
                filterExtReg: /^(jpg|png|jpeg|gif|GIF|pdf|bmp|doc|docx|txt|xls|xlsx)$/i,
				
                multiple: true,

				beforeUpload: function() {return true},
				//上传附件
                setUploadAttachParams: function(requestParams) {},
				getUploadAttachIdAndUrl: function(currentAttach) { return currentAttach; },
				previewCallback: function(attachView, responseJson) {},
                deleteCallback: function(responseJson) { return true; },
				
				//引用附件
				setLinkAttachParams: function(requestParams){},
				getLinkAttachIdAndUrl: function(currentAttach) { return currentAttach; },
				
				//确认引用
				setConfirmLinkAttachParams: function(requestParams) {},
				getConfirmLinkAttachIdAndUrl: function(currentAttach) { return currentAttach; },
				
				generateProcessBarCallback: function(uploadingId){			//显示正在上传...
					 return '<div id='+ uploadingId + ' class="upload_doing">'+
								'<div class="doing">正在上传...'+
									'<div class="percent"><img src="http://dui.dooioo.com/public/images/bg_uploadPercent.gif" height="22"/></div>'+
								'</div>'+
							'</div>'; 
				},
                generateThumbnailCallback: function(thumbnailInfo){		//构建缩略图html
					var attachUrl = thumbnailInfo.attachUrl || '',
						attachId = thumbnailInfo.attachId || '';
					
                    return '<div class="attThumbWrap js_attThumbWrap">\
								<div class="attThumbWrapInner">\
									<a class="attach_thumbnail js_thumbnail" href="javascript:;" style="background-image: url('+attachUrl+'); -webkit-background-size: 100px 100px; background-size: 100px 100px;"> </a>\
									<div class="op js_op"><a href="javascript:;" class="red js_delete" rel="' + attachId + '">删除</a></div>\
								</div>\
							</div>';
                },
				completeCallback: function(uploadCtrl, responseJson) {}
            }, opts || {});

			
            return this.each(function() {
            	if (opts.multiple === true) {
            		var self = $(this).html(uploadHtml);
            	} else {
            		var self = $(this).html(uploadSingleHtml);
            	};
                
				
				//添加引用文件按钮
				if(opts.hasLinkAttach){
					self.find('.js_uploadLinkWrapper').append('<span class="attach_lnk in_block"><a href="javascript:;" class="quote_lnk js_linkAttach">引用</a></span>');
				}
				
                //初始化时加载附件列表
				$.each(opts.initAttachDataOrUrl || [], function(){
					self.find('.js_attachViewList')[opts.addDirection]( createAttachView(opts.getInitAttachIdAndUrl(this), this, opts) );
				});
				
				//触发点击上传文件操作
				self.find('.js_uploadFile').click(function(){
					if(opts.beforeUpload()){
						self.find('.js_trueFile').trigger('click');
					}
				});
				
				//选择文件并上传文件
				self.find('.js_trueFile').change(function(e){
					//设置请求参数
					var requestParams = {};
					opts.setUploadAttachParams(requestParams);
					
					//调用上传组件
					var attachListContainer = self.find('.js_attachViewList');
					for(var i=0; i<$(this).get(0).files.length; i++){
						var fileUploader = new FileUploader($(this).get(0).files[i], requestParams, opts);
						
						var preCheckResult = fileUploader.preCheck();
						if( preCheckResult.code === FileUploader.ERROR_STATUS.TYPE_NOT_SUPPORTED || preCheckResult.code === FileUploader.ERROR_STATUS.EXCEED_LIMIT_SIZE) {
							alert(preCheckResult.message);
							continue ;
						}
						
						attachListContainer[opts.addDirection]($(opts.generateProcessBarCallback(fileUploader.getUploadingId())));
						fileUploader.upload(function (responseJson){
							//移除进度条
							$('#'+ responseJson.uploadingId).remove();
							//生成缩略图
							attachListContainer[opts.addDirection]( createAttachView(opts.getUploadAttachIdAndUrl(responseJson), responseJson, opts) );
							//上传完成回调
							opts.completeCallback(self.find('.js_uploadFile'), responseJson);
						});
					}
				});

				
				//引用文件
				self.find('.js_linkAttach').click(function(){
					var attachViewList = $(this).parents('div.js_uploadContainer').find('div.js_attachViewList');

					//生成碳层并加载附件引用
					if($('#jygj_layer_link_file').length === 0){
						$(linkAttachLayerHtml).appendTo($('body'));
						var popup = $('#jygj_layer_link_file');
						
						popup.find('.js_linktitle').text(opts.linkTitle);
						//点击确定，选择链接文件
						popup.find('.js_confirmLink').click(function(){
							$('#jygj_layer_link_file').find('[name=linkedAttach]:checked').each(function(){
								var confirmLinkParams = {};
								opts.setConfirmLinkParams(confirmLinkParams);
								$.getJSON(opts.confirmLinkAttachUrl, confirmLinkParams, function(res){
									attachViewList.append( createAttachView(opts.getConfirmLinkAttachIdAndUrl(res), res, opts) );
									removePopup(popup);
								});
							});
						});

						//隐藏并移除弹层
						popup.find(".js_hidepop").live("click", function(){
							removePopup($('#jygj_layer_link_file'));
						})
					}
					
					
					/** 加载引用附件列表 **/
					//组装请求参数
					var linkAttachParams = {};
					opts.setLinkAttachParams(linkAttachParams);
					
					//发起获取引用文件请求
					$.getJSON(opts.linkAttachUrl, linkAttachParams, function(response){
						var attachListContainer = $('#jygj_layer_link_file').find('.js_linkAttachList').empty();
						if(response && response.length > 0){
							var attachView = null;
							$.each(response, function(){
								var attachIdAndUrlObj = opts.getLinkAttachIdAndUrl(this);
								attachView = createAttachView(attachIdAndUrlObj, this, opts);
								attachView.find('.js_op').remove();
								attachListContainer.append(attachView) ;
								attachView.append('<p class="ml_10 mt_5"><input type="checkbox" name="linkedAttach" attachUrl="'+ attachIdAndUrlObj.attachUrl +'" value="'+ attachIdAndUrlObj.attachId +'"/></p>');
							});
						}else{
							attachListContainer.append('<p class="center">没有可以引用的附件</p>');
						}
					});
					
					showPopupDiv($('#jygj_layer_link_file'));
				});
            });
			
			//删除引用弹层
			function removePopup(popup){
				hidePopupDiv(popup);
				popup.remove();
			}
        }
    });
	
	//显示附件列表
	function createAttachView(attachInfo, completeJson, opts){
		var attachView = $(opts.generateThumbnailCallback(attachInfo));
		attachView.data('thumbnailInfo', completeJson);
		
		//预览附件
		attachView.find('.js_thumbnail').click(function(){
			opts.previewCallback(attachView, completeJson);
		});

		//删除附件
		attachView.find('.js_delete').click(function(){
			if( opts.deleteCallback($(this).closest('.js_attThumbWrap').data('thumbnailInfo')) ){
				attachView.remove();
			}
		});
		
		return attachView;
	}

	
	
	/** 文件上传器 **/
	function FileUploader(file, reqParams, opts){
		this.file = file;
		this.reqParams = reqParams;
		this.opts = opts;
		
		this.uploadingId = Number(new Date());
	}
	FileUploader.ERROR_STATUS = {};
	FileUploader.ERROR_STATUS.SUCCESS = 1;
	FileUploader.ERROR_STATUS.TYPE_NOT_SUPPORTED = 2;
	FileUploader.ERROR_STATUS.EXCEED_LIMIT_SIZE = 3;
	
	FileUploader.prototype = {
		preCheck: function(){		//上传之前的预处理
			var extName = this.file.name.slice(this.file.name.indexOf('.') + 1);
			if( this.opts.filterExtReg.test(extName) === false ){
				return {code: FileUploader.ERROR_STATUS.TYPE_NOT_SUPPORTED, message: '不支持扩展名为"' + extName + '"的文件。'};
			}
			if( this.file.size > this._getMaxSize() ){
				return {code: FileUploader.ERROR_STATUS.EXCEED_LIMIT_SIZE, message: '文件大小不能超过“' + this.opts.maxsize + 'MB"'};
			}
			return  {code: FileUploader.ERROR_STATUS.SUCCESS, message: '可以上传'};
		},
		upload: function(successCallback){	//上传文件
			var xhr = new XMLHttpRequest();
			xhr.open('post', this.opts.uploadAttachUrl, true);
			xhr.send(this._buildSendData());
			
			xhr.addEventListener('readystatechange', function(response) {
				try {
					if ((xhr.status == 0 || xhr.status == 200) && xhr.readyState == 4) {
						successCallback(JSON.parse(response.target.responseText));
					} 
				}catch (e) {}
			}, false);
		},
		getUploadingId: function(){		//获取此次上传的ID
			return this.uploadingId;
		},
		_buildSendData: function(){
			var fd = new FormData();
			
			fd.append('name', this.file.name);
			fd.append('uploadingId', this.getUploadingId());
			for(var paramName in this.reqParams) {
				fd.append(paramName, this.reqParams[paramName]);
			}
			fd.append('file', this.file);
			
			return fd;
		},
		_getMaxSize: function(){
			try{
				var maxSize = Number(this.opts.maxsize);
			}catch(e){
				return 1 * 1024 * 1024;
			}
			
			return maxSize * 1024 * 1024;
		}
	};
})(jQuery);