package org.sparklezfish.drivebywire.typewriter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TypewriterChannels {

    private static final String HUNGARIAN_CHARACTERS = "áÁéÉíÍóÓöÖőŐúÚüÜűŰ";

    private static final LinkedHashMap<Integer, String> CODE_TO_CHANNEL = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> CHARACTER_TO_CHANNEL = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> CHANNEL_NAMES = new LinkedHashMap<>();

    static {
        // Letters A-Z (GLFW codes match ASCII uppercase: 65-90)
        for (int c = 65; c <= 90; c++) {
            registerCode(c, "key" + (char) c);
            registerCharacter(Character.toString((char) c), "key" + (char) c);
            registerCharacter(Character.toString(Character.toLowerCase((char) c)), "key" + (char) c);
        }
        // Digits 0-9 (GLFW 48-57)
        for (int c = 48; c <= 57; c++) {
            registerCode(c, "key" + (char) c);
            registerCharacter(Character.toString((char) c), "key" + (char) c);
        }
        // Symbol keys present in the Typewriter's keyboard UI
        registerCodeAndCharacter(32, " ", "keySpace");
        registerCodeAndCharacter(39, "'", "keyApostrophe");
        registerCodeAndCharacter(44, ",", "keyComma");
        registerCodeAndCharacter(45, "-", "keyMinus");
        registerCodeAndCharacter(46, ".", "keyPeriod");
        registerCodeAndCharacter(47, "/", "keySlash");
        registerCodeAndCharacter(59, ";", "keySemicolon");
        registerCodeAndCharacter(61, "=", "keyEquals");
        registerCodeAndCharacter(91, "[", "keyLeftBracket");
        registerCodeAndCharacter(92, "\\", "keyBackslash");
        registerCodeAndCharacter(93, "]", "keyRightBracket");
        registerHungarianCharacters();
        // Control keys
        registerCode(257, "keyEnter");
        registerCode(258, "keyTab");
        registerCode(259, "keyBackspace");
        registerCode(261, "keyDelete");
        registerCode(280, "keyCapsLock");
        // Navigation
        registerCode(262, "keyRight");
        registerCode(263, "keyLeft");
        registerCode(264, "keyDown");
        registerCode(265, "keyUp");
        registerCode(266, "keyPageUp");
        registerCode(267, "keyPageDown");
        registerCode(269, "keyEnd");
        // Modifiers
        registerCode(340, "keyLeftShift");
        registerCode(341, "keyLeftCtrl");
        registerCode(342, "keyLeftAlt");
        registerCode(343, "keyLeftSuper");
        registerCode(344, "keyRightShift");
        registerCode(345, "keyRightCtrl");
        registerCode(346, "keyRightAlt");
        registerCode(348, "keyMenu");
    }

    public static final List<String> CHANNELS = List.copyOf(CHANNEL_NAMES.values());

    public static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(CODE_TO_CHANNEL);
    public static final Map<String, String> CHARACTER_MAP = Collections.unmodifiableMap(CHARACTER_TO_CHANNEL);

    private TypewriterChannels() {
    }

    public static boolean isKnownChannel(String channel) {
        return CHANNELS.contains(channel);
    }

    public static String channelForCharacter(String character) {
        if (character == null || character.isEmpty())
            return null;
        return CHARACTER_TO_CHANNEL.get(character);
    }

    public static String channelForCode(int key) {
        return CODE_TO_CHANNEL.get(key);
    }

    public static boolean isHungarianCharacter(String character) {
        return character != null
            && character.codePointCount(0, character.length()) == 1
            && HUNGARIAN_CHARACTERS.indexOf(character.charAt(0)) >= 0;
    }

    private static void registerCode(int code, String channel) {
        CODE_TO_CHANNEL.put(code, channel);
        CHANNEL_NAMES.put(channel, channel);
    }

    private static void registerCharacter(String character, String channel) {
        CHARACTER_TO_CHANNEL.put(character, channel);
        CHANNEL_NAMES.put(channel, channel);
    }

    private static void registerCodeAndCharacter(int code, String character, String channel) {
        registerCode(code, channel);
        registerCharacter(character, channel);
    }

    private static void registerHungarianCharacters() {
        registerAccent("á", "keyÁ");
        registerAccent("é", "keyÉ");
        registerAccent("í", "keyÍ");
        registerAccent("ó", "keyÓ");
        registerAccent("ö", "keyÖ");
        registerAccent("ő", "keyŐ");
        registerAccent("ú", "keyÚ");
        registerAccent("ü", "keyÜ");
        registerAccent("ű", "keyŰ");
    }

    private static void registerAccent(String character, String channel) {
        registerCharacter(character, channel);
        registerCharacter(character.toUpperCase(), channel);
    }
}
