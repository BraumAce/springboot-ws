var wsObj = null;
var wsUri = null;
var userId = -1;
var lockReconnect = false; //避免重复连接
var wsCreateHandler = null;

// 创建WebSocket
function createWebSocket() {
	var host = window.location.host; // 带有端口号
	userId = GetQueryString("userId");
	// wsUri = "ws://" + host + "/websocket?userId=" + userId;
	wsUri = "ws://" + host + "/websocket/" + userId;

	try {
		wsObj = new WebSocket(wsUri);
		initWsEventHandle();
	} catch (e) {
		writeToScreen("发生异常，开始重连");
		reconnect();
	}
}

// 绑定事件
function initWsEventHandle() {
	try {
		wsObj.onopen = function (evt) {
			onWsOpen(evt);
			heartCheck.start();
		};

		wsObj.onmessage = function (evt) {
			onWsMessage(evt);
			heartCheck.start();
		};

		wsObj.onclose = function (evt) {
			writeToScreen("连接关闭");
			onWsClose(evt);
		};

		wsObj.onerror = function (evt) {
			writeToScreen("发生异常，开始重连");
			onWsError(evt);
			reconnect();
		};
	} catch (e) {
		writeToScreen("绑定事件没有成功");
		reconnect();
	}
}

function onWsOpen(evt) {
	writeToScreen("已建立连接");
}

function onWsClose(evt) {
	writeToScreen("已断开连接");
}

function onWsError(evt) {
	writeToScreen(evt.data);
}

function writeToScreen(message) {
	if (DEBUG_FLAG) {
		$("#debuggerInfo").val($("#debuggerInfo").val() + "\n" + message);
	}
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	// 获取url中"?"符后的字符串并正则匹配
	var r = window.location.search.substr(1).match(reg);
	var context = "";
	if (r != null) context = r[2];
	reg = null;
	r = null;
	return context == null || context == "" || context == "undefined" ? "" : context;
}

// 重连机制
function reconnect() {
	if(lockReconnect) {
		return;
	}

	console.log("1秒后重连");
	lockReconnect = true;
	// 没连接上会一直重连，设置延迟避免请求过多
	wsCreateHandler && clearTimeout(wsCreateHandler);
	wsCreateHandler = setTimeout(function () {
		console.log("重连中..." + wsUri);
		createWebSocket();
		lockReconnect = false;
		console.log("重连完成");
	}, 1000);
}

// 心跳检测
var heartCheck = {
	// 60s之内如果没有收到后台的消息，则认为是连接断开了，需要再次连接
	timeout: 60000,
	timeoutObj: null,
	serverTimeoutObj: null,

	// 重启
	reset: function(){
		clearTimeout(this.timeoutObj);
		clearTimeout(this.serverTimeoutObj);
		this.start();
	},

	// 开启定时器
	start: function(){
		var self = this;
		this.timeoutObj && clearTimeout(this.timeoutObj);
		this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);

		this.timeoutObj = setTimeout(
			function() {
				console.log("发送ping到后台");
				try {
					wsObj.send("ping");
				} catch(ee) {
					console.log("发送ping异常");
				}

				// 内嵌计时器
				self.serverTimeoutObj = setTimeout(function(){
					// 如果onclose会执行reconnect，我们执行ws.close()就行了.
					// 如果直接执行reconnect 会触发onclose导致重连两次
					console.log("没有收到后台的数据，重新连接");
					//wsObj.close();
					reconnect();
				}, self.timeout);
			},
			this.timeout)
	},

}