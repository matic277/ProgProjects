package JUnitTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import Words.Hashtag;
import Words.Target;
import Words.URL;

class WordClassifyTests {
	
	String[] hashtags = {
		"#hashtag",
		"##hashtag",
		"#abc#asd"
	}
	String[] notHashtags = {
		"hashtag",
		"a#hashtag",
		"onetwothree#"
	}
	@Test
	public void hashtagTests() {
		// hashtags
		for (int i=0; i<hashtags.length; i++) {
			Assert.assertTrue(Hashtag.isType(hashtags[i]));
		}
		// not hashtags
		for (int i=0; i<notHashtags.length; i++) {
			Assert.assertFalse(Hashtag.isType(notHashtags[i]));
		}
	}
	
	String[] urls = {
		"http://www.google.com",
		"https://www.google.com",
		"najdi.si",
		"www.whatever.we",
		"www.w.org",
		"www.something.co.uk",
		"something.co.uk",
		"ww.com.com"
	}
	String[] notUrls = {
		"google.commmmmm",
		"http//www.com.com",
		"address.toolong"
	}
	@Test
	public void urlTests() {
		// urls
		for (int i=0; i<urls.length; i++) {
			Assert.assertTrue(URL.isType(urls[i]));
		}
		// not urls
		for (int i=0; i<notUrls.length; i++) {
			Assert.assertFalse(URL.isType(notUrls[i]));
		}
	}
	
	String[] targets = {
		"@abc",
		"@@abc",
		"@abc@abc"
	}
	String[] notTargets = {
		"abc",
		"@",
		"a@bcd",
		"hello@"
	}
	@Test
	public void targetTests() {
		// targets
		for (int i=0; i<targets.length; i++) {
			Assert.assertTrue(Target.isType(targets[i]));
		}
		// not targets
		for (int i=0; i<notTargets.length; i++) {
			Assert.assertFalse(Target.isType(notTargets[i]));
		}

}
