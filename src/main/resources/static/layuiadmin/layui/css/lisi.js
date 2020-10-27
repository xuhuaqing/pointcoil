/**
 * 注意！尽量不要使用 alter 方法，这样会使连接中断 
 */
var conn = null;
var getMsg1 = '';//发送的消息
var getMsg2 = '';//接收的消息
$(function() {
			//创建连接：开启IM连接
			conn = new WebIM.connection({
				isMultiLoginSessions: WebIM.config.isMultiLoginSessions,
				url: WebIM.config.xmppURL,
				heartBeatWait: WebIM.config.heartBeatWait,
				autoReconnectNumMax: WebIM.config.autoReconnectNumMax,
				autoReconnectInterval: WebIM.config.autoReconnectInterval,
				apiUrl: WebIM.config.apiURL,
				isAutoLogin: true 
			});
			//回调函数：开启监听（精简过后的代码）
			conn.listen({
				onOpened: function(message) {
					console.log('###Listen Success! You can used send###',message);			
				},
				onClosed: function(message) {}, //连接关闭回调
				onTextMessage: function(message) {
					console.log('###收到了文本###',message);
					getMsg2 = $("#text02").val()+message.sourceMsg+'\n';
					$("#text02").val(getMsg2);
				}, //收到文本消息
				onError: function(message) {console.log('###失败回调###',message);}, //失败回调
				onReceivedMessage: function(message) {}, //收到消息送达服务器回执
				onDeliveredMessage: function(message) {}, //收到消息送达客户端回执
				onReadMessage: function(message) {}, //收到消息已读回执
			});
		})
		
		
		$("#regist").click(function() {
			console.log('###注册###');
			var myusername = $("#username").val();
			var mypassword = $("#password").val();
			var mynickname = $("#nickname").val();
			var options = {
				username: myusername, //环信用户名
				password: mypassword, //环信密码
				nickname: mynickname, //环信昵称
				appKey: WebIM.config.appkey,
				success: function(msg) {
					console.log('###注册成功###',msg);
				},
				error: function(msg) {
					console.log('###注册失败###',msg);
				},
				apiUrl: WebIM.config.apiURL
			};
			conn.registerUser(options);
		})
		$("#login").click(function() {
			console.log('###登录###');
			var myusername = $("#username1").val();
			var mypassword = $("#password1").val();
			var options = {
				apiUrl: WebIM.config.apiURL,
				user: myusername,//环信用户名
				pwd: mypassword,//环信密码
				appKey: WebIM.config.appkey,
				success: function(msg) {
					console.log('###登录成功###',msg);
				},
				error: function(msg) {
					console.log('###登录失败###',msg);
				}
			};
			conn.open(options);
			//该设置为手动上线conn.setPresence();
		})
		
		
		//发送消息
		$("#sendMsg").click(function(){
			var txt = $("#value").val();
			var id = conn.getUniqueId();                 // 生成本地消息id
			var msg = new WebIM.message('txt', id);      // 创建文本消息
			msg.set({
				msg: txt,             // 消息内容
				to: 'zhangsan',     // 接收消息对象（用户id）
				roomType: false, 
				success: function (id, serverMsgId) {
					console.log('###消息发送成功###',msg);
				},
				fail: function(e){
					console.log('###消息发送失败###');
				}
			});
			msg.body.chatType = 'singleChat';
			conn.send(msg.body);
			getMsg1 = $("#text01").val()+txt+'\n';
			$("#text01").val(getMsg1);
		})