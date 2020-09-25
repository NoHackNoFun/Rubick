package Rubik;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cube {

	private String md5;
	private int back[][]; 
	private int down[][]; 
	private int front[][]; 
	private int left[][]; 
	private int right[][]; 
	private int up[][]; 
	

	public Cube(int[][] back, int[][] down, int[][] front,
			int[][] left, int[][] right, int[][] up) {
		super();
		this.back = back;
		this.down = down;
		this.front = front;
		this.left = left;
		this.right = right;
		this.up = up;
	}

	public Cube() {
	}

	public int[][] getBack() {
		return back;
	}

	public void setBack(int[][] back) {
		this.back = back;
	}

	public int[][] getDown() {
		return down;
	}

	public void setDown(int[][] down) {
		this.down = down;
	}

	public int[][] getFront() {
		return front;
	}

	public void setFront(int[][] front) {
		this.front = front;
	}

	public int[][] getLeft() {
		return left;
	}

	public void setLeft(int[][] left) {
		this.left = left;
	}

	public int[][] getRight() {
		return right;
	}

	public void setRight(int[][] right) {
		this.right = right;
	}

	public int[][] getUp() {
		return up;
	}

	public void setUp(int[][] up) {
		this.up = up;
	}
	
	public String getMd5() {
		updatemd5();
		return md5;
	}
	
	public void updatemd5() {
		try {
			this.md5 = generateMD5(face(back)+face(down)+face(front)+face(left)+face(right)+face(up));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String face(int[][] m) {
		String txt = "";
		for(int i = 0; i < m.length; i++)
			for(int j = 0; j < m.length; j++)
				txt+=m[i][j];
		return txt;
	}
	
	public static String generateMD5(String id) throws NoSuchAlgorithmException {

		String IdMD5;

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(id.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		IdMD5 = sb.toString();

		return IdMD5;
	}

	
	
}
