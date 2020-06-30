package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import utils.TestUpload;

public class UploadServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String content = request.getParameter("content");
		String access_token= "这里放自己的token";
		System.out.println("接受到的base64: "+content);
        //committer信息
		JSONObject committer = new JSONObject();
		committer.put("name", "github名");
		committer.put("email", "邮箱");
		//要发送的json
		JSONObject json = new JSONObject();
		json.put("message", "提交文件的说明");
        //去除接收到的base64编码，避免提交的数据不是base64编码导致失败
		json.put("content", content.split(",")[1].trim().replace("%", "").replace(",", "").replace(" ", "+"));
		json.put("committer", committer);
		System.out.println(json);
        //要保存的文件名，可以带文件夹，不存在会自动创建，如a/test.jpg
		String filename = "test27.jpg";
		String url = "https://api.github.com/repos/raingnight/Blog_pic/contents/"+filename+"?access_token="+access_token;
		
		TestUpload testUpload = new TestUpload(json, url);
		Thread t = new Thread(testUpload);
		t.run();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print("开始执行");//向请求方返回数据，可做判断后返回成功或失败等信息
		printWriter.close();
	}

}
