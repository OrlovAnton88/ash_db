package aorlov.ashdb.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mouse
 * Date: 21.12.13
 * Time: 2:41
 * To change this template use File | Settings | File Templates.
 */
public class Vocabulary {

    private static Vocabulary instance = null;
    private Map<String, String> vocabularyMap = null;

    private Vocabulary() {
        constructVocabulary();
        moveIntoLowerCase(vocabularyMap);
    }

    private void constructVocabulary() {
        vocabularyMap = new HashMap<String, String>();
        vocabularyMap.put("Код", "personalCode");
        vocabularyMap.put("Пол", "gender");
        vocabularyMap.put("Фамилия Имя", "SurnameAndName");
        vocabularyMap.put("Отчество", "familyName");
        vocabularyMap.put("Клуб", "club");
        vocabularyMap.put("Текущ. класс", "currentClass");
        vocabularyMap.put("Дата пост.в базу", "registrationDate");
    }

    private void moveIntoLowerCase(Map<String, String> mapIn) {
        Map<String, String> newMap = new HashMap();
        Set keys = mapIn.keySet();
        Iterator keysIterator = keys.iterator();
        while (keysIterator.hasNext()) {
            String key = (String) keysIterator.next();
            String value = mapIn.get(key);
            newMap.put(key.toLowerCase(), value.toLowerCase());
        }
        vocabularyMap.clear();
        vocabularyMap.putAll(newMap);
    }


    public Map<String, String> getVocabulary() {
        return vocabularyMap;
    }

    public static Vocabulary getInstance() {
        if (instance == null) {
            instance = new Vocabulary();
        }
        return instance;
    }
}
