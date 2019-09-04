/**
 * 
 */
package com.liangzisong.security.core.validate.code.image;

import com.liangzisong.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;


/**
 * @author zhailiang
 *
 */
public class ImageCode extends ValidateCode {

	private static final long serialVersionUID = -683762396972609683L;
	//这个就不必参与序列化了
	private transient BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
