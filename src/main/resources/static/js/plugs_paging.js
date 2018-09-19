
(function($, window, document, undefined) {
	//定义分页类
	function Paging(element, options) {
		this.element = element;
		//传入形参
		this.options = {
            rows: options.rows,
			pageNum: options.pageNum||1,
			pages: options.pages,
			total:options.total,
            flag:options.flag,
			callback:options.callback
		};
		//根据形参初始化分页html和css代码
		this.init();
	}
	//对Paging的实例对象添加公共的属性和方法
	Paging.prototype = {
		constructor: Paging,
		init: function() {
			this.creatHtml();
			this.bindEvent();
		},
		creatHtml: function() {
			var me = this;
			var content = "";
			var current = me.options.pageNum;
			var pages = me.options.pages;
			var total = me.options.total;
			var flag = me.options.flag;
			content += "<ul class=\"page am-pagination  am-pagination-centered\">";
			//总页数大于4时候
			if(flag == 0){
                if(pages > 4) {
                    //如果当前页小于4
                    if(current < 4){
                        if(current != 1){
                            content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        }
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    } else {
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        //当前页在末尾时
                        if(current > pages - 3) {
                            if(current != pages){
                                content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                            }
                        } else {
                            //当前页在中间时
                            content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                        }
                    }
                    //页面总数小于6的时候
                } else {
                    if(current != 1){
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                    }
                    if(current != pages){
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    }
                }
			} else {
                if(pages > 4) {
                    //如果当前页小于4
                    if(current < 4){
                        if(current != 1){
                            content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        }
                        for(var i = 1; i < 5; i++) {
                            if(current == i) {
                                content += "<li class=\"am-active\"><a href=\"#\">" + i + "</a></li>";
                            } else {
                                content += "<li><a>" + i + "</a></li>";
                            }
                        }
                        content += ". . .";
                        content += "<li><a>"+pages+"</a></li>";
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    } else {
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        content += "<li><a>" + 1 + "</a></li>";
                        content += "...";
                        //当前页在末尾时
                        if(current > pages - 3) {
                            for(var i = pages - 3; i <= pages; i++) {
                                if(current == i) {
                                    content += "<li class=\"am-active\"><a href=\"#\">" + i + "</a></li>";
                                } else {
                                    content += "<li><a>" + i + "</a></li>";
                                }
                            }
                            if(current != pages){
                                content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                            }
                        } else {
                            //当前页在中间时
                            for(var i = current - 1; i < current + 3; i++) {
                                if(current == i) {
                                    content += "<li class=\"am-active\"><a href=\"#\">" + i + "</a></li>";
                                } else {
                                    content += "<li><a>" + i + "</a></li>";
                                }
                            }
                            content += "...";
                            content += "<li><a>"+pages+"</a></li>";
                            content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                        }
                    }
                    //页面总数小于6的时候
                } else {
                    if(current != 1){
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                    }
                    for(var i = 1; i < pages + 1; i++) {
                        if(current == i) {
                            content += "<li class=\"am-active\"><a>" + i + "</a></li>";
                        } else {
                            content += "<li><a>" + i + "</a></li>";
                        }
                    }
                    if(current != pages){
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    }
                }
			}

			me.element.html(content);
		},
		//添加页面操作事件
		bindEvent: function() {
			var me = this;
			me.element.off('click', 'a');
			me.element.on('click', 'a', function() {
				var currentPage = $(this).html();
				var id=$(this).attr("id");
				if(id == "prePage") {
					if(me.options.pageNum == 1) {
						me.options.pageNum = 1;
					} else {
						me.options.pageNum = +me.options.pageNum - 1;
					}
				} else if(id == "nextPage") {
					if(me.options.pageNum == me.options.pages) {
						me.options.pageNum = me.options.pages
					} else {
						me.options.pageNum = +me.options.pageNum + 1;
					}

				} else{
					me.options.pageNum = +currentPage;
				}
				me.creatHtml();
				if(me.options.callback) {
					me.options.callback(me.options.pageNum);
				}
			});
		}
	};
	//通过jQuery对象初始化分页对象
	$.fn.paging = function(options) {
		return new Paging($(this), options);
	}
})(jQuery, window, document);