/**
 * git do not control webim.config.js
 * everyone should copy webim.config.js.demo to webim.config.js
 * and have their own configs.
 * In this way , others won't be influenced by this config while git pull.
 *
 */
//建议xmppURL、apiURL、https三个参数统一，否则ie9以下会报拒绝访问的错误。
var WebIM = {};
WebIM.config = {
	//xmpp Server地址，对于在console.easemob.com创建的appKey，固定为该值
    xmppURL: 'im-api.easemob.com',
	//rest Server地址，对于在console.easemob.com创建的appkey，固定为该值
    apiURL:'http://a1.easemob.com',
	//Application AppKey
    appkey: '1104190516090655#851655609',
	//是否使用https
    https: false,
	//是否开启多页面同步收消息，注意，需要先联系商务开通此功能
    isMultiLoginSessions: true,
	//自动出席，（如设置为false，则表示离线，无法收消息，需要在登录成功后手动调用conn.setPresence()才可以收消息）
    isAutoLogin: true,
	//打开调试，会自动打印log，在控制台的console中查看log
	isDebug: false,
	//断线重连最大次数，=0时则不会自动连接
	autoReconnectNumMax: 2,
	//每次自动重新连接之间的间隔秒，>= 2时才有效
	autoReconnectInterval: 2,
	//使用webrtc（视频聊天）时发送心跳包的时间间隔，单位ms
	heartBeatWait: 4500,
	//是否发送已读回执
	delivery: true,
	
	
    /**
     * Whether to use window.doQuery()
     * @parameter {Boolean} true or false
     */
    isWindowSDK: false,
    /**
     * isSandBox=true:  xmppURL: 'im-api-sandbox.easemob.com',  apiURL: '//a1-sdb.easemob.com',
     * isSandBox=false: xmppURL: 'im-api.easemob.com',          apiURL: '//a1.easemob.com',
     * @parameter {Boolean} true or false
     */
    isSandBox: false,
    
    /**
     * webrtc supports WebKit and https only
     */
    isWebRTC: (/Firefox/.test(navigator.userAgent) || /WebKit/.test(navigator.userAgent)) && /^https\:$/.test(window.location.protocol),
    
    /**
     * while http access,use ip directly,instead of ServerName,avoiding DNS problem.
     */
    isHttpDNS: false,

    /**
     * Will show the status of messages in single chat
     * msgStatus: true  show
     * msgStatus: true  hide
     */
    msgStatus: true,

    /**
     * When a message read, the receiver send an ack message to the
     * sender, in order to tell the sender the message has been read.
     * See call back function onReadMessage
     */
    read: true,

    /**
     * When a message sent or arrived, will save it into the localStorage,
     * true: Store the chat record
     * false: Don't store the chat record 
     */
    saveLocal: true,

    /**
     * Will encrypt text message and emoji message
     * {type:'none'}   no encrypt
     * {type:'base64'} encrypt with base64
     * {type:'aes',mode: 'ebc',key: '123456789easemob',iv: '0000000000000000'} encrypt with aes(ebc)
     * {type:'aes',mode: 'cbc',key: '123456789easemob',iv: '0000000000000000'} encrypt with aes(cbc)
     */
    encrypt: {
        type: 'none'
    }
};
