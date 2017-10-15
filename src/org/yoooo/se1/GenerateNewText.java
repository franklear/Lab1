package org.yoooo.se1;

import java.util.List;
import java.util.Random;

public class GenerateNewText {
    /**
     * Generates new text from input text according to bridge words
     *
     * @param inputText input text
     * @return new text
     */
    public static String generateNewText(String inputText) {
        String[] words = inputText.split(" ");
        if (words.length < 2) return inputText;
        StringBuilder result = new StringBuilder(words[0]);
        Random random = Application.getInstance().getRandom();
        for (int i = 1; i < words.length; ++i) {
            List<String> bridgeWords = QueryBridgeWords.getBridgeWords(words[i - 1], words[i]);
            if (bridgeWords != null && !bridgeWords.isEmpty()) {
                result.append(" ");
                result.append(bridgeWords.get(random.nextInt(bridgeWords.size())));
            }
            result.append(" ");
            result.append(words[i]);
        }
        return result.toString();
    }
}
