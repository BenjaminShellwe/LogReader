package top.shellwe.logreadermod.controller;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.OutputStreamAppender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import top.shellwe.logreadermod.utility.TailfLogThread;
import top.shellwe.logreadermod.utility.Transformer;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

@ServerEndpoint("/log")
@RestController
@Slf4j
public class WebSocketController {
    //process 记录执行的命令
    private Process process;
    //onlineCount 用来记录当前连接数
    private static int onlineCount = 0;
    //inputStream 将获取的数据转换为输入流
    private InputStream inputStream;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();

    //与某个客户端的连接会话 需要通过它来给客户端发送数据
    private Session session;

    /**
     * 新的WebSocket请求开启
     * @param session 新请求的session记录
     */

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);  // 新连接加入webSocketSet集合中
        addOnlineCount();   // 人数加一

        log.info("有一链接访问，当前人数为" + getOnlineCount());


        final org.slf4j.Logger logger =  log;
//      目前进行简单连通性测试
        String str1 = "连通性测试 此字符说明正常联通" ;

        InputStream result = new ByteArrayInputStream(str1.getBytes(StandardCharsets.UTF_8));
//            Linux 读取 取消注解记得添加异常处理
//            process = Runtime.getRuntime().exec("tail -f /logs/es-sync/es-sync.log");
//            inputStream = process.getInputStream();

        inputStream = result;

        TailfLogThread thread = new TailfLogThread(inputStream, session);
        thread.start();
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从集合中删除
        subOnlineCount();           //在线数减1人数减一
        log.info("有一连接关闭，当前人数为" + getOnlineCount());
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }

    /**此方法用于后期错误追踪
     *
     * @param thr 抛出错误目标
     */
    @OnError
    public void onError(Throwable thr) {
        log.error("发生错误");
        thr.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        //log.info("收到来自窗口的信息:"+message);
    }

    /**
     * 此方法实现由后台主动推送消息到前台
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(Object o) throws IOException {
        this.session.getBasicRemote().sendText((String) o);
    }

    /**
     * 此方法实现发送自定义消息
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {

        for (WebSocketController item : webSocketSet) {
            try {
                //这里可以设定只推送给某个sid，为null则全部推送
//                if(sid==null) {

                item.sendMessage(message);

                log.info("推送消息到指定客户端"+item+"，推送内容:"+message);


//                }else if(item.sid.equals(sid)){
//                    item.sendMessage(message);
//                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }

}
