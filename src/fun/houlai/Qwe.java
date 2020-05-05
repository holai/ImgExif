package fun.houlai;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Qwe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("P3P", "CP=CAO PSA OUR");
        if (req.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(req.getMethod())) {
            resp.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTIONS");
            resp.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept");
            resp.addHeader("Access-Control-Max-Age", "120");
        }
        String data=(String) req.getParameter("data");
        Map map = ImageM.GenerateImage(data,"qwe");
        System.out.println(map.size());
        JSONObject  jsona = null;
        if(map != null ){
            jsona = JSONObject.parseObject(JSON.toJSONString(map));//把map转为json数据
            System.out.println(jsona);
        }else {
            map = new HashMap();
            map.put("code",-1);
            jsona = JSONObject.parseObject(JSON.toJSONString(map));
            System.out.println("xxx");
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.print(jsona);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }



}
