/*
 *	History				Who				What
 *  2017年9月7日			liujinfeng			Created.
 */
package com.ue.ems.common.util;

/**
 * Title: TRS 内容协作平台（TRS WCM） <BR>
 * Description: <BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 * 
 * @author liu.jinfeng
 * @version 1.0
 */
public class HexUtil {
    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
    public static String toHexString(byte[] src)
    {
      char[] value = null;
      int j = src.length;
      value = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++)
      {
        byte byte0 = src[i];
        value[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
        value[(k++)] = hexDigits[(byte0 & 0xF)];
      }
      return new String(value);
    }
}
