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
		String access_token= "������Լ���token";
		System.out.println("���ܵ���base64: "+content);
        //committer��Ϣ
		JSONObject committer = new JSONObject();
		committer.put("name", "github��");
		committer.put("email", "����");
		//Ҫ���͵�json
		JSONObject json = new JSONObject();
		json.put("message", "�ύ�ļ���˵��");
        //ȥ�����յ���base64���룬�����ύ�����ݲ���base64���뵼��ʧ��
		json.put("content", content.split(",")[1].trim().replace("%", "").replace(",", "").replace(" ", "+"));
		json.put("committer", committer);
		System.out.println(json);
        //Ҫ������ļ��������Դ��ļ��У������ڻ��Զ���������a/test.jpg
		String filename = "test27.jpg";
		String url = "https://api.github.com/repos/raingnight/Blog_pic/contents/"+filename+"?access_token="+access_token;
		
		TestUpload testUpload = new TestUpload(json, url);
		Thread t = new Thread(testUpload);
		t.run();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print("��ʼִ��");//�����󷽷������ݣ������жϺ󷵻سɹ���ʧ�ܵ���Ϣ
		printWriter.close();
	}

}
