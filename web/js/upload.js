function DragImgUpload(id,options){
	this.me=$(id);
	var defaultOpt={
		boxWidth:'200px',boxHeight:'auto'
		}
	this.preview=$('<div id="preview"><img src="img/upload.png" class="img-responsive"  style="width: 100%;height: auto;" alt="" title=""> </div>');
	this.opts=$.extend(true,defaultOpt,{},options);
	this.init();
	this.callback=this.opts.callback;
}

DragImgUpload.prototype={
	init:function(){
		this.me.append(this.preview);
		this.me.append(this.fileupload);
		this.cssInit();
		this.eventClickInit();
	},
	cssInit:function(){
		this.me.css({
			'width':this.opts.boxWidth,'height':this.opts.boxHeight,'border':'1px solid #cccccc','padding':'10px','cursor':'pointer'
			})
		this.preview.css({
			'height':'100%','overflow':'hidden'
			})
	},
	onDragover:function(e){
		e.stopPropagation();
		e.preventDefault();
		e.dataTransfer.dropEffect='copy';
	},
	onDrop:function(e){
		var self=this;
		e.stopPropagation();
		e.preventDefault();
		var fileList=e.dataTransfer.files;
		if(fileList.length==0){
			return false;
		}
		alert(fileList[0].type);
		if(fileList[0].type.indexOf('image')===-1){
			alert("您拖的不是图片！");
			return false;
		}
		var img=window.URL.createObjectURL(fileList[0]);
		var filename=fileList[0].name;
		var filesize=Math.floor((fileList[0].size)/1024);
		if(filesize>500){
			alert("上传大小不能超过500K.");
			return false;
		}
		self.me.find("img").attr("src",img);
		self.me.find("img").attr("title",filename);
		this.onDrop(fileList);
		if(this.callback){
			this.callback(fileList);
		}
	},
	eventClickInit:function(){
	var self=this;
	this.me.unbind().click(function(){
		self.createImageUploadDialog();
	})
	var dp=this.me[0];
	dp.addEventListener('dragover',function(e){
		self.onDragover(e);});
	dp.addEventListener("drop",function(e){
		self.onDrop(e);
	});
	},
	onChangeUploadFile:function(){
		var fileInput=this.fileInput;
		var files=fileInput.files;
		var file=files[0];
						var imgExt = new Array(".png",".jpg",".jpeg",".bmp",".gif");//图片文件的后缀名
			            var name = file.name.toLowerCase();
					    var i = name.lastIndexOf(".");
					    if(i > -1){
					    var ext = name.substring(i);
					    }
			            for(var i=0; i<imgExt.length; i++){
						    if(imgExt[i] === ext){
						    		console.log(ext);
						    					           
					    
		var img=window.URL.createObjectURL(file);							
		var filename=file.name;this.me.find("img").attr("src",img);
		this.me.find("img").attr("title",filename);
		if(this.callback){
			this.callback(files);
		}
						            return true;
						    }        
					    }
			            alert("文件格式错误：请上传图片")
			            return false;
			            console.log(3);

	},
    createImageUploadDialog: function() {
        var fileInput = this.fileInput;
        if (!fileInput) {
            fileInput = document.createElement('input');
            fileInput.type = 'file';
            fileInput.name = 'ime-images';
            fileInput.multiple = true;
            fileInput.onchange = this.onChangeUploadFile.bind(this);
            this.fileInput = fileInput;
        }
        fileInput.value = ''; //这里清空input的值，即可解决选择同一文件无法触发回调的问题
        fileInput.click();
    }
}