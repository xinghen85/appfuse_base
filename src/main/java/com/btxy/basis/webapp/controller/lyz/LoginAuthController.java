package com.btxy.basis.webapp.controller.lyz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginAuthController {

	private int width = 90;// 定义图片的width
	private int height = 23;// 定义图片的height

	private Random random = new Random();
	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串
	private int stringNum = 4;// 随机产生字符数量
	private int lineSize = 0;// 干扰线数量
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	@RequestMapping("/api/identifyingCode/getLoginImage")
	public void getLoginCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(80, 35, BufferedImage.TYPE_INT_BGR);

		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		gd.fillRect(0, 0, 80, 35);
		gd.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		gd.setColor(getRandColor(110, 133));
		// 将图像填充为白色

		// 绘制干扰线
		for (int i = 0; i < lineSize; i++) {
			drowLine(gd);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= stringNum; i++) {
			randomString = drowString(gd, randomString, i);
		}
		HttpSession session = req.getSession();
		System.out.print(randomString);
		session.setAttribute("loginCode", randomString.toString());

		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	@RequestMapping("/api/identifyingCode/checkCode/{vcode}/php*")
	public void checkCode(HttpServletRequest req, HttpServletResponse resp, @PathVariable String vcode) throws IOException {
		JSONObject obj = new JSONObject();
		HttpSession session = req.getSession();
		String code = (String) session.getAttribute("loginCode");
		if (StringUtils.isNotEmpty(code)) {
			if (!code.equalsIgnoreCase(vcode)) {
				obj.put("rtnValue", 0);
				obj.put("rtnDescription", "验证码错误");
				returnJSON(obj, resp);
				return;
			}
		}
		obj.put("rtnValue", 1);
		obj.put("rtnDescription", "验证成功");
		returnJSON(obj, resp);
	}

	/*
	 * 获得字体
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/*
	 * 绘制字符串
	 */
	private String drowString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 11 * i, 20);
		return randomString;
	}

	/*
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/*
	 * 获取随机的字符
	 */
	private String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}

	private void returnJSON(JSONObject obj, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(obj == null ? "{}" : obj.toJSONString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
