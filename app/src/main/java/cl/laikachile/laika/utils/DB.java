package cl.laikachile.laika.utils;

/**
 * Created by Tito_Leiva on 27-01-15.
 */
public class DB {

    public final static String _IN_ = " IN ";
    public final static String _AND_ = " AND ";
    public final static String _OR_ = " OR ";
    public final static String _EQUALS_ = " = ";
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
