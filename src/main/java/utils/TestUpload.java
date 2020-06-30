package utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class TestUpload implements Runnable {
	private String url;
	private JSONObject json;
	
	/**
	 * 
	 * @param json 
	 * "message": "提交说明", 
	 * "content": "将文件base64编码后的字符串",
	 *  commiter:{
	 *  	"name": "名字",
	 *   	"email": "邮箱"
	 *   }
	 */
	public TestUpload(JSONObject json,String url) {
		this.json = json;
		this.url = url;
	}

	public void run() {
		// 设置编码
		String encode = "utf-8";
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		// 创建Http连接 以put方法请求url
		HttpPut httpput = new HttpPut(url);
		// 设置请求的header信息
		/** header中通用属性 */
		httpput.setHeader("Accept", "*/*");
		httpput.setHeader("Accept-Encoding", "gzip, deflate");
		httpput.setHeader("Cache-Control", "no-cache");
		httpput.setHeader("Connection", "keep-alive");
		httpput.setHeader("Content-Type", "application/json;charset=UTF-8");
		/** 业务参数 */
		// httpput.setHeader("Authorization", "你的token");

		// 组织请求参数
		/** 按encode编码格式将String数据转为请求的实体对象 */
		StringEntity stringEntity = new StringEntity(json.toJSONString(), encode);
		// 将请求的实体绑定到http请求上
		httpput.setEntity(stringEntity);
		String content = null;// 回复的内容
		CloseableHttpResponse httpResponse = null;// 回复对象
		try {
			// 响应信息
			httpResponse = closeableHttpClient.execute(httpput);// 执行请求
			HttpEntity entity = httpResponse.getEntity();// 接收响应
			content = EntityUtils.toString(entity, encode);// 将响应转为String类型
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 关闭资源
			try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			closeableHttpClient.close(); // 关闭连接、释放资源
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(content);
//				return content;//返回响应内容
	}

}