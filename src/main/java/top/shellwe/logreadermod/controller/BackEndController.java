package top.shellwe.logreadermod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.ServerEndpoint;

/**
 * <p>后台通用信息控制类 借助此类生成LogReader后台的一些基本通用信息返回到前端</p>
 * @author shellwe
 * @version 1.0
 * @since 2022/12/04
 */

@RestController
public class BackEndController {
    @Autowired
    private WebSocketController wsc;

    public void JqcaseSearch() {
        try {
            System.out.println("这是心跳");
            wsc.sendInfo("主动推送消息");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
