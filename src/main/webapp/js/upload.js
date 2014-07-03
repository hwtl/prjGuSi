(function($){
    var uploadCount = 0;
    $.fn.extend({
        uploadFile: function(opts){
            opts = $.extend({
                attachName: 'attachFile',
				thumbnailContainerSelector: '#attachViewList',
                addDirection: 'append',
                filterExtReg: /^(jpg|png|jpeg|gif|GIF|pdf|bmp|doc|docx|txt|xls|xlsx)$/i,
                setRequestParams: function(uploadCtrl, requestParams) {},
				previewCallback: function(attachView, responseJson) {},
                deleteCallback: function(deleteObj, responseJson) { return true; },
				generateProcessBarCallback: function(uploadingId){
					 return '<div id='+ uploadingId + ' class="upload_doing">'+
								'<div class="doing">正在上传...'+
									'<div class="percent"><img src="http://dui.dooioo.com/public/images/bg_uploadPercent.gif" width="" height="22"/></div>'+
								'</div>'+
							'</div>';
				},
                generateThumbnailCallback: function(uploadCtrl, responseJson, extName){
					var attachUrl = responseJson.attachUrl ? responseJson.attachUrl : THUMB_NAIL_CONTEXT_URL+THUMB_NAIL_MAPPING[extName];
                    return '<div class="attThumbWrap">'+
								'<div class="attThumbWrapInner">'+
									'<a class="attach_thumbnail js_thumbnail" href="#" style="background-image: url('+attachUrl+');"> </a>' +
									'<div class="op"><a href="#" class="red js_delete" attachId='+responseJson.attachId+'>删除</a></div>'+
								'</div>'+
							'</div>';
                },
				completeCallback: function(uploadCtrl, responseJson) {}
            }, opts || {});

            return this.each(function() {
                var self = $(this);
                var uploadingId, extName = '';
                new AjaxUpload(self, {
                    action: opts.url,
                    name: opts.attachName,
                    responseType: 'json',

                    onChange:function(){
                        uploadingId = Number(new Date());
                        var requestParams = {};
                        opts.setRequestParams(self, requestParams);
                        requestParams.uploadingId = uploadingId;
                        this.setData(requestParams);
                        this.submit();
                    },

                    onSubmit: function(file , ext){
                        if (!(ext && opts.filterExtReg.test(ext))){
                            alert('系统不支持你所选的附件格式，支持格式为：jpg,png,jpeg,gif,GIF,pdf,bmp,doc,docx,txt,xls,xlsx');
                            return false;
                        }
						extName = ext;
                        //添加“正在上传”进度条
                        $(opts.thumbnailContainerSelector)[opts.addDirection]($(opts.generateProcessBarCallback(uploadingId)));
                        //进度条显示
                        setProcess(uploadingId);
                    },

                    onComplete: function(file, responseJSON){
                        if(responseJSON.responseStatus == '404'){
                            alert('上传文件不得超过8M');
                            $('#'+ uploadingId).remove();
                            return;
                        }

						uploadCount++;

                        //移除进度条
                        $('#'+ responseJSON.uploadingId).remove();


                        //生成缩略图
						var attachView = $(opts.generateThumbnailCallback(self, responseJSON, extName));
                        $('#'+self.attr("attachList"))[opts.addDirection]( attachView );


                        //预览附件
                        attachView.find('.js_thumbnail').click(function(){
                            opts.previewCallback(attachView, responseJSON);
                            return false;
                        });

                        //删除附件
						attachView.find('.js_delete').click(function(){
							if( opts.deleteCallback($(this), responseJSON) ){
								attachView.remove();
							}
                            return false;
						});

                        //上传完成回调
                        opts.completeCallback(self, responseJSON);
                    }
                });
            });
        }
    });

	$.fn.uploadFile.getAttachCount = function(){
		return uploadCount;
	};
    $.fn.uploadFile.THUMB_NAIL_CONTEXT_URL = 'http://dui.dooioo.com/public/images/';
    $.fn.uploadFile.THUMB_NAIL_MAPPING = {
        'pdf': 'pdf.gif',
        'doc': 'msword.gif',
        'docx': 'docx.gif',
        'txt': 'txt.gif',
        'xls': 'xls.gif'
    };
})(jQuery);


