package com.JUtils.sensitiveword;

import com.roklenarcic.util.strings.AhoCorasickSet;
import com.roklenarcic.util.strings.SetMatchListener;
import com.roklenarcic.util.strings.StringSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhui
 * @date 2017-05-17 下午4:06
 */
public class SensitiveWordUtil {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private StringSet stringSet; //AC校验类

    private static final String SENSTIVE_WORDS_FILE = "/SensitiveWords.txt";

    public SensitiveWordUtil() {
        loadConfAndInitStringSet();
    }

    /**
     * 加载敏感词配置文件，初始化StringSet
     *
     */
    private void loadConfAndInitStringSet() {
        InputStream is = null;
        BufferedReader br = null;
        try {
            List<String> blackWords = new ArrayList<String>();
            is = this.getClass().getResourceAsStream(SENSTIVE_WORDS_FILE);
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String words = null;
            while ((words = br.readLine()) != null) {
                String[] word = words.split(",");
                for (int i = 0; i < word.length; i++) {
                    if (!"".equals(word[i])) {
                        blackWords.add(word[i]);
                    }
                }
            }
            stringSet = new AhoCorasickSet(blackWords, false);

        } catch (Exception e) {
            logger.error("Error when load sensitive words file.", e);

        } finally {
            try {
                if (br != null) { br.close(); }
                if (is != null) { is.close(); }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 检验字符串内是否包含敏感词
     *
     * @param strToCheck
     * @return
     */
    public boolean containSensitiveWords(String strToCheck) {
        if (StringUtils.isBlank(strToCheck)) {
            return false;
        }
        CountingMatchListener listener = new CountingMatchListener();
        stringSet.match(strToCheck, listener);
        return listener.getCount() > 0;
    }

    class CountingMatchListener implements SetMatchListener {
        int count = 0;

        public boolean match(String haystack, final int startPosition, final int endPosition) {
            logger.warn("Found sensitive word: {}", haystack.substring(startPosition, endPosition));
            count++;
            return true;
        }

        public int getCount() {
            return count;
        }

        public CountingMatchListener reset() {
            count = 0;
            return this;
        }
    }
}
