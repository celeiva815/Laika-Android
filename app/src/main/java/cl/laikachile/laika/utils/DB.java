package cl.laikachile.laika.utils;

/**
 * Created by Tito_Leiva on 27-01-15.
 */
public class DB {

    public final static String IN = " IN ";
    public final static String AND = " AND ";
    public final static String OR = " OR ";
    public final static String EQUALS = " = ";
    public final static String NOT_EQUALS = " != ";
    public final static String _EQUALS_QUESTION = " = ?";
    public final static String ORDER_BY = "ORDER BY";
    public final static String TRUE = "1";
    public final static String FALSE = "0";

    public static String values(int... integers) {

        String array = "(";

        for(int i = 0; i < integers.length; i++) {

            array += Integer.toString(integers[i]);

            if (i == integers.length - 1)
                array += ")";
            else
                array += ", ";
        }

        return array;
    }
}
