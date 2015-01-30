package com.dynamo2.myerp.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class RegrexTest {

	@Test
	public void testVerifyWebsite() {
		Pattern p = Pattern.compile("www\\.[a-zA-Z0-9]*\\.(com|cn|com\\.cn)");
		Matcher matcher = p.matcher("www.sina.com");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("www.sina.com.cn");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("www.sina.cn");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("http://www.sina.cn");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("www.si na.cn");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("w ww.si na.cn");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher(" w ww.si na.cn ");
		Assert.assertFalse(matcher.matches());
	}

	@Test
	public void testVerifyCompanyName() {
		Pattern p = Pattern.compile("[^\\s\\p{Punct}]*");
		Matcher matcher = p.matcher("  aaa  ");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("aa  a");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("aa.xa");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("aa(xa");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("aaa");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("您好");
		Assert.assertTrue(matcher.matches());
	}

	@Test
	public void testVerifyCurrency() {
		Pattern p = Pattern.compile("([0-9]+\\.[0-9]+)|([0-9]+)");
		Matcher matcher = p.matcher("0.11");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher(".11");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("11");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("11.");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("a");
		Assert.assertFalse(matcher.matches());
	}

	@Test
	public void testVerifyPhone() {
		Pattern p = Pattern
				.compile("(\\([0-9]{3}\\)\\-[0-9]{3,4}\\-[0-9]{6,8})|([0-9]{3,4}\\-[0-9]{6,8})|([0-9]{3,4}\\-[0-9]{6,8}\\-[0-9]{3,4})");
		Matcher matcher = p.matcher("025-1111111");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("1111111");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("025-1111111-111");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("0571-1111111");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("0571-1111111-9989");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("111-1111111111");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("(086)-025-1111111");
		Assert.assertTrue(matcher.matches());
	}

	@Test
	public void testVerifyEmail() {
		Pattern p = Pattern.compile("[\\w]+@[\\w]+\\.(com|cn|com\\.cn)");
		Matcher matcher = p.matcher("fwang@yahoo.com.cn");
		Assert.assertTrue(matcher.matches());

		matcher = p.matcher("fwangyahoo.com.cn");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("fwang@yahoo");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("fwang@yaho o");
		Assert.assertFalse(matcher.matches());

		matcher = p.matcher("_fwang@yahoo.com");
		Assert.assertTrue(matcher.matches());
	}

	@Test
	public void testExtractFieldNameFromExpression() {
		Pattern p = Pattern.compile("(#\\w+#)");
		Matcher matcher = p.matcher("(#field1# + #field2#) * 0.5 + #field3#");

		while (matcher.find()) {
			System.out.println(matcher.group());
		}

	}

	@Test
	public void testStringFormat() {
		System.out.println(String.format("C%06d", 1000));
	}
	
	@Test
	public void testVerifyRoleName() {
		Pattern p = Pattern.compile("ROLE_[A-Z_]*");
		Matcher matcher = p.matcher("ROLE_A_B_C");
		Assert.assertTrue(matcher.matches());
		
		matcher = p.matcher("ROLE_A_b_C");
		Assert.assertFalse(matcher.matches());
		
		matcher = p.matcher("ROL_A_b_C");
		Assert.assertFalse(matcher.matches());
	}
}
